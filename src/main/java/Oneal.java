//package main.java;

import java.awt.image.BufferedImage;
import java.util.*;

public class Oneal extends Animation {
    static int[][] distances = null;
    static int width, height;

    static float untilNextRefresh = 10.0f;

    private float t = 0.0f;
    private TileMap.Pair prev, targ; 

    private int mode = 0; // Follow (1 = avoid)

    public Oneal(BufferedImage all, TileMap.Pair initial) {
        if (distances == null) {
            Oneal.width = Global.tilemap.getWidth();
            Oneal.height = Global.tilemap.getHeight();
            distances = new int[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Oneal.distances[x][y] = Global.tilemap.map[x][y] == 0 ? Integer.MAX_VALUE : -1;
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
        x = initial.x;
        y = initial.y;
        
        prev = initial;
        dijkstra();
        
        TileMap.Pair coord = new TileMap.Pair(initial.x / Global.scaledSize, initial.y / Global.scaledSize);
        TileMap.Pair options[] = {new TileMap.Pair(coord.x + 1, coord.y), new TileMap.Pair(coord.x - 1, coord.y), new TileMap.Pair(coord.x, coord.y - 1), new TileMap.Pair(coord.x, coord.y + 1)};
        for (int i = 0; i < options.length; i++) {
            if (Oneal.distances[options[i].x][options[i].y] == -1) continue;
            if (Oneal.distances[options[i].x][options[i].y] < Oneal.distances[coord.x][coord.y]) coord = options[i];
        }

        targ = coord;
        targ.x *= Global.scaledSize;
        targ.y *= Global.scaledSize;
    }

    private void dijkstra() {
        for (int x = 0; x < Oneal.width; x++) {
            for (int y = 0; y < Oneal.height; y++) {
                Oneal.distances[x][y] = Global.tilemap.map[x][y] == 0 ? Integer.MAX_VALUE : -1;
            }
        }
        
        Set<TileMap.Pair> frontier = new HashSet<TileMap.Pair>();
        TileMap.Pair origin = new TileMap.Pair(Global.bomber.getX() / Global.scaledSize, Global.bomber.getY() / Global.scaledSize);
        Oneal.distances[origin.x][origin.y] = 0;
        frontier.add(origin);


        while (!frontier.isEmpty()) {
            TileMap.Pair closest = null;
            int mdis = Integer.MAX_VALUE;
            for (TileMap.Pair candidate : frontier) {
                if (mdis > Oneal.distances[candidate.x][candidate.y]) {
                    closest = candidate;
                    mdis = Oneal.distances[candidate.x][candidate.y];
                }
            }
            frontier.remove(closest);

            TileMap.Pair[] adjacents = {new TileMap.Pair(closest.x + 1, closest.y),
                                        new TileMap.Pair(closest.x - 1, closest.y), 
                                        new TileMap.Pair(closest.x, closest.y + 1), 
                                        new TileMap.Pair(closest.x, closest.y - 1),}; 
            
            for (int i = 0; i < adjacents.length; i++) {
                if (distances[adjacents[i].x][adjacents[i].y] == -1) 
                    continue;
                if (distances[adjacents[i].x][adjacents[i].y] <= mdis + 1) 
                    continue;
                
                Oneal.distances[adjacents[i].x][adjacents[i].y] = mdis + 1;
                frontier.add(adjacents[i]);
            }
        }
    }


    public void update(float delta) {
        super.update(delta);
        Oneal.untilNextRefresh -= delta;
        if (Oneal.untilNextRefresh <= 0.0f) {
            Oneal.untilNextRefresh = 10.0f;
            dijkstra();
        }


        t += delta * 0.2f;
        if (t > 1.0f) {
            t = 0.0f;
            TileMap.Pair coord = new TileMap.Pair(targ.x / Global.scaledSize, targ.y / Global.scaledSize);
            if (mode == 0 && Oneal.distances[coord.x][coord.y] < 3) {
                mode = 1;
            } else if (mode == 1 && Oneal.distances[coord.x][coord.y] > 8) {
                mode = 0;
            }
            TileMap.Pair options[] = {new TileMap.Pair(coord.x + 1, coord.y), new TileMap.Pair(coord.x - 1, coord.y), new TileMap.Pair(coord.x, coord.y - 1), new TileMap.Pair(coord.x, coord.y + 1)};
            for (int i = 0; i < options.length; i++) {
                if (Oneal.distances[options[i].x][options[i].y] == -1) continue;
                if (mode == 0) {
                    if (Oneal.distances[options[i].x][options[i].y] < Oneal.distances[coord.x][coord.y]) coord = options[i];
                } else if (mode == 1) {
                    if (Oneal.distances[options[i].x][options[i].y] > Oneal.distances[coord.x][coord.y]) coord = options[i];
                }
            }
            
            prev = targ;
            targ = coord;
            targ.x *= Global.scaledSize;
            targ.y *= Global.scaledSize;
        }

        x = (int)(targ.x * t) + (int)(prev.x * (1 - t));
        y = (int)(targ.y * t) + (int)(prev.y * (1 - t));
    }


    


}
