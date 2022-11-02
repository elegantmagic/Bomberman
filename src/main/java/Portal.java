import java.awt.image.BufferedImage;

public class Portal extends Collectable {
    private static BufferedImage icon;
    public static boolean exist = false;

    public static void setIcon(BufferedImage icon) {
        Portal.icon = icon;
    }

    public Portal(int row, int col) {
        super(row, col);
        exist = true;
    }

    public void draw() {
        drawIcon(Portal.icon);
    }

    public void collect() {
        exist = false;
        Global.nextlev = true;
    }

    public boolean canBeCollected(Bomber b) { return Global.nEnemy == 0; }

}

