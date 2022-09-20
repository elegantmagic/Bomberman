import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Window extends JPanel implements Runnable {
    boolean isRunning;
    Thread thread;
    public static int width = 31;
    public static int height = 13;
    int[] scene;

	private static JFrame frame;

    public Window() {
        setPreferredSize(new Dimension(width * 16 * 3, height * 16 * 3));
    }


    public static void main(String[] args) {
        frame = new JFrame("Bomberman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new Window());
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
		frame.requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            isRunning = true;
            thread.start();
        }
    }
	private BufferedImage image;
    // BufferedImage image, wall, all, block; // bomber
	private Bomber br;
	private TileMap tilemap;

    public void start() {
        try {
            image = new BufferedImage(31 * 3 * 16, 13 * 3 * 16, BufferedImage.TYPE_INT_RGB);
            BufferedImage all = ImageIO.read(Objects.requireNonNull(getClass().getResource("/image/img.png")));
			br = new Bomber();
			frame.addKeyListener(br);
			BufferedImage tileset[] = {all.getSubimage(0, 4 * 16, 16, 16), all.getSubimage(3 * 16, 3 * 16, 16, 16)};

            scene = new int[] {
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
            };

			tilemap = new TileMap(tileset, 31, 13, 3);
			tilemap.setMap(scene);
			
		

			Global.framebuffer = image;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw() {
		br.update(0.4f);


        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setColor(new Color(56, 133, 0));
        graphics2D.fillRect(0, 0, 31 * 16 * 3, 13 * 16 * 3);

		/*
        int size = 16 * 3;
        for (int i = 0; i < Window.width; i++) {
            for (int j = 0; j < Window.height; j++) {
                if (scene[j][i] == 1) {
                    graphics2D.drawImage(block, i * size, j * size, size, size, null);
                }
            }
        }*/
		tilemap.draw();
		br.draw();
        Graphics graphics = getGraphics();
        graphics.drawImage(this.image, 0, 0, 31 * 3 * 16, 13 * 3 * 16, null);
        graphics.dispose();
    }

    public void update() {

    }

    @Override
    public void run() {
        try {
            requestFocus();
            start();
            while (isRunning) {
                update();
                draw();
                Thread.sleep(1000 / 60);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

