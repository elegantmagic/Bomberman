//package main.java;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;

public class TileMap implements Drawable {
	private BufferedImage[] tileset;
	public int[][] map;
	private int width;
    private int height;

    private int levelNum;

    public TileMap(BufferedImage[] tileset, String level) throws IOException {
        BufferedReader levelReader = new BufferedReader(new FileReader(level));
        String[] rawMetadata = levelReader.readLine().split(" ");
        assert(rawMetadata.length == 3);
        
        levelNum = Integer.parseInt(rawMetadata[0]); 
        height = Integer.parseInt(rawMetadata[1]);
        width = Integer.parseInt(rawMetadata[2]);

        map = new int[width][height];
        for (int row = 0; row < height; row++) {
            String r = levelReader.readLine();
            for (int col = 0; col < width; col++) {
                if(r.charAt(col) == '#') {
                    map[col][row] = 1;
                } else if(r.charAt(col) == ' '){
                    map[col][row] = 0;
                } else if(r.charAt(col) == '*') {
                    map[col][row] = 2;
                }
            }
        }

        levelReader.close();
        this.tileset = tileset;
    }
	
	public TileMap(BufferedImage[] tileset, int width, int height) {
		this.tileset = tileset;


		this.width = width; 
		this.height = height;
		this.map = new int[width][height];

	}

	public void setMap(int x, int y, int index) {
		map[x][y] = index;
	}
  
    private int whatAt(Pair p) {
        return map[p.x / Global.scaledSize][p.y / Global.scaledSize];
    }

    public Pair nearestSpace(int x, int y) {
        final int scaledSize = Global.tileSize * Global.scaleBy;
        // clockwise        upper left      upper right          lower right               lower left
        Pair[] corners = {new Pair(x, y), new Pair(x + scaledSize - 1, y), new Pair(x + scaledSize - 1, y + scaledSize - 1), new Pair(x, y + scaledSize - 1)};
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
            p.x -= p.x % scaledSize;
            p.y -= p.y % scaledSize;
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
                c.x = a.x - (a.x % scaledSize); 
            } else if (a.y == b.y) {
                c.y = a.y - (a.y % scaledSize);
            } else {
                c.x = (a.x + b.x) / 2;
                c.y = (a.y + b.y) / 2;
                c.x -= c.x % scaledSize;
                c.y -= c.y % scaledSize;
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
            int nx = o.x - (o.x % scaledSize);
            int ny = o.y - (o.y % scaledSize);
            if (Math.abs(nx - corners[0].x) > Math.abs(ny - corners[0].y)) {
                corners[0].y = ny;
            } else {
                corners[0].x = nx;
            }
            return corners[0];
        }
    }

    public boolean isFreeSpace(Pair position) {
        return map[position.x / Global.scaledSize][position.y / Global.scaledSize] == 0;
    }

    public static class Pair {
        public int x, y; 
        public Pair() {
        }
        public Pair(int x, int y) {
            this.x = x; 
            this.y = y;
        }

        public boolean equals(Object other) {
            if (other instanceof Pair) {
                Pair o = (Pair)other;
                return x == o.x && y == o.y;
            } else {
                return false;
            }
        }
    }

	
	public void draw() {
        int scaledSize = Global.tileSize * Global.scaleBy;
		for (int i = 0; i < map.length; i++) {
		}
        Graphics2D graphics2D = (Graphics2D) Global.framebuffer.getGraphics();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
			    graphics2D.drawImage(tileset[map[x][y]], scaledSize * x, scaledSize * y, scaledSize, scaledSize, null);
            }
        }
	}
    
    /**
     * Get levelNum.
     *
     * @return levelNum as int.
     */
    public int getLevelNum()
    {
        return levelNum;
    }
	
	/**
	 * Get width.
	 *
	 * @return width as int.
	 */
	public int getWidth()
	{
	    return width;
	}
    
    /**
     * Get height.
     *
     * @return height as int.
     */
    public int getHeight()
    {
        return height;
    }
}
