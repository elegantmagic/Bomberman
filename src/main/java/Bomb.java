/*
<<<<<<< HEAD
//package main.java;

public class Bomb extends Animation {
||||||| 7460973
public class Bomb extends Animation {
=======
import java.awt.image.BufferedImage;
>>>>>>> development
*/

import java.awt.image.*;
public class Bomb extends Animation {
    private float timer = 1.2f * 3.0f;
    private int radius = 4; 
    private BombPlanter bp;

    public Bomb(BufferedImage all, int x, int y) {
        BufferedImage[] frames = new BufferedImage[3];
        float[] frameLengths = new float[3];
        for (int i = 0; i < frames.length; i++) {
            frameLengths[i] = 1.2f;
            frames[i] = all.getSubimage(Global.tileSize * i, 3 * Global.tileSize, Global.tileSize, Global.tileSize);
        }
        
        int[] segmentStarts = {0};
        int[] segmentLengths = {frames.length};

        setFrames(frames);
        setFrameLengths(frameLengths);

        setSegmentStarts(segmentStarts);
        setSegmentLenghts(segmentLengths);

        assert(x % Global.scaledSize == 0 && y % Global.scaledSize == 0);
        setX(x);
        setY(y);
    }

    public Bomb(BufferedImage all, int x, int y, int radius) {
        this(all, x, y);
        this.radius = radius;
    }

    public Bomb(BufferedImage all, int x, int y, int radius, BombPlanter bp) {
        this(all, x, y);
        this.radius = radius;
        this.bp = bp;
    }


    public static Bomb plantBomb(int col, int row, int radus, BombPlanter bp) {
        Bomb bomb = new Bomb(Global.all, col, row, radus, bp);
        Global.drawables.add(bomb);
        Global.dynamics.add(bomb);
        return bomb;
    }

    public Explosion explode() {
        Explosion ex = new Explosion(this);
        bp.allowPlantingBomb();

        Global.addQueue.add(ex);
        Global.deleteQueue.add(this);
        return ex;
    }

    public void update(float delta) {
        super.update(delta);
        timer -= delta;
        if (timer <= 0.0f)
            explode();
    }
    
    /**
     * Get radius.
     *
     * @return radius as int.
     */
    public int getRadius()
    {
        return radius;
    }
}
