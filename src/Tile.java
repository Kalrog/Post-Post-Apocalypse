import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Tile {
	/** A wall on the lower left side */
	public static final int WALL_LL = 0;
	/** A wall on the lower right side */
	public static final int WALL_LR = 1;
	/** The damage the tile can take before it gets destroyed */
	int durability;
	/** The tile's current health */
	int health;
	/** The image used to display the tile */
	Image display;
	/** The image used to display the tile's lower left wall */
	Image llwall;
	/** The image used to display the tile's lower right wall */
	Image lrwall;
	/** The tile that it will become when being upgraded */
	Tile upgrade;
	/** The tile it will become when being destroyed */
	Tile downgrade;
	/** The items on the tile */
	List<Item> items;
	/** The walls of the tile */
	boolean[] wall = new boolean[2];

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
		health = durability;
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
		health = durability;
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
		health = durability;
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

	public Image getDisplay() {
		return display;
	}

	public void setDisplay(Image display) {
		this.display = display;
	}

	public boolean getWall(int x) {
		return wall[x];
	}

	public void setWall(int x, boolean b) {
		wall[x] = b;
	}

	/**
	 * transforms virtual tile coordinates to screen coordinates and displays
	 * them
	 * 
	 * @param x
	 *            The x-coordinate of the tile
	 * @param y
	 *            The y-coordinate of the tile
	 * @param gc
	 *            The GameContainter that will be drawn on
	 * @param zoom
	 *            The zoom that will be applied
	 * @param xoffset
	 *            The x offset that will be used
	 * @param yoffset
	 *            The y offset that will be used
	 */
	void draw(float x, float y, float xoffset, float yoffset, float zoom,
			GameContainer gc) {
		float i = x;
		float j = y;
		x = xoffset + (i - j) * (display.getWidth() / 2 + 1) * zoom;
		y = yoffset + (j + i) * display.getHeight() / 2 * zoom;
		gc.getGraphics().drawImage(display.getScaledCopy(zoom), x, y);
	}

	void drawwall(float x, float y, float xoffset, float yoffset, float zoom,
			GameContainer gc) {
		float i = x;
		float j = y;
		x = xoffset + (i - j) * (display.getWidth() / 2 + 1) * zoom;
		y = yoffset + (j + i) * display.getHeight() / 2 * zoom;
		if (wall[WALL_LR])
			gc.getGraphics().drawImage(lrwall.getScaledCopy(zoom),
					x + (display.getWidth() / 2)*zoom, y + display.getHeight()*zoom-lrwall.getHeight()*zoom);
		if (wall[WALL_LL])
			gc.getGraphics().drawImage(llwall.getScaledCopy(zoom), x-1*zoom,
					y + display.getHeight()*zoom-llwall.getHeight()*zoom);
	}
}
