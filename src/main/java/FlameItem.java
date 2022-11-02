import java.awt.image.BufferedImage;

public class FlameItem extends Collectable {
    private static BufferedImage icon;

    public static void setIcon(BufferedImage icon) {
        FlameItem.icon = icon;
    }

    public FlameItem(int row, int col) {
        super(row, col);
    }

    public void draw() {
        drawIcon(FlameItem.icon);
    }

    public void collect() {
        Global.deleteQueue.add(this);
        Global.bomber.addEffect(new FlameEffect());
        Collectable.collected(this);
    }

    public boolean canBeCollected(Bomber b) { return true; }
}

