import java.awt.image.BufferedImage;

public class SpeedItem extends Collectable {
    private static BufferedImage icon;

    public static void setIcon(BufferedImage icon) {
        SpeedItem.icon = icon;
    }

    public SpeedItem(int row, int col) {
        super(row, col);
    }

    public void draw() {
        drawIcon(SpeedItem.icon);
    }

    public void collect() {
        Global.deleteQueue.add(this);
        Global.bomber.addEffect(new SpeedEffect());
        Collectable.collected(this);
    }

    
}
