//package main.java;

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

    private boolean animate;
    public Animation() {
        frames = null;
        frameLengths = null;
        segmentLenghts = null;
        segmentStarts = null;

        fidx = 0;
        t = 0.0f;
        segment = 0;
        animate = true;
    }
    public Animation(BufferedImage[] frames, float[] frameLengths, int[] segmentStarts, int[] segmentLenghts) {
        this.frames = frames;
        this.frameLengths = frameLengths;

        this.segmentStarts = segmentStarts; 
        this.segmentLenghts = segmentLenghts;

        this.segment = 0;
        this.fidx = 0;
        this.t = 0.0f;
        this.animate = true;
        assert(frames.length == frameLengths.length && frameLengths.length == segmentStarts.length && segmentStarts.length == segmentStarts.length);
    }

    public void update(float delta) {
        if (!animate) return;
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

    public void resetCycle() {
        fidx = segmentStarts[segment];
    }
    
    
    /**
     * Get frames.
     *
     * @return frames as BufferedImage[].
     */
    public BufferedImage[] getFrames()
    {
        return frames;
    }
    
    /**
     * Get frames element at specified index.
     *
     * @param index the index.
     * @return frames at index as BufferedImage.
     */
    public BufferedImage getFrames(int index)
    {
        return frames[index];
    }
    
    /**
     * Set frames.
     *
     * @param frames the value to set.
     */
    public void setFrames(BufferedImage[] frames)
    {
        this.frames = frames;
    }
    
    /**
     * Set frames at the specified index.
     *
     * @param frames the value to set.
     * @param index the index.
     */
    public void setFrames(BufferedImage frames, int index)
    {
        this.frames[index] = frames;
    }
    
    /**
     * Get frameLengths.
     *
     * @return frameLengths as float[].
     */
    public float[] getFrameLengths()
    {
        return frameLengths;
    }
    
    /**
     * Get frameLengths element at specified index.
     *
     * @param index the index.
     * @return frameLengths at index as float.
     */
    public float getFrameLengths(int index)
    {
        return frameLengths[index];
    }
    
    /**
     * Set frameLengths.
     *
     * @param frameLengths the value to set.
     */
    public void setFrameLengths(float[] frameLengths)
    {
        this.frameLengths = frameLengths;
    }
    
    /**
     * Set frameLengths at the specified index.
     *
     * @param frameLengths the value to set.
     * @param index the index.
     */
    public void setFrameLengths(float frameLengths, int index)
    {
        this.frameLengths[index] = frameLengths;
    }
    
    /**
     * Get fidx.
     *
     * @return fidx as int.
     */
    public int getFidx()
    {
        return fidx;
    }
    
    /**
     * Set fidx.
     *
     * @param fidx the value to set.
     */
    public void setFidx(int fidx)
    {
        this.fidx = fidx;
    }
    
    /**
     * Get t.
     *
     * @return t as float.
     */
    public float getT()
    {
        return t;
    }
    
    /**
     * Set t.
     *
     * @param t the value to set.
     */
    public void setT(float t)
    {
        this.t = t;
    }
    
    /**
     * Get segmentStarts.
     *
     * @return segmentStarts as int[].
     */
    public int[] getSegmentStarts()
    {
        return segmentStarts;
    }
    
    /**
     * Get segmentStarts element at specified index.
     *
     * @param index the index.
     * @return segmentStarts at index as int.
     */
    public int getSegmentStarts(int index)
    {
        return segmentStarts[index];
    }
    
    /**
     * Set segmentStarts.
     *
     * @param segmentStarts the value to set.
     */
    public void setSegmentStarts(int[] segmentStarts)
    {
        this.segmentStarts = segmentStarts;
    }
    
    /**
     * Set segmentStarts at the specified index.
     *
     * @param segmentStarts the value to set.
     * @param index the index.
     */
    public void setSegmentStarts(int segmentStarts, int index)
    {
        this.segmentStarts[index] = segmentStarts;
    }
    
    /**
     * Get segmentLenghts.
     *
     * @return segmentLenghts as int[].
     */
    public int[] getSegmentLenghts()
    {
        return segmentLenghts;
    }
    
    /**
     * Get segmentLenghts element at specified index.
     *
     * @param index the index.
     * @return segmentLenghts at index as int.
     */
    public int getSegmentLenghts(int index)
    {
        return segmentLenghts[index];
    }
    
    /**
     * Set segmentLenghts.
     *
     * @param segmentLenghts the value to set.
     */
    public void setSegmentLenghts(int[] segmentLenghts)
    {
        this.segmentLenghts = segmentLenghts;
    }
    
    /**
     * Set segmentLenghts at the specified index.
     *
     * @param segmentLenghts the value to set.
     * @param index the index.
     */
    public void setSegmentLenghts(int segmentLenghts, int index)
    {
        this.segmentLenghts[index] = segmentLenghts;
    }
    
    /**
     * Get segment.
     *
     * @return segment as int.
     */
    public int getSegment()
    {
        return segment;
    }
    
    /**
     * Set segment.
     *
     * @param segment the value to set.
     */
    public void setSegment(int segment)
    {
        this.segment = segment;
    }
    
    /**
     * Get animate.
     *
     * @return animate as boolean.
     */
    public boolean getAnimate()
    {
        return animate;
    }
    
    /**
     * Set animate.
     *
     * @param animate the value to set.
     */
    public void setAnimate(boolean animate)
    {
        this.animate = animate;
    }
}
