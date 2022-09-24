import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation extends Spatial implements Drawable, Dynamic {
    private BufferedImage[] frames;
    private float[] frameLengths;

    private int fidx;
    private float t;
    public Animation(BufferedImage[] frames, float[] frameLengths) {
        this.frames = frames;
        this.frameLengths = frameLengths;

        this.fidx = 0;
        this.t = 0.0f;
        assert(frames.length == frameLengths.length);
    }

    public void update(float delta) {
        t += delta;
        while (t > frameLengths[fidx]) {
            t -= frameLengths[fidx];
            fidx++;
            if (fidx >= frameLengths.length) {
                fidx = 0;
            }
        }
    }
    
    public void draw() {
        Graphics2D graphics2D = (Graphics2D) Global.framebuffer.getGraphics();
		graphics2D.drawImage(frames[fidx], x, y, frames[fidx].getWidth()  * Global.scaleBy, frames[fidx].getHeight() * Global.scaleBy, null);
    }
    


}
