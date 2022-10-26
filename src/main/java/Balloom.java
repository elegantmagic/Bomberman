//package main.java;

import java.awt.image.BufferedImage;

class Balloom extends Animation {
    private TileMap.Pair prev;    
    private TileMap.Pair targ;
    private float t;

    public Balloom(BufferedImage all, TileMap.Pair initial) {
        assert(initial.x % Global.scaledSize == 0 && initial.y % Global.scaledSize == 0);
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
        

        BufferedImage frames[] = new BufferedImage[6];
        float frameLengths[] = new float[6];
        for (int i = 0; i < frames.length; i++) {
            frameLengths[i] = 1.8f;
            frames[i] = all.getSubimage(Global.tileSize * i, Global.tileSize * 15, Global.tileSize, Global.tileSize);
        }

        int[] segmentStarts = {0};
        int[] segmentLengths = {6};

        setFrames(frames);
        setFrameLengths(frameLengths);
        setSegmentStarts(segmentStarts);
        setSegmentLenghts(segmentLengths);

        x = prev.x;
        y = prev.y;

        t = 0.0f;
    }


    public void update(float delta) {
        SpaceSearch.remove(this);
        super.update(delta);

        x = prev.x + (int)((targ.x - prev.x) * t);
        y = prev.y + (int)((targ.y - prev.y) * t);


        t += delta * 0.1f;
        if (t > 1.0f) {
            t = 0.0f;
            pathing();
        }
        SpaceSearch.add(this);
    }

    private void pathing() {
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
        assert(targ.x % Global.scaledSize == 0 && targ.y % Global.scaledSize == 0);
    }
}
