import java.awt.image.BufferedImage;
import java.awt.*;

public class TileMap implements Drawable {
	private BufferedImage[] tileset;
	private int[] map;
	private int width, height;
	private int scale;
	
	public TileMap(BufferedImage[] tileset, int width, int height) {
		this.tileset = tileset;


		this.width = width; 
		this.height = height;
		this.map = new int[width * height];

		this.scale = 1;
	}
	public TileMap(BufferedImage[] tileset, int width, int height, int scale) {
		this.tileset = tileset;
		this.width = width; 
		this.height = height;
		this.map = new int[width * height];

		this.scale = scale;
	}

	public void setMap(int[] map) {
		for (int i = 0; i < Math.min(map.length, this.map.length); i++) {
			this.map[i] = map[i];
		}
	}

	private int getCoordX(int idx) {
		return idx % this.width;
	}

	private int getCoordY(int idx) {
		return idx / this.width;
	}

	
	public void draw() {
		int tsize = tileset[0].getWidth();
		for (int i = 0; i < map.length; i++) {
        	Graphics2D graphics2D = (Graphics2D) Global.framebuffer.getGraphics();
			graphics2D.drawImage(tileset[map[i]], scale * tsize * getCoordX(i), scale * tsize * getCoordY(i), tsize * scale, tsize * scale, null);
		}
	}
}
