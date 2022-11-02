//package main.java;

import java.awt.image.BufferedImage;

class Balloom extends Enemy {
    private TileMap.Pair prev;    
    private TileMap.Pair targ;
    private float t;
    private float untilNextAttack = 10.0f;

    private static final float rateParameter = -0.01f;
    private static final float playerSensitivity = 0.01f;


    private boolean dying = false;
    public Balloom(TileMap.Pair initial) {
        Global.nEnemy++;

        prev = initial;
        TileMap.Pair[] options = {new TileMap.Pair(prev.x - Global.scaledSize, prev.y)
                                , new TileMap.Pair(prev.x + Global.scaledSize, prev.y)
                                , new TileMap.Pair(prev.x, prev.y - Global.scaledSize)
                                , new TileMap.Pair(prev.x, prev.y + Global.scaledSize)};

        for (int i = 0; i < options.length; i++) {
            if (Global.tilemap.isFreeSpace(options[i])) {
                targ = options[i];
                break;
            }
        }
        

        BufferedImage frames[] = new BufferedImage[11];
        float frameLengths[] = new float[11];
        for (int i = 0; i < frames.length; i++) {
            frameLengths[i] = 1.8f;
            frames[i] = Global.all.getSubimage(Global.tileSize * i, Global.tileSize * 15, Global.tileSize, Global.tileSize);
        }

        int[] segmentStarts = {0, 6};
        int[] segmentLengths = {6, 5};

        setFrames(frames);
        setFrameLengths(frameLengths);
        setSegmentStarts(segmentStarts);
        setSegmentLenghts(segmentLengths);

        x = prev.x;
        y = prev.y;

        t = 0.0f;

        untilNextAttack = (float)Math.log(Global.rnd.nextFloat(0.001f, 0.999f)) / (rateParameter);
    }

    public static Balloom newBalloom(int x, int y) {
        return new Balloom(new TileMap.Pair(x, y));
    }


    public void update(float delta) {
        SpaceSearch.remove(this);
        super.update(delta);
        if (dying) {
            if (getCycleNo() > 0) {
                Global.deleteQueue.add(this);
            }
            return;
        }



        x = prev.x + (int)((targ.x - prev.x) * t);
        y = prev.y + (int)((targ.y - prev.y) * t);


        if (Global.bomber.isAlive) t += delta * 0.2f;
        if (t > 1.0f) {
            t = 0.0f;
            pathing();
        }
        SpaceSearch.add(this);

        if (Global.bomber.isAlive) untilNextAttack -= delta;
        if (untilNextAttack < 0.0f) {
            untilNextAttack = Float.MAX_VALUE;
            int x_ = x + Global.scaledSize / 2;
            int y_ = y + Global.scaledSize / 2;

            Bomb.plantBomb(x_ - (x_ % Global.scaledSize), y_ - (y_ % Global.scaledSize), 4, this);
        }
    }

    protected TileMap.Pair pathing() {
        TileMap.Pair[] options = {new TileMap.Pair(targ.x - Global.scaledSize, targ.y)
                                , new TileMap.Pair(targ.x + Global.scaledSize, targ.y)
                                , new TileMap.Pair(targ.x, targ.y - Global.scaledSize)
                                , new TileMap.Pair(targ.x, targ.y + Global.scaledSize)};
        int f = 0;
        for (int i = 0; i < options.length; i++) {
            if (!Global.tilemap.isFreeSpace(options[i]) || options[i].equals(prev)) {
                options[i] = null;
            } else {
                f++;
            }
        }
        if (f == 0) {
            TileMap.Pair a = targ;
            targ = prev; 
            prev = a;
        } else {
            int k = Global.rnd.nextInt(4);
            for (int i = 0; i < options.length; i++) {
                if (options[(i + k) % options.length] != null) {
                    prev = targ; 
                    targ = options[(i + k) % options.length];
                    break;
                }
            }
        }
        return targ;
    }

    public void allowPlantingBomb() {
        int playerDistance = Math.absExact(Global.bomber.getX() - x) + Math.absExact(Global.bomber.getY() - y); 
        untilNextAttack = (float)Math.log(Global.rnd.nextFloat(0.001f, 0.999f)) / (rateParameter - playerDistance * playerSensitivity); //  + playerSensitivity * playerDistance);
    }

    public void die() {
        if (dying) return;
        dying = true;
        setSegment(1);
        setCycleNo(0);
    }
}
