package com.game.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Datacenter {
	public static final boolean LOG = true;

	public static final String PATH_WALL = "res/img/Walls.png";

	public static final String PATH_TILE = "res/img/Tiles.png";

	public static final String PATH_DATA = "res/data/Tiles.dat";

	public static final String PATH_UNIT = "res/img/Units.png";

	public static float xoffset;

	public static float yoffset;

	public static int windowwidth;

	public static int windowheight;

	public static float zoom;

	public static final int tilewidth = 14;

	public static final int tileheight = 8;

	public static final int wallwidth = 8;

	public static final int wallheight = 25;

	public static List<Tile> Tilelist;

	public static final int unitwidth = 14;

	public static final int unitheight = 25;

	public static void setup(int x, int y, int ww, int wh, int z) {
		xoffset = x;
		yoffset = y;
		windowwidth = ww;
		windowheight = wh;
		zoom = z;
	}

	public static void createTiles() {
		try {
			Tilelist = new ArrayList<Tile>();
			SpriteSheet tiles = new SpriteSheet(PATH_TILE, tilewidth, tileheight);
			tiles.setFilter(SpriteSheet.FILTER_NEAREST);
			SpriteSheet walls = new SpriteSheet(PATH_WALL, wallwidth, wallheight);
			walls.setFilter(SpriteSheet.FILTER_NEAREST);
			Scanner in = new Scanner(new File(PATH_DATA));
			String line;
			String tag;
			int x = 0, wx = 0;
			while (in.hasNext()) {
				line = in.nextLine();
				if (line.contains("(")) {
					while (!line.contains(")")) {
						line = in.nextLine();
						tag = line.split(":")[0];
						if (tag.equals("wx"))
							wx = Integer.valueOf(line.split(":")[1]);
						if (tag.equals("x"))
							x = Integer.valueOf(line.split(":")[1]);
					}
					Tile t = new Tile(tiles.getSprite(x, 0));
					t.setLrwall(walls.getSprite(wx, 0));
					t.setLlwall(walls.getSprite(wx, 1));
					Tilelist.add(t);
					if (LOG)
						System.out.println("created Tile " + x + " " + wx);
				}
			}
			in.close();
		} catch (SlickException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static int tileX(int x, int y) {
		return (int) (windowwidth / 2 + (xoffset + (x - y) * (tilewidth+2) / 2 ) * zoom);
	}

	public static int tileY(int x, int y) {
		return (int) (windowheight / 2 + (yoffset + (x + y) * tileheight / 2) * zoom);
	}

	public static int[] tile(int x, int y) {
		return new int[] { tileX(x, y), tileY(x, y) };
	}

	public static int tileatX(float mx, float my) {
		return Math.round(((my - windowheight / 2 - yoffset * zoom) / tileheight + (mx - windowwidth / 2 - xoffset * zoom) / (tilewidth+2)) / zoom - 1);
	}

	public static int tileatY(float mx, float my) {
		return Math.round(((my - windowheight / 2 - yoffset * zoom) / tileheight - (mx - windowwidth / 2 - xoffset * zoom) / (tilewidth+2)) / zoom);
	}

	public static int[] tileat(float mx, float my) {
		return new int[] { tileatX(mx, my), tileatY(mx, my) };
	}
}
