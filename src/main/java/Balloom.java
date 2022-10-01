//package main.java;

import java.awt.image.BufferedImage;

class Balloom extends Animation {
    private TileMap.Pair prev;    
    private TileMap.Pair next;
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
                next = options[i];
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
        super.update(delta);
        x = (int)(next.x * t) + (int)((1 - t) * prev.x);
        y = (int)(next.y * t) + (int)((1 - t) * prev.y);
        t += delta * 0.1f;
        if (t > 1.0f) {
            t = 0.0f;
            pathing();
        }
    }

    private void pathing() {
        TileMap.Pair[] options = {new TileMap.Pair(next.x - Global.scaledSize, next.y)
                                , new TileMap.Pair(next.x + Global.scaledSize, next.y)
                                , new TileMap.Pair(next.x, next.y - Global.scaledSize)
                                , new TileMap.Pair(next.x, next.y + Global.scaledSize)};
        int f = 0;
        for (int i = 0; i < options.length; i++) {
            if (!Global.tilemap.isFreeSpace(options[i]) || options[i].equals(prev)) {
                options[i] = null;
            } else {
                f++;
            }
        }
        if (f == 0) {
            TileMap.Pair a = next;
            next = prev; 
            prev = a;
        } else {
            int k = Global.rnd.nextInt(4);
            for (int i = 0; i < options.length; i++) {
                if (options[(i + k) % options.length] != null) {
                    prev = next; 
                    next = options[(i + k) % options.length];
                    break;
                }
            }
        }
        assert(next.x % Global.scaledSize == 0 && next.y % Global.scaledSize == 0);
    }
}
