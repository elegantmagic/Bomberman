//package main.java;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;

public class TileMap implements Drawable {
	private BufferedImage[] tileset;
	public int[][] map;
    public int[][] collectableMap;
    public int[][] enemyMap;
	private int width;
    private int height;

    private int levelNum;
    public int nbrick = 0;

    public TileMap(BufferedImage[] tileset, String level) throws IOException {
        BufferedReader levelReader = new BufferedReader(new FileReader(level));
        String[] rawMetadata = levelReader.readLine().split(" ");
        assert(rawMetadata.length == 3);
        
        levelNum = Integer.parseInt(rawMetadata[0]); 
        height = Integer.parseInt(rawMetadata[1]);
        width = Integer.parseInt(rawMetadata[2]);

        map = new int[width][height];
        collectableMap = new int[width][height];
        enemyMap = new int[width][height];
        for (int row = 0; row < height; row++) {
            String r = levelReader.readLine();
            for (int col = 0; col < width; col++) {
                collectableMap[col][row] = -1; // Nothing
                enemyMap[col][row] = 0;
                if(r.charAt(col) == '#') {
                    map[col][row] = 1;
                } else if(r.charAt(col) == ' '){
                    map[col][row] = 0;
                } else if(r.charAt(col) == '*') {
                    map[col][row] = 2;
                    nbrick++;
                } else if (r.charAt(col) == 'x') {
                    map[col][row] = 2;
                    nbrick++;
                    collectableMap[col][row] = 0; // For portal
                } else if (r.charAt(col) == 'b') {
                    map[col][row] = 2;
                    nbrick++;
                    collectableMap[col][row] = 1; // For bombitem
                } else if (r.charAt(col) == 'f') {
                    map[col][row] = 2;
                    nbrick++;
                    collectableMap[col][row] = 2; // For flameitem
                } else if (r.charAt(col) == 's') {
                    map[col][row] = 2;
                    nbrick++;
                    collectableMap[col][row] = 3; // For speeditem
                } else if (r.charAt(col) == 'p') {
                    map[col][row] = 0;
                    Global.bomber.setX(Global.scaledSize * col);
                    Global.bomber.setY(Global.scaledSize * row);
                } else if (r.charAt(col) == '1') {
                    map[col][row] = 0;
                    enemyMap[col][row] = 1;
                } else if (r.charAt(col) == '2') {
                    map[col][row] = 0;
                    enemyMap[col][row] = 2;
                } else if (r.charAt(col) == '3') {
                    map[col][row] = 0;
                    enemyMap[col][row] = 3;
                } else if (r.charAt(col) == '4') {
                    map[col][row] = 0;
                    enemyMap[col][row] = 4;
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

    public Pair randomFreeSpace() {
        int x = Global.rnd.nextInt(0, width);
        int y = Global.rnd.nextInt(0, height);
        while (map[x][y] != 0) {
            x = Global.rnd.nextInt(0, width);
            y = Global.rnd.nextInt(0, height);        
        }
        return new Pair(x, y);
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

        public int hashCode() {
            return (x * 3134 - y * 11 * y) % 322;
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

    public void spawnEnemy() {
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (enemyMap[col][row] == 1) {
                    Global.addQueue.add(Balloom.newBalloom(col * Global.scaledSize, row * Global.scaledSize));
                } else if (enemyMap[col][row] == 2) {
                    Global.addQueue.add(Oneal.newOneal(col * Global.scaledSize, row * Global.scaledSize));
                } else if (enemyMap[col][row] == 3) {
                    Global.addQueue.add(OtherEnemy.newOtherEnemy(col * Global.scaledSize, row * Global.scaledSize));
                } else if (enemyMap[col][row] == 4) {
                    Global.addQueue.add(MoreEnemy.newMoreEnemy(col * Global.scaledSize, row * Global.scaledSize));
                }
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
