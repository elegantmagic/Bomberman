package main.java;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;

public class Window extends JPanel implements Runnable {
    boolean isRunning;
    Thread thread;
    public static int width = 31;
    public static int height = 13;

    private static JFrame frame;

    private int index = 0;
    private int frameBoom = 0;
    private int interval = 7;
    private boolean boom = false;
    private int boomX, boomY;

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
    private Balloom test;

    public void start() {
        try {
            BufferedImage all = ImageIO.read(new File("image/img.png"));

            br = new Bomber(all);
            frame.addKeyListener(br);
            BufferedImage tileset[] = {all.getSubimage(0, 4 * 16, 16, 16),
                    all.getSubimage(3 * 16, 3 * 16, 16, 16),
                    all.getSubimage(4 * 16, 3 * 16, 16, 16),
                    all.getSubimage(0 * 16, 3 * 16, 16, 16),
                    all.getSubimage(1 * 16, 3 * 16, 16, 16),
                    all.getSubimage(2 * 16, 3 * 16, 16, 16)};
            tilemap = new TileMap(tileset, "Level2.txt");

            Global.tilemap = tilemap;
            test = new Balloom(all, new TileMap.Pair(Global.scaledSize, Global.scaledSize * 2));

            image = new BufferedImage(tilemap.getWidth() * 3 * 16, tilemap.getHeight() * 3 * 16, BufferedImage.TYPE_INT_RGB);
            Global.framebuffer = image;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw() {


        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setColor(new Color(56, 133, 0));
        graphics2D.fillRect(0, 0, 31 * 16 * 3, 13 * 16 * 3);

        tilemap.draw();
        br.draw();
        test.draw();

        Graphics graphics = getGraphics();
        graphics.drawImage(this.image, 0, 0, 31 * 3 * 16, 13 * 3 * 16, null);
        graphics.dispose();
    }

    public void update() {
        br.update(0.4f);
        test.update(0.4f);

        if(br.isBoom()){
            boom = true;
            boomX = br.getVx()/48;
            boomY = br.getVy()/48;
        }

        if (boom) {
            frameBoom++;
            if (frameBoom > interval) {
                frameBoom = 0;
                this.index = (++this.index) % 3;
            }

            tilemap.setMap(boomX, boomY, index+3);

        }

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









            /*
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
            
			tilemap = new TileMap(tileset, 31, 13);
			tilemap.setMap(scene);
			*/

