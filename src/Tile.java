import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Tile {
	int durability;
	Image display;
	Tile upgrade;
	Tile downgrade;

	Tile(int durability, Image display) {
		this.display = display;
	}
	
	//transforms virtual tile coordinates to screen coordinates and displays them
	void draw(int x, int y, float zoom, Graphics g) {
		g.drawImage(display.getScaledCopy(zoom), (x * (display.getWidth() + 2)
				- ((y + 1) % 2) * display.getWidth() / 2 + y % 2)
				* zoom, (y - 1) * display.getHeight() / 2 * zoom);
	}
}
