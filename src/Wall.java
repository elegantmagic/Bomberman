import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Wall extends JPanel {
    BufferedImage image;
    public void draw() {

        this.image = new BufferedImage(31*3*16, 13*3*16, BufferedImage.TYPE_INT_RGB);
        BufferedImage s = null;
        try{
            s = ImageIO.read(getClass().getResource("/image/wall/Wall.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Graphics2D graphics2D = (Graphics2D) this.image.getGraphics();
        graphics2D.setBackground(new Color(56, 133, 0));
        graphics2D.fillRect(0, 0, 31*3*16,13*3*16);
        graphics2D.drawImage(s,0, 0, 16*3, 16*3, null);

            Graphics graphics = getGraphics();
            graphics.drawImage(this.image, 0, 0, 31*3*16, 13*3*16, null);
            graphics.dispose();
    }

}
