import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Collectable implements Drawable {
    public static Collectable[][] maps;
    private int row;
    private int col;

    public static void setTilemap(TileMap tm) {
        Collectable.maps = new Collectable[tm.getWidth()][tm.getHeight()];
    }

    public Collectable(int row, int col) {
        Collectable.maps[col][row] = this;
        this.row = row;
        this.col = col;

        Global.drawables.add(this);
    }

    protected void drawIcon(BufferedImage icon) {
        Graphics2D graphics2D = (Graphics2D) Global.framebuffer.getGraphics();
		graphics2D.drawImage(icon, col * Global.scaledSize, row * Global.scaledSize, Global.scaledSize, Global.scaledSize, null);
    }

    protected static void collected(Collectable c) {
        Collectable.maps[c.col][c.row] = null;
    }

    public abstract void collect();
    public abstract boolean canBeCollected(Bomber b);

    public static Collectable randomCollectableAt(int row, int col) {
        if (Portal.exist) {
            Global.tilemap.nbrick--;
            switch (Global.rnd.nextInt(3)) {
                case 0:
                    return new FlameItem(row, col);
                case 1:
                    return new SpeedItem(row, col);
                case 2:
                    return new BombItem(row, col);
                default:
                    return null;
            }
        } else {
            if (Global.rnd.nextInt(Global.tilemap.nbrick) == 0) {
                Global.tilemap.nbrick--;
                return new Portal(row, col);
            }
            Global.tilemap.nbrick--;
            switch (Global.rnd.nextInt(3)) {
                case 0:
                    return new FlameItem(row, col);
                case 1:
                    return new SpeedItem(row, col);
                case 2:
                    return new BombItem(row, col);
                default:
                    return null;
            }
            
        }
    }
};
