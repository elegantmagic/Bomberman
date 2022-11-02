//package main.java;

import java.util.*;
import java.awt.image.BufferedImage;

public class Global {
	public static BufferedImage framebuffer;
    public static TileMap tilemap;

    public static final int scaleBy = 3;
    public static final int tileSize = 16;
    public static final int scaledSize = scaleBy * tileSize;

    public static final int playerSpeed = 25;
    public static final int expRad = 4;

    public static Random rnd = new Random(0312);
    public static Bomber bomber;

    public static List<Drawable> drawables = new ArrayList<Drawable>(64);
    public static List<Dynamic> dynamics = new ArrayList<Dynamic>(64);

    public static BufferedImage all;

    public static BufferedImage StartMenu;
    public static Queue<Object> addQueue = new ArrayDeque<Object>();
    public static Queue<Object> deleteQueue = new ArrayDeque<Object>();

    public static SpaceSearch ss;
    public static int nEnemy = 0;

    public static LogicMap distToBomber;
    public static LogicMap bombBlast;

    public static boolean nextlev = false;
    public static int level = 1;
}
