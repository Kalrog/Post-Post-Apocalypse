package com.game.data;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class Tile {
	/** A wall on the lower left side */
	public static final int WALL_LL = 0;

	/** A wall on the lower right side */
	public static final int WALL_LR = 1;

	/** The damage the tile can take before it gets destroyed */
	private int durability;

	/** The tile's current health */
	private int health;

	/** The color to filter with while drawing */
	private Color color = new Color(255, 255, 255, 255);

	/** The image used to display the tile */
	private Image display;

	/** The image used to display the tile's lower left wall */
	private Image llwall;

	/** The image used to display the tile's lower right wall */
	private Image lrwall;

	/** The tile that it will become when being upgraded */
	private Tile upgrade;

	/** The tile it will become when being destroyed */
	private Tile downgrade;

	/** The items on the tile */
	private Item[] items = new Item[10];

	/** The walls of the tile */
	private boolean[] wall = new boolean[2];

	/**
	 * Creates a new Tile
	 * 
	 * @param display
	 *            The image used to display the tile
	 */
	Tile(Image display) {
		this.display = display;
	}

	Tile() {
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Tile getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(Tile upgrade) {
		this.upgrade = upgrade;
	}

	public Tile getDowngrade() {
		return downgrade;
	}

	public void setDowngrade(Tile downgrade) {
		this.downgrade = downgrade;
	}

	public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
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

	public Tile getCopy() {
		Tile t = new Tile(display);
		t.setLlwall(llwall);
		t.setLrwall(lrwall);
		return t;
	}

	/**
	 * transforms virtual tile coordinates to screen coordinates and displays
	 * them
	 * 
	 * @param x
	 *            The x-coordinate of the tile
	 * @param y
	 *            The y-coordinate of the tile
	 * @param zoom
	 *            // * The zoom that will be applied
	 */
	public void draw(float x, float y, float zoom) {
		display.draw(x, y, zoom, color);
	}

	/**
	 * displays the tiles walls
	 * 
	 * @param x
	 *            The x-coordinate of the tile
	 * @param y
	 *            The y-coordinate of the tile
	 * @param zoom
	 *            The zoom that will be applied
	 */
	public void drawwall(float x, float y, float zoom) {
		if (wall[WALL_LL])
			llwall.draw(x - zoom, y + (display.getHeight() - llwall.getHeight()) * zoom, zoom);
		if (wall[WALL_LR])
			lrwall.draw(x + (display.getWidth() / 2) * zoom, y + (display.getHeight() - lrwall.getHeight()) * zoom, zoom);
	}
}
