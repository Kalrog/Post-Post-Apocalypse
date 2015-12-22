package com.game.data;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class Map implements TileBasedMap {
	/** The map size */
	public static final int MAP_SIZE = 100;

	/** The tiles that make up the map */
	Tile[][] tiles = new Tile[MAP_SIZE][MAP_SIZE];

	/** The units that are currently on the map */
	Unit[][] units = new Unit[MAP_SIZE][MAP_SIZE];
	
	Image[][] maps = new Image[Overworld.OVERWORLD_SIZE][Overworld.OVERWORLD_SIZE];

	public Map() {
	}

	Image overworldRep;

	public Image getOverworldRep() {
		return overworldRep;
	}

	/**
	 * draws the map and the units on it on the Graphics g
	 * 
	 * @param gc
	 *            The Graphics to be draw on
	 * @param zoom
	 *            The zoom that will be applied
	 */
	public void draw() {
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				float tx = Datacenter.tileX(i, j);
				float ty = Datacenter.tileY(i, j);
				if (tx > Datacenter.windowwidth + 1 * Datacenter.zoom
						|| ty < 0 - Datacenter.TILEHEIGHT * Datacenter.zoom)
					continue;
				if (ty - Datacenter.WALLHEIGHT * Datacenter.zoom > Datacenter.windowheight
						|| tx < 0 - Datacenter.TILEWIDTH * Datacenter.zoom)
					break;
				tiles[i][j].draw(tx, ty, Datacenter.zoom);
				if (units[i][j] != null)
					units[i][j].draw(tx, ty);
				tiles[i][j].drawwall(tx, ty, Datacenter.zoom);
			}
		}
	}

	public void rect(int x1, int y1, int x2, int y2, boolean set, boolean wall, Tile t) {
		/* Switch positions until y1 < y2 and x1 < x2 */
		if (y2 < y1) {
			y2 += y1;
			y1 = y2 - y1;
			y2 -= y1;
		}
		if (x2 < x1) {
			x2 += x1;
			x1 = x2 - x1;
			x2 -= x1;
		}
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				if (i < 0 || j >= MAP_SIZE)
					break;
				if (i >= MAP_SIZE || j < 0)
					continue;
				if (!wall) {
					if (tiles[i][j] == null)
						tiles[i][j] = t.getCopy();
					tiles[i][j].setDisplay(t.getDisplay());
					tiles[i][j].setLlwall(t.getLlwall());
					tiles[i][j].setLrwall(t.getLrwall());
				} else if (wall) {
					if (i == x1 && i > 0) {
						tiles[i - 1][j].setWall(Tile.WALL_LR, set);
					}
					if (j == y1 && j > 0) {
						tiles[i][j - 1].setWall(Tile.WALL_LL, set);
					}
					if (i == x2) {
						tiles[i][j].setWall(Tile.WALL_LR, set);
					}
					if (j == y2) {
						tiles[i][j].setWall(Tile.WALL_LL, set);
					}
					if (!set) {
						tiles[i][j].setWall(Tile.WALL_LR, set);
						tiles[i][j].setWall(Tile.WALL_LL, set);
					}
				}
			}
		}
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	public void setTile(int x, int y, Tile t) {
		tiles[x][y] = t;
	}

	public Unit getUnit(int x, int y) {
		return units[x][y];
	}

	public void setUnit(int x, int y, Unit u) {
		units[x][y] = u;
	}

	public void test() {
		try {
			SpriteSheet weapon = new SpriteSheet("res/img/Weapon.png", Datacenter.UNITWIDTH, Datacenter.UNITHEIGHT);
			weapon.setFilter(SpriteSheet.FILTER_NEAREST);
			SpriteSheet armor = new SpriteSheet("res/img/Armor.png", Datacenter.UNITWIDTH, Datacenter.UNITHEIGHT);
			armor.setFilter(SpriteSheet.FILTER_NEAREST);
			Image unit = new Image("res/img/Units.png");
			unit.setFilter(Image.FILTER_NEAREST);
			int x = 10;
			int y = 19;
			units[x][y] = new Unit(unit);
			units[x][y].equipitem(new Equipment(weapon.getSprite(8, 0), Equipment.SLOT_WEAPON));
			units[x][y].equipitem(new Equipment(armor.getSprite(1, 0), Equipment.SLOT_TORSO));
			units[x][y].equipitem(new Equipment(armor.getSprite(2, 0), Equipment.SLOT_TROUSERS));
			units[x][y].equipitem(new Equipment(armor.getSprite(4, 1), Equipment.SLOT_HEAD));
			units[x][y].equipitem(new Equipment(armor.getSprite(1, 1), Equipment.SLOT_SHOE));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	// Pathfinding Functions
	@Override
	public int getWidthInTiles() {
		return MAP_SIZE;
	}

	@Override
	public int getHeightInTiles() {
		return MAP_SIZE;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
	}

	@Override
	public boolean blocked(PathFindingContext context, int tx, int ty) {
		if (tx < context.getSourceX())
			return tiles[tx][ty].getWall(Tile.WALL_LR);
		if (tx > context.getSourceX())
			return tiles[context.getSourceX()][context.getSourceY()].getWall(Tile.WALL_LR);
		if (ty < context.getSourceY())
			return tiles[tx][ty].getWall(Tile.WALL_LL);
		if (ty > context.getSourceY())
			return tiles[context.getSourceX()][context.getSourceY()].getWall(Tile.WALL_LL);
		return false;
	}

	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		return 1.0f;
	}
}
