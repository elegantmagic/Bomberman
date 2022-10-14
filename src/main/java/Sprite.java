//package main.java;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite extends Spatial implements Drawable {
	protected BufferedImage sprite;
	
    public Sprite() {

    }

	public Sprite(BufferedImage sprite) {
		super();
		this.sprite = sprite;
	}

	public Sprite(BufferedImage sprite, int x, int y) {
		super(x, y);
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
		graphics2D.drawImage(sprite, x, y, sprite.getWidth()  * Global.scaleBy, sprite.getHeight() * Global.scaleBy, null);
	}
}
