package com.game.data;

import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.util.pathfinding.Path;

public class Unit {
	/** The unit is controlled by the zombie AI */
	public static final int ZOMBIE = 0;

	/** The unit is controlled by the player */
	public static final int PLAYER = 1;

	/**
	 * The team the unit is on
	 * 
	 * @param team
	 *            One of: {@link Unit#PLAYER}, {@link Unit#ZOMBIE}
	 * */
	int team;

	/** The Text that is displayed as unit information */
	String tooltip;

	/** The unit's equipment */
	Equipment[] equip = new Equipment[9];

	/** The unit's inventory */
	List<Item> inventory;

	/** The image used to display the unit */
	Image display;

	/** The path the Unit is following */
	Path path;

	public Unit(Image display) {
		this.display = display;
	}

	public void draw(float x, float y) {
		display.draw(x, y + (Datacenter.tileheight - Datacenter.unitheight - 1) * Datacenter.zoom, Datacenter.zoom);
		for (int i = 0; i < equip.length; i++)
			if (equip[i] != null)
				equip[i].draw(x, y);
	}

	public void equipitem(Equipment e) {
		if (equip[e.getSlot()] != null)
			inventory.add(equip[e.getSlot()]);
		equip[e.getSlot()] = e;
	}
}
