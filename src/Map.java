import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

class Map implements TileBasedMap {
	/** The map size */
	public static final int MAP_SIZE = 20;
	/** The tiles that make up the map */
	Tile[][] tiles = new Tile[MAP_SIZE][MAP_SIZE];
	Tile open;
	/** The units that are currently on the map */
	List<Unit> units;
	/** The current x offset */
	float x = 0;
	/** The current y offset */
	float y = 0;

	/**
	 * draws the map and the units on it on the Graphics g
	 * 
	 * @param g
	 *            The Graphics to be draw on
	 * @param zoom
	 *            The zoom that will be applied
	 */
	public void draw(GameContainer gc, float zoom) {
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				tiles[i][j].draw(i, j, x, y, zoom, gc);
			}
		}

	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	public void setTile(int x, int y, Tile t) {
		tiles[x][y] = t;
	}

	public void test(Image testimg) {
		open = new Tile(testimg);
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				tiles[i][j] = new Tile(testimg);
			}
		}
	}

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
			return tiles[context.getSourceX()][context.getSourceY()]
					.getWall(Tile.WALL_UL)
					|| tiles[tx][ty].getWall(Tile.WALL_LR);
		if (tx > context.getSourceX())
			return tiles[context.getSourceX()][context.getSourceY()]
					.getWall(Tile.WALL_LR)|| tiles[tx][ty].getWall(Tile.WALL_UL);
		if (ty < context.getSourceY())
			return tiles[context.getSourceX()][context.getSourceY()]
					.getWall(Tile.WALL_UR)|| tiles[tx][ty].getWall(Tile.WALL_LL);
		if (ty > context.getSourceY())
			return tiles[context.getSourceX()][context.getSourceY()]
					.getWall(Tile.WALL_LL)|| tiles[tx][ty].getWall(Tile.WALL_UR);
		return false;
	}

	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		return 1.0f;
	}

}
