//package main.java;

import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.event.*;

public class Bomber extends Animation implements KeyListener, BombPlanter {
	private int vx = 0;
	private int vy = 0;

	private boolean W,A,S,D;

    private boolean canPlantBomb = true;

	private boolean boom = false;
    
    public int runSpeed;
    public int bombRadius;

    private List<ItemEffect> effects = new ArrayList<ItemEffect>();
    private boolean allowedPlantingBomb = true;


    public Bomber(BufferedImage all) {
        BufferedImage[] frames = new BufferedImage[12];
        float[] frameLengths = new float[12];
        for (int i = 0; i < frames.length; i++) {
            frameLengths[i] = 0.5f;

            int x = i % 6;
            int y = i / 6;
            frames[i] = all.getSubimage(Global.tileSize * x, Global.tileSize * y, Global.tileSize, Global.tileSize);    
        }
        this.setFrames(frames);
        this.setFrameLengths(frameLengths);

        int[] segmentStarts = new int[4];
        int[] segmentLengths = new int[4];
        for (int i = 0; i < 4; i++) {
            segmentStarts[i] = 3 * i;
            segmentLengths[i] = 3;
        }

        this.setSegmentLenghts(segmentLengths);
        this.setSegmentStarts(segmentStarts);

        x = Global.tileSize * Global.scaleBy;
        y = Global.tileSize * Global.scaleBy;

        runSpeed = Global.playerSpeed;
        bombRadius = Global.expRad;
    }

	public void update(float delta) {
        super.update(delta);
        TileMap.Pair p = Global.tilemap.nearestSpace(x + (int)(vx * delta), y + (int)(vy * delta));
        int dx = p.x - x;
        int dy = p.y - y;
        x = p.x;
        y = p.y;

        if (dx == 0 && dy == 0) {
            this.setAnimate(false);
            this.resetCycle();
        } else {
            if (dx < 0) {
                this.setSegment(0);
            } else if (dx > 0) {
                this.setSegment(2);
            } else if (dy < 0) {
                this.setSegment(3);
            } else if (dy > 0) {
                this.setSegment(1);
            }
            this.setAnimate(true);
        }
    

        Iterator<ItemEffect> it = effects.iterator();
        while (it.hasNext()) {
            ItemEffect e = it.next();
            if (e.checkTime(this, delta)) 
                it.remove();
        }

        Collectable e = Collectable.maps[x / Global.scaledSize][y / Global.scaledSize];
        if (e != null) {
            e.collect();
        }
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
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE && canPlantBomb && allowedPlantingBomb) {
            int x_ = x - (x % Global.scaledSize);
            int y_ = y - (y % Global.scaledSize);
            canPlantBomb = false;
            Bomb.plantBomb(x_, y_, bombRadius, this);
            allowedPlantingBomb = false;
        }

        


		if (W && !S) {
			vy = -runSpeed;
		} else if (!W && S) {
			vy = runSpeed;
		} else {
			vy = 0;
		}

		if (A && !D) {
			vx = -runSpeed;
		} else if (!A && D) {
			vx = runSpeed;
		} else {
			vx = 0;
		}

		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			boom = true;
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
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE && !canPlantBomb) {
            canPlantBomb = true;
        }

		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			boom = false;
		}

		if (W && !S) {
			vy = -runSpeed;
		} else if (!W && S) {
			vy = runSpeed;
		} else {
			vy = 0;
		}

		if (A && !D) {
			vx = -runSpeed;
		} else if (!A && D) {
			vx = runSpeed;
		} else {
			vx = 0;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public int getVx() {
		return vx + 24;
	}

	public int getVy() {
		return vy + 24;
	}

	public boolean isBoom() {
		return boom;
	}

    public void addEffect(ItemEffect e) {
        e.effectBomber(this);
        effects.add(e);
    }

    public void allowPlantingBomb() {
        allowedPlantingBomb = true;
    }
}

