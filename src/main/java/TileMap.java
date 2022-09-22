import java.awt.image.BufferedImage;
import java.awt.*;

public class TileMap implements Drawable {
	private BufferedImage[] tileset;
	public int[] map;
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

    private int getIdx(int x, int y) {
        return x + y * this.width;
    }
    
    private int whatAt(Pair p) {
        return map[getIdx(p.x / 48, p.y / 48)];
    }

    public Pair nearestSpace(int x, int y) {
        // clockwise        upper left      upper right          lower right               lower left
        Pair[] corners = {new Pair(x, y), new Pair(x + 47, y), new Pair(x + 47, y + 47), new Pair(x, y + 47)};
        boolean[] free = {whatAt(corners[0]) == 0, whatAt(corners[1]) == 0, whatAt(corners[2]) == 0, whatAt(corners[3]) == 0};
        int n = 0;
        for (int i = 0; i < free.length; i++) {
            if (free[i]) n++;
        }
        if (n == 0 || n == 4) {
            return new Pair(x, y);
        } else if (n == 1) {
            Pair p = null; 
            for (int i = 0; i < free.length; i++) {
                if (free[i]) {
                    p = corners[i];
                    break;
                }
            }
            p.x -= p.x % 48;
            p.y -= p.y % 48;
            return p;
        } else if (n == 2) {
            Pair a = null;
            Pair b = null;
            for (int i = 0; i < free.length; i++) {
                if (free[i]) {
                    if (a == null) {
                        a = corners[i];
                    } else {
                        b = corners[i];
                        break;
                    }
                }
            }
            Pair c = corners[0];
            if (a.x == b.x) {
                c.x = a.x - (a.x % 48); 
            } else {
                c.y = a.y - (a.y % 48);
            }
            return c;
        } else {
            Pair o = null;
            if (!free[0]) {
                o = corners[2];
            } else if (!free[1]) {
                o = corners[3];
            } else if (!free[2]) {
                o = corners[0];
            } else {
                o = corners[1];
            }
            int nx = o.x - (o.x % 48);
            int ny = o.y - (o.y % 48);
            if (Math.abs(nx - corners[0].x) > Math.abs(ny - corners[0].y)) {
                corners[0].y = ny;
            } else {
                corners[0].x = nx;
            }
            return corners[0];
        }
    }

    public class Pair {
        public int x, y; 
        public Pair(int x, int y) {
            this.x = x; 
            this.y = y;
        }
    }

	
	public void draw() {
		int tsize = tileset[0].getWidth();
		for (int i = 0; i < map.length; i++) {
        	Graphics2D graphics2D = (Graphics2D) Global.framebuffer.getGraphics();
			graphics2D.drawImage(tileset[map[i]], scale * tsize * getCoordX(i), scale * tsize * getCoordY(i), tsize * scale, tsize * scale, null);
		}
	}
}
