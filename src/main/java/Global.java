import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Global {
	public static BufferedImage framebuffer;
    public static TileMap tilemap;

    public static final int scaleBy = 3;
    public static final int tileSize = 16;
    public static final int playerSpeed = 14;
}
