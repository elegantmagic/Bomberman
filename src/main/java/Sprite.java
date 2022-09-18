import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite extends Spatial implements Drawable {
	protected BufferedImage sprite;

	protected int scaleX;
	protected int scaleY;
	
	public Sprite(BufferedImage sprite) {
		super();
		this.scaleX = 1;
		this.scaleY = 1;
		this.sprite = sprite;
	}

	public Sprite(BufferedImage sprite, int x, int y) {
		super(x, y);
		this.scaleX = 1;
		this.scaleY = 1;
		this.sprite = sprite;
	}

	public Sprite(BufferedImage sprite, int x, int y, int scaleX, int scaleY) {
		super(x, y);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.sprite = sprite;
	}


	public BufferedImage getSprite() {
		return this.sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public void draw() {
        Graphics2D graphics2D = (Graphics2D) Global.framebuffer.getGraphics();
		graphics2D.drawImage(sprite, x, y, sprite.getWidth()  * scaleX, sprite.getHeight() * scaleY, null);
	}
}
