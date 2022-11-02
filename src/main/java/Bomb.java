import java.awt.image.*;
public class Bomb extends Animation {
    private float timer = 36.0f;
    private int radius = 4; 
    private BombPlanter bp;

    private int[] extents = {0, 0, 0, 0};

    public Bomb(BufferedImage all, int x, int y) {
        this(all, x, y, 4, null);
    }

    public Bomb(BufferedImage all, int x, int y, int radius) {
        this(all, x, y, radius, null);
    }

    public Bomb(BufferedImage all, int x, int y, int radius, BombPlanter bp) {
        this.radius = radius;
        this.bp = bp;
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


        for (int i = 0; i < extents.length; i++) {
            final int[] d = {0, -1, 0, 1};
            extents[i] = radius;
            for (int r = 0, x_ = x / Global.scaledSize, y_ = y / Global.scaledSize; 
                    r < radius;
                    r++, x_ += d[i], y_ += d[(i + 1) % d.length]) {
                    Global.bombBlast.map[x_][y_] += -2 * (radius - r - 1);
                    if (Global.tilemap.map[x_][y_] != 0) {
                        extents[i] = r;
                        break;
                    }
                }
        }

        /*
        for (int i = 0; i < Global.bombBlast.map.length; i++) {
            for (int j = 0; j < Global.bombBlast.map[i].length; j++) {
                System.out.print(Global.bombBlast.map[i][j] + " ");
            }
            System.out.println();
        }*/

    }


    public static Bomb plantBomb(int col, int row, int radus, BombPlanter bp) {
        Bomb bomb = new Bomb(Global.all, col, row, radus, bp);
        Global.addQueue.add(bomb);
        return bomb;
    }

    public Explosion explode() {
        Explosion ex = new Explosion(this);
        if (bp != null) bp.allowPlantingBomb();

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
    
    public int getRadius()
    {
        return radius;
    }
}
