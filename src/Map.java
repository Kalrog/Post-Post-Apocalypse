import java.util.List;

import org.newdawn.slick.Graphics;

public class Map {
	/** The map size */
	public static final int MAP_SIZE = 20;
	/** The tiles that make up the map */
	Tile[][] Tiles = new Tile[MAP_SIZE][MAP_SIZE];
	/** The units that are currently on the map */
	List<Unit> units;

	/**
	 * draws the map and the units on it on the Graphics g
	 * 
	 * @param g
	 *            The Graphics to be draw on
	 * @param zoom
	 *            The zoom that will be applied
	 */
	void draw(Graphics g, float zoom) {
		for (int i = 0; i < Tiles[0].length; i++) {
			for (int j = 0; j < Tiles.length; j++) {
				Tiles[i][j].draw(i, j, zoom, g);
			}
		}

	}

}
