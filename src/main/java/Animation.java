import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation extends Spatial implements Drawable, Dynamic {
    private BufferedImage[] frames;
    private float[] frameLengths;

    private int fidx;
    private float t;

    private int[] segmentStarts;
    private int[] segmentLenghts;

    private int segment;

    public Animation(BufferedImage[] frames, float[] frameLengths, int[] segmentStarts, int[] segmentLenghts) {
        this.frames = frames;
        this.frameLengths = frameLengths;

        this.segmentStarts = segmentStarts; 
        this.segmentLenghts = segmentLenghts;

        this.segment = 0;
        this.fidx = 0;
        this.t = 0.0f;
        assert(frames.length == frameLengths.length && frameLengths.length == segmentStarts.length && segmentStarts.length == segmentStarts.length);
    }

    public void update(float delta) {
        t += delta;
        while (t > frameLengths[fidx]) {
            t -= frameLengths[fidx];
            fidx++;
            if (fidx >= segmentLenghts[segment] + segmentStarts[segment]) {
                fidx = segmentStarts[segment];
            }
        }
    }


    
    public void draw() {
        Graphics2D graphics2D = (Graphics2D) Global.framebuffer.getGraphics();
		graphics2D.drawImage(frames[fidx], x, y, frames[fidx].getWidth()  * Global.scaleBy, frames[fidx].getHeight() * Global.scaleBy, null);
    }  


    
    /**
     * Get segment.
     * @return segment as int.
     */
    public int getSegment() {
        return segment;
    }
    
    /**
     * Set segment.
     *
     * @param segment the value to set.
     */
    public void setSegment(int segment) {
        this.segment = segment;
    }
    
    /**
     * Get fidx.
     * @return fidx as int.
     */
    public int getFidx() {
        return fidx;
    }
    
    /**
     * Set fidx.
     *
     * @param fidx the value to set.
     */
    public void setFidx(int fidx) {
        this.fidx = fidx;
    }
}
