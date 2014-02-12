import org.newdawn.slick.Graphics;

public class Map {
	Tile[][] Tiles = new Tile[20][20];

	void draw(Graphics g, float zoom) {
	 for (int i = 0; i < Tiles[0].length; i++) {
		 for (int j = 0; j < Tiles.length; j++) {
		Tiles[i][j].draw(i, j, zoom, g);
		 }
		}
	}

}
