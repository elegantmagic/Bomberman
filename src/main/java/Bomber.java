import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Objects;
import java.awt.event.*;

public class Bomber extends Sprite implements KeyListener {
	// private BufferedImage sprite;
	private int vx = 0; 
	private int vy = 0;
	public Bomber() {
		super(null,  16 * 3, 16 * 3, 3);
		try {
        	BufferedImage all = ImageIO.read(Objects.requireNonNull(getClass().getResource("/image/img.png")));
			this.sprite = all.getSubimage(4 * 16, 0, 16, 16);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Bomber(BufferedImage sprite) {
		super(sprite, 16 * 3, 16 * 3, 3);
	}

	public Bomber(BufferedImage sprite, int x, int y) {
		super(sprite, x, y, 3);
	}

	public void update(float delta) {
		x += vx * delta; 
		y += vy * delta;
	}

	public void keyPressed(KeyEvent e) {
		// System.out.println(e.getKeyCode() == KeyEvent.VK_E);
		if (e.getKeyCode() == KeyEvent.VK_W) {
			vy = -10;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			vy = 10; 
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			vx = -10;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			vx = 10;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			vy = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			vy = 0; 
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			vx = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			vx = 0;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}
}

