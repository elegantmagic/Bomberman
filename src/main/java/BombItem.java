import java.awt.image.BufferedImage;

public class BombItem extends Collectable {
    private static BufferedImage icon;

    public static void setIcon(BufferedImage icon) {
        BombItem.icon = icon;
    }

    public BombItem(int row, int col) {
        super(row, col);
    }

    public void draw() {
        drawIcon(BombItem.icon);
    }

    public void collect() {
        Global.bomber.allowPlantingBomb();
        Collectable.collected(this);
    }
}

