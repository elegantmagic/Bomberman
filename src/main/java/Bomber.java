import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Objects;
import java.awt.event.*;

public class Bomber extends Sprite implements KeyListener {
	// private BufferedImage sprite;
	private int vx = 0; 
	private int vy = 0;

	private boolean W,A,S,D;

	public Bomber(BufferedImage sprite) {
		super(sprite, 16 * 3, 16 * 3, 3);
	}

	public Bomber(BufferedImage sprite, int x, int y) {
		super(sprite, x, y, 3);
	}

	public void update(float delta) {
		x += vx * delta; 
		y += vy * delta;

        System.out.printf("%d %d\n", (x / 48), (y / 48));
	}

	public void keyPressed(KeyEvent e) {
		// System.out.println(e.getKeyCode() == KeyEvent.VK_E);
		if (e.getKeyCode() == KeyEvent.VK_W) {
			W = true;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			S = true;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			A = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			D = true;
		}

		if (W && !S) {
			vy = -14;
		} else if (!W && S) {
			vy = 14;
		} else {
			vy = 0;
		}

		if (A && !D) {
			vx = -14;
		} else if (!A && D) {
			vx = 14;
		} else {
			vx = 0;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			W = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			S = false;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			A = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			D = false;
		}

		if (W && !S) {
			vy = -10;
		} else if (!W && S) {
			vy = 10;
		} else {
			vy = 0;
		}

		if (A && !D) {
			vx = -10;
		} else if (!A && D) {
			vx = 10;
		} else {
			vx = 0;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}
}

