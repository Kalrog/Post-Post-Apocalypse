import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

class Map implements TileBasedMap {
	/** The map size */
	public static final int MAP_SIZE = 100;
	/** The tiles that make up the map */
	Tile[][] tiles = new Tile[MAP_SIZE][MAP_SIZE];
	/** The units that are currently on the map */
	Unit[][] units = new Unit[MAP_SIZE][MAP_SIZE];

	SpriteSheet tilemap;

	SpriteSheet walls;

	public Map(Image tilemap, Image walls) {
		tilemap.setFilter(Image.FILTER_NEAREST);
		this.tilemap = new SpriteSheet(tilemap, ITPT.tilewidth, ITPT.tileheight);
		walls.setFilter(Image.FILTER_NEAREST);
		this.walls = new SpriteSheet(walls, ITPT.wallwidth, ITPT.wallheight);
	}

	/**
	 * draws the map and the units on it on the Graphics g
	 * 
	 * @param gc
	 *            The Graphics to be draw on
	 * @param zoom
	 *            The zoom that will be applied
	 */
	public void draw(GameContainer gc) {
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				float tx = ITPT.tileX(i, j);
				float ty = ITPT.tileY(i, j);
				if (tx > ITPT.windowwidth
						|| ty < 0 - ITPT.tileheight * ITPT.zoom)
					continue;
				if (ty > ITPT.windowheight
						|| tx < 0 - ITPT.tilewidth * ITPT.zoom)
					break;
				tiles[i][j].draw(tx, ty, gc);
			}
		}
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				float tx = ITPT.tileX(i, j);
				float ty = ITPT.tileY(i, j);
				if (tx > ITPT.windowwidth
						|| ty < 0 - ITPT.tileheight * ITPT.zoom)
					continue;
				if (ty - ITPT.wallheight * ITPT.zoom > ITPT.windowheight
						|| tx < 0 - ITPT.tilewidth * ITPT.zoom)
					break;
				if (units[i][j] != null)
					units[i][j].draw(tx, ty
							+ (ITPT.tileheight - ITPT.unitheight - 1)
							* ITPT.zoom, gc);
				tiles[i][j].drawwall(tx, ty, gc);
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
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				tiles[i][j] = new Tile(tilemap.getSprite(0, 0));
				tiles[i][j].setLrwall(walls.getSprite(0, 0));
				tiles[i][j].setLlwall(walls.getSprite(0, 1));
			}
		}
		try {
			SpriteSheet weapon = new SpriteSheet("res/Weapon.png",
					ITPT.unitwidth, ITPT.unitheight);
			weapon.setFilter(SpriteSheet.FILTER_NEAREST);
			SpriteSheet armor = new SpriteSheet("res/Armor.png",
					ITPT.unitwidth, ITPT.unitheight);
			armor.setFilter(SpriteSheet.FILTER_NEAREST);
			Image unit = new Image("res/Units.png");
			unit.setFilter(Image.FILTER_NEAREST);
			int x = 10;
			int y = 19;
			units[x][y] = new Unit(unit);
			units[x][y].equipitem(new Equipment(weapon.getSprite(8, 0),
					Equipment.SLOT_WEAPON));
			units[x][y].equipitem(new Equipment(armor.getSprite(1, 0),
					Equipment.SLOT_TORSO));
			units[x][y].equipitem(new Equipment(armor.getSprite(2, 0),
					Equipment.SLOT_TROUSERS));
			units[x][y].equipitem(new Equipment(armor.getSprite(4, 1),
					Equipment.SLOT_HEAD));
			units[x][y].equipitem(new Equipment(armor.getSprite(5, 1),
					Equipment.SLOT_SHOE));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void wallrect(int x1, int y1, int x2, int y2, boolean set,
			boolean fill) {

		if (y2 < y1) {
			int b = y2;
			y2 = y1;
			y1 = b;
		}
		if (x2 < x1) {
			int b = x2;
			x2 = x1;
			x1 = b;
		}
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				if (i < 0 || j >= MAP_SIZE)
					break;
				if (i >= MAP_SIZE || j < 0)
					continue;
				if (fill) {
					tiles[i][j].setWall(Tile.WALL_LR, set);
					tiles[i][j].setWall(Tile.WALL_LL, set);
				} else {
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
				}
			}

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
			return tiles[context.getSourceX()][context.getSourceY()]
					.getWall(Tile.WALL_LR);
		if (ty < context.getSourceY())
			return tiles[tx][ty].getWall(Tile.WALL_LL);
		if (ty > context.getSourceY())
			return tiles[context.getSourceX()][context.getSourceY()]
					.getWall(Tile.WALL_LL);
		return false;
	}

	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		return 1.0f;
	}

}
