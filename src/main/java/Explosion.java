import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion extends Spatial implements Drawable, Dynamic {
    private static BufferedImage[] center = null;
    private static BufferedImage[][] blast = null;
    private static BufferedImage[][] corner = null;

    private static final float defusionTime = 0.28f;
    private float time = defusionTime;
    private int stage = 0;

    private int[] extents = {0, 0, 0, 0};
    private int blastRadius;

    public Explosion(Bomb bomb) {
        this.blastRadius = bomb.getRadius();
        if (center == null) {
            center = new BufferedImage[4];
            blast = new BufferedImage[4][4];
            corner = new BufferedImage[4][4];

            for (int i = 0; i < center.length; i++) {
                int x = (i % 2) * 5 + 2;
                int y = (i / 2) * 5 + 6;
                center[i] = Global.all.getSubimage(x * Global.tileSize, y * Global.tileSize, Global.tileSize, Global.tileSize);
                for (int j = 0; j < 4; j++) {
                    final int[] d = {0, -1, 0, 1};
                    blast[i][j] = Global.all.getSubimage((x + d[j]) * Global.tileSize, (y + d[(j + 1) % d.length]) * Global.tileSize
                            , Global.tileSize, Global.tileSize);
                    corner[i][j] = Global.all.getSubimage((x + d[j] * 2) * Global.tileSize, (y + d[(j + 1) % d.length] * 2) * Global.tileSize
                            , Global.tileSize, Global.tileSize);

                }
            }
        }
        setX(bomb.getX());
        setY(bomb.getY());
        int x = bomb.getX() / Global.scaledSize;
        int y = bomb.getY() / Global.scaledSize;
        for (int i = 0; i < extents.length; i++) {
            final int[] d = {0, -1, 0, 1};
            extents[i] = blastRadius;
            for (int r = 0, x_ = x, y_ = y; 
                    r < blastRadius;
                    r++, x_ += d[i], y_ += d[(i + 1) % d.length]) {
                    if (Global.tilemap.map[x_][y_] == 2) {
                        Global.tilemap.map[x_][y_] = 0;
                        Collectable.randomCollectableAt(y_, x_);
                    } else if (Global.tilemap.map[x_][y_] != 0) {
                        extents[i] = r;
                        break;
                    }
                }
        }
        
    }

    public void draw() {
        Graphics2D graphics2D = (Graphics2D) Global.framebuffer.getGraphics();
		graphics2D.drawImage(center[stage], x, y, Global.scaledSize, Global.scaledSize, null);
        for(int i = 0; i < extents.length; i++) {
            final int[] d = {0, -1, 0, 1};
            int x_ = x / Global.scaledSize + d[i];
            int y_ = y / Global.scaledSize + d[(i + 1) % d.length];
            for (int r = 1; 
                    r < extents[i] - 1;
                    r++, x_ += d[i], y_ += d[(i + 1) % d.length]) {
		            graphics2D.drawImage(blast[stage][i], x_ * Global.scaledSize, y_ * Global.scaledSize, Global.scaledSize, Global.scaledSize, null);
                }
		    if (extents[i] > 1) graphics2D.drawImage(corner[stage][i], x_ * Global.scaledSize, y_ * Global.scaledSize, Global.scaledSize, Global.scaledSize, null);
        }
    }

    public void update(float delta) {
        time -= delta;
        if (time < 0.0f) {
            time = defusionTime;
            stage++;
            if (stage >= center.length) {
                Global.deleteQueue.add(this);
                stage = center.length - 1;
                return;
            }
        }
    }
}

