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

    public static Queue<Object> addQueue = new ArrayDeque<Object>();
    public static Queue<Object> deleteQueue = new ArrayDeque<Object>();
}
