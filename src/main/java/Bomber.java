//package main.java;

import java.awt.image.BufferedImage;
import java.awt.event.*;

public class Bomber extends Animation implements KeyListener {
	// private BufferedImage sprite;
	private int vx = 0;
	private int vy = 0;

	private boolean W,A,S,D;

	private boolean boom = false;

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
			vy = -Global.playerSpeed;
		} else if (!W && S) {
			vy = Global.playerSpeed;
		} else {
			vy = 0;
		}

		if (A && !D) {
			vx = -Global.playerSpeed;
		} else if (!A && D) {
			vx = Global.playerSpeed;
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
		}

		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			boom = false;
		}

		if (W && !S) {
			vy = -Global.playerSpeed;
		} else if (!W && S) {
			vy = Global.playerSpeed;
		} else {
			vy = 0;
		}

		if (A && !D) {
			vx = -Global.playerSpeed;
		} else if (!A && D) {
			vx = Global.playerSpeed;
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
}

