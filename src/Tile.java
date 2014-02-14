import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Tile {
	/** The damage the tile can take before it gets destroyed */
	int durability;
	/** The image used to display the tile */
	Image display;
	/** The tile that it will become when being upgraded */
	Tile upgrade;
	/** The tile it will become when being destroyed */
	Tile downgrade;
	/** The items on the tile */
	List<Item> items;

	/**
	 * Creates a new Tile
	 * 
	 * @param display
	 *            The image used to display the tile
	 * @param durability
	 *            The damage the tile can take before it gets destroyed
	 * @param upgrade
	 *            The tile that it will become when being upgraded
	 * @param downgrade
	 *            The tile it will become when being destroyed
	 */
	Tile(Image display, int durability, Tile upgrade, Tile downgrade) {
		this.durability = durability;
		this.display = display;
		this.upgrade = upgrade;
		this.downgrade = downgrade;
	}

	/**
	 * Creates a new Tile
	 * 
	 * @param display
	 *            The image used to display the tile
	 * @param durability
	 *            The damage the tile can take before it gets destroyed
	 * @param upgrade
	 *            The tile that it will become when being upgraded
	 */
	Tile(int durability, Image display, Tile upgrade) {
		this.durability = durability;
		this.display = display;
		this.upgrade = upgrade;
	}

	/**
	 * Creates a new Tile
	 * 
	 * @param display
	 *            The image used to display the tile
	 * @param durability
	 *            The damage the tile can take before it gets destroyed
	 * @param downgrade
	 *            The tile it will become when being destroyed
	 */
	Tile(Image display, int durability, Tile downgrade) {
		this.durability = durability;
		this.display = display;
		this.downgrade = downgrade;
	}

	/**
	 * Creates a new Tile
	 * 
	 * @param display
	 *            The image used to display the tile
	 */
	Tile(Image display) {
		this.display = display;
	}

	/**
	 * transforms virtual tile coordinates to screen coordinates and displays
	 * them
	 * 
	 * @param x
	 *            The x-coordinate of the tile
	 * @param y
	 *            The y-coordinate of the tile
	 * @param g
	 *            The Graphics to be draw on
	 * @param zoom
	 *            The zoom that will be applied
	 */
	void draw(float x, float y, float zoom, Graphics g) {
		x = (x * (display.getWidth() + 2) - ((y + 1) % 2) * display.getWidth()
				/ 2 + y % 2)
				* zoom;
		y = (y - 1) * display.getHeight() / 2 * zoom;
		g.drawImage(display.getScaledCopy(zoom), x, y);
		if (items != null)
			g.drawImage(items.get(items.size()).icon.getScaledCopy(zoom), x, y);

	}
}
