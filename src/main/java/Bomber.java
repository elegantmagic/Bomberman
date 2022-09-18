import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Objects;

public class Bomber extends Sprite{
	// private BufferedImage sprite;

	public Bomber() {
		super(null,  16 * 3, 16 * 3, 3, 3);
		try {
        	BufferedImage all = ImageIO.read(Objects.requireNonNull(getClass().getResource("/image/img.png")));
			this.sprite = all.getSubimage(4 * 16, 0, 16, 16);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Bomber(BufferedImage sprite) {
		super(sprite, 16 * 3, 16 * 3, 3, 3);
	}

	public Bomber(BufferedImage sprite, int x, int y) {
		super(sprite, x, y, 3, 3);
	}
}

