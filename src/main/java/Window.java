//package main.java;

import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import java.awt.event.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;

import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class Window extends JPanel implements Runnable {
    boolean isRunning;
    Thread thread;
    public static int width = 31;
    public static int height = 13;

    private boolean startScreen = false;

    private static JFrame frame;

    private int index = 0;
    private int frameBoom = 0;
    private int interval = 7;
    private boolean boom = false;
    private int boomX, boomY;

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private BufferedImage[] tileset;


    public Window() {
        setPreferredSize(new Dimension(width * 16 * 3, height * 16 * 3));
        prepareGUI();
    }
    private void prepareGUI() {

    }
    /*
    class CustomMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            if(758 <= e.getX() && e.getX() < 927 && 495 < e.getY() && e.getY() < 527) {
                startScreen = true;
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }
    */

    class CustomKeyListener implements KeyListener {
        public void keyReleased(KeyEvent e) {

        }

        public void keyTyped(KeyEvent e) {

        }

        public void keyPressed(KeyEvent e) {
            startScreen = true;
        }
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

        Thread playMusic = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    do {
                        FileInputStream file = new FileInputStream("music.mp3");
                        AdvancedPlayer play = new AdvancedPlayer(file);
                        play.play();
                    } while (true);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        playMusic.start();
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
    private Bomber bomber;
    private TileMap tilemap;
    private BufferedImage StartMenu;
    public void start() {
        try {
            StartMenu = ImageIO.read(new File("image/start.jpg"));
            Global.StartMenu = StartMenu;
            BufferedImage all = ImageIO.read(new File("image/img.png"));

            Global.all = all;

			bomber = new Bomber(all);
            Global.bomber = bomber;

            frame.addKeyListener(bomber);
            frame.addKeyListener(new CustomKeyListener());
            BufferedImage tileset[] = {
                    all.getSubimage(0, 4 * 16, 16, 16),
                    all.getSubimage(3 * 16, 3 * 16, 16, 16),
                    all.getSubimage(4 * 16, 3 * 16, 16, 16),
                    all.getSubimage(0 * 16, 3 * 16, 16, 16),
                    all.getSubimage(1 * 16, 3 * 16, 16, 16),
                    all.getSubimage(2 * 16, 3 * 16, 16, 16)};
            this.tileset = tileset;
            Global.level = 1;
            tilemap = new TileMap(tileset, "Level1.txt");
            Global.tilemap = tilemap;
            Global.ss = new SpaceSearch();
            SpaceSearch.setup(tilemap);
            tilemap.spawnEnemy();

            Collectable.setTilemap(Global.tilemap);
            
            BombItem.setIcon(all.getSubimage(0* Global.tileSize, 14 * Global.tileSize, Global.tileSize, Global.tileSize));
            FlameItem.setIcon(all.getSubimage(1* Global.tileSize, 14 * Global.tileSize, Global.tileSize, Global.tileSize));
            SpeedItem.setIcon(all.getSubimage(2 * Global.tileSize, 14 * Global.tileSize, Global.tileSize, Global.tileSize));

            Portal.setIcon(all.getSubimage(11 * Global.tileSize, 3 * Global.tileSize, Global.tileSize, Global.tileSize));







            image = new BufferedImage(tilemap.getWidth() * 3 * 16, tilemap.getHeight() * 3 * 16, BufferedImage.TYPE_INT_RGB);
            Global.framebuffer = image;


            



            Global.distToBomber = new BomberDijkstra(tilemap);
            Global.bombBlast = new BlastMap(tilemap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw() {
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setColor(new Color(56, 133, 0));
        graphics2D.fillRect(0, 0, tilemap.getWidth() * Global.scaledSize, tilemap.getHeight() * Global.scaledSize);

        if(!startScreen) {
            graphics2D.drawImage(StartMenu, 0, 0, tilemap.getWidth() * Global.scaledSize, tilemap.getHeight() * Global.scaledSize, null);
        } else {
            tilemap.draw();
            bomber.draw();
            for (Drawable d : Global.drawables) {
                d.draw();
            }
        }

        Graphics graphics = getGraphics();
        graphics.drawImage(this.image, 0, 0, 31 * 3 * 16, 13 * 3 * 16, null);
        graphics.dispose();
    }

    public void update() {
        if (!startScreen) return;
        while (!Global.addQueue.isEmpty()) {
            Object o = Global.addQueue.poll();
            if (o instanceof Drawable)
                Global.drawables.add((Drawable)o);
            if (o instanceof Dynamic)
                Global.dynamics.add((Dynamic)o);
        }

        while (!Global.deleteQueue.isEmpty()) {
            Object o = Global.deleteQueue.poll();
            if (o instanceof Drawable)
                Global.drawables.remove(o);
            if (o instanceof Dynamic)
                Global.dynamics.remove(o);
            if (o instanceof Oneal || o instanceof Balloom) {
                Global.nEnemy--;
                SpaceSearch.remove((Spatial)o);
            }
        }
        bomber.update(0.18f);
        Global.distToBomber.update();
        for (Dynamic d : Global.dynamics) {
            d.update(0.18f);
        }
        Global.distToBomber.update();
        
        if (Global.nextlev) {
            Global.nextlev = false;
            Global.level++;
            try {
                String nextlevfilename = String.format("Level%d.txt", Global.level);
                tilemap = new TileMap(tileset, nextlevfilename);
                SpaceSearch.reset(); 
                TileMap.Pair bp = tilemap.randomFreeSpace();
                bomber.setX(bp.x * Global.scaledSize);
                bomber.setY(bp.y * Global.scaledSize);
            } catch (Exception e) {
                isRunning = false;
                System.out.println("CONGRAT, YOU'VE WON!");
                // e.printStackTrace();
            }


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






