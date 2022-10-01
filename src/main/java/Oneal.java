//package main.java;

import java.awt.image.BufferedImage;
import java.util.*;

public class Oneal extends Animation {
    static int[] distances = null;


    public Oneal(BufferedImage all, TileMap.Pair initial) {
        if (distances == null) {
            int width = Global.tilemap.getWidth();
            int height = Global.tilemap.getHeight();
            distances = new int[width * height];
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    distances[Global.tilemap.getIdx(col, row)] = Global.tilemap.map[Global.tilemap.getIdx(col, row)] == Integer.MAX_VALUE ? Integer.MAX_VALUE : -1;
                }
            }
        }

        BufferedImage[] frames = new BufferedImage[6];
        float[] frameLengths = new float[6];
        for (int i = 0; i < frames.length; i++) {
            frameLengths[i] = 1.8f;
            frames[i] = all.getSubimage(Global.tileSize * i, Global.tileSize * 16, Global.tileSize, Global.tileSize);
        }

        int[] segmentStarts = {0};
        int[] segmentLengths = {6};

        setFrames(frames);
        setFrameLengths(frameLengths);
        setSegmentStarts(segmentStarts);
        setSegmentLenghts(segmentLengths);
        
        assert(initial.x % Global.scaledSize == 0 && initial.y % Global.scaledSize == 0);
    }

    private void dijkstra() {
                






    }

    


}
