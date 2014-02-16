import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

class Map {
	/** The map size */
	public static final int MAP_SIZE = 20;
	/** The tiles that make up the map */
	Tile[][] tiles = new Tile[MAP_SIZE][MAP_SIZE];
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
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				tiles[i][j] = new Tile(testimg);
			}
		}
	}

}
