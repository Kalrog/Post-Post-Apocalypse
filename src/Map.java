import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

class Map implements TileBasedMap {
	/** The map size */
	public static final int MAP_SIZE = 100;
	/** The tiles that make up the map */
	Tile[][] tiles = new Tile[MAP_SIZE][MAP_SIZE];
	/** The units that are currently on the map */
	Unit[][] units = new Unit[MAP_SIZE][MAP_SIZE];

	Image tilemap;

	Image walls;

	public Map(Image tilemap, Image walls) {
		tilemap.setFilter(Image.FILTER_NEAREST);
		this.tilemap = tilemap;
		walls.setFilter(Image.FILTER_NEAREST);
		this.walls = walls;
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
				tiles[i][j].draw(ITPT.tileX(i, j), ITPT.tileY(i, j),
						ITPT.zoom, gc);
			}
		}
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				tiles[i][j].drawwall(ITPT.tileX(i, j), ITPT.tileY(i, j),
						ITPT.zoom, gc);
			}
		}

	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	public void setTile(int x, int y, Tile t) {
		tiles[x][y] = t;
	}

	public void test() {
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				tiles[i][j] = new Tile(tilemap.getSubImage(0, 0,
						ITPT.tilewidth, ITPT.tileheight));
				tiles[i][j].setLrwall(walls.getSubImage(0, 0,
						ITPT.wallwidth, ITPT.wallheight));
				tiles[i][j].setLlwall(walls.getSubImage(0,
						ITPT.wallheight, ITPT.wallwidth,
						ITPT.wallheight));
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
