//package main.java;

import java.awt.image.BufferedImage;

public class Oneal extends Enemy {
    private boolean hasBomb = true;

    static int width, height;

    static float untilNextRefresh = 10.0f;

    private float t = 0.0f;
    private TileMap.Pair prev, targ; 

    private int mode = 0; 
    // 0 : targeting bomber
    // 1 : fleeing bomber
    private boolean dying = false;

    public Oneal(BufferedImage all, TileMap.Pair initial) {
        Global.nEnemy++;

        BufferedImage[] frames = new BufferedImage[11];
        float[] frameLengths = new float[11];
        for (int i = 0; i < 6; i++) {
            frameLengths[i] = 1.8f;
            frames[i] = all.getSubimage(Global.tileSize * i, Global.tileSize * 16, Global.tileSize, Global.tileSize);
        }
        for (int i = 6; i < frames.length; i++) {
            frameLengths[i] = 1.8f;
            frames[i] = all.getSubimage(Global.tileSize * (i + 1), Global.tileSize * 18, Global.tileSize, Global.tileSize);
        }

        int[] segmentStarts = {0, 6};
        int[] segmentLengths = {6, 5};

        setFrames(frames);
        setFrameLengths(frameLengths);
        setSegmentStarts(segmentStarts);
        setSegmentLenghts(segmentLengths);
        
        assert(initial.x % Global.scaledSize == 0 && initial.y % Global.scaledSize == 0);
        x = initial.x;
        y = initial.y;
        
        prev = initial;
        
        TileMap.Pair coord = new TileMap.Pair(initial.x / Global.scaledSize, initial.y / Global.scaledSize);

        targ = coord;
        targ.x *= Global.scaledSize;
        targ.y *= Global.scaledSize;
    }



    public void update(float delta) {
        super.update(delta);
        SpaceSearch.remove(this);
        if (dying) {
            if (getCycleNo() > 0) {
                Global.deleteQueue.add(this);
            }
            return;
        }


        Oneal.untilNextRefresh -= delta;
        

        if (Global.bomber.isAlive) t += delta * 0.2f;
        if (t > 1.0f) {
            t = 0.0f;
            path();
        }
        
        x = prev.x + (int)((targ.x - prev.x) * t);
        y = prev.y + (int)((targ.y - prev.y) * t);
        SpaceSearch.add(this);
    }

    protected TileMap.Pair path() {
        TileMap.Pair coord = new TileMap.Pair(targ.x / Global.scaledSize, targ.y / Global.scaledSize);
        if (mode == 0 && Global.distToBomber.map[coord.x][coord.y] < 4) {
            if (hasBomb && Global.rnd.nextInt(2) == 0) {
                Bomb.plantBomb((getX() + Global.scaledSize / 2) / Global.scaledSize * Global.scaledSize, (getY() + Global.scaledSize / 2) / Global.scaledSize * Global.scaledSize, 3, this);
                hasBomb = false;
            }
            mode = 1;
        } else if (mode == 1 && Global.distToBomber.map[coord.x][coord.y] > 8) {
            mode = 0;
        }
        TileMap.Pair options[] = {new TileMap.Pair(coord.x + 1, coord.y), new TileMap.Pair(coord.x - 1, coord.y), new TileMap.Pair(coord.x, coord.y - 1), new TileMap.Pair(coord.x, coord.y + 1)};
        int optimal = Integer.MAX_VALUE;
        for (TileMap.Pair opt : options) {
            int dijkstra = Global.distToBomber.map[opt.x][opt.y];
            if (dijkstra == -1) continue;
            int score = Global.bombBlast.map[opt.x][opt.y] < 0 ? (-16 * Global.bombBlast.map[opt.x][opt.y]) : dijkstra;  
            if (dijkstra == Integer.MAX_VALUE) {
                score = Global.bombBlast.map[opt.x][opt.y] * -16;
            } else if (mode == 1) score = -score;
            if (score < optimal) {
                coord = opt;
                optimal = score;
            }
        }
        
        prev = targ;
        targ = coord;
        targ.x *= Global.scaledSize;
        targ.y *= Global.scaledSize;
        return targ;
    }


    @Override
    public void allowPlantingBomb() {
        hasBomb = true;
    }

    public void die() {
        if (dying) return;
        dying = true;
        setSegment(1);
        setCycleNo(0);
    }
}
