
import org.newdawn.slick.GameContainer;
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
	Item[] items = new Item[10];
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

	public Image getLlwall() {
		return llwall;
	}

	public void setLlwall(Image llwall) {
		this.llwall = llwall;
	}

	public Image getLrwall() {
		return lrwall;
	}

	public void setLrwall(Image lrwall) {
		this.lrwall = lrwall;
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
	 */
	void draw(float x, float y, GameContainer gc) {
		gc.getGraphics().drawImage(display.getScaledCopy(ITPT.zoom), x, y);
	}

	/**
	 * displays the tiles walls
	 * 
	 * @param x
	 *            The x-coordinate of the tile
	 * @param y
	 *            The y-coordinate of the tile
	 * @param gc
	 *            The GameContainter that will be drawn on
	 * @param zoom
	 *            The zoom that will be applied
	 */
	void drawwall(float x, float y, GameContainer gc) {
		if (wall[WALL_LR])
			gc.getGraphics().drawImage(lrwall.getScaledCopy(ITPT.zoom),
					x + (display.getWidth() / 2-1) * ITPT.zoom,
					y + (display.getHeight() - lrwall.getHeight()) * ITPT.zoom);
		if (wall[WALL_LL])
			gc.getGraphics().drawImage(llwall.getScaledCopy(ITPT.zoom),
					x - ITPT.zoom,
					y + (display.getHeight() - llwall.getHeight()) * ITPT.zoom);
	}
}
