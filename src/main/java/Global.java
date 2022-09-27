import java.util.Random;
import java.awt.image.BufferedImage;

public class Global {
	public static BufferedImage framebuffer;
    public static TileMap tilemap;

    public static final int scaleBy = 3;
    public static final int tileSize = 16;
    public static final int scaledSize = scaleBy * tileSize;

    public static final int playerSpeed = 14;

    public static Random rnd = new Random(0312);
}
