package com.game.data;

import org.newdawn.slick.Image;

public class Equipment extends Item {
	public static final int SLOT_SHOE = 0;

	public static final int SLOT_GLOVE = 1;

	public static final int SLOT_BRACER = 2;

	public static final int SLOT_TROUSERS = 3;

	public static final int SLOT_TORSO = 4;

	public static final int SLOT_SHOULDERS = 5;

	public static final int SLOT_HEAD = 6;

	public static final int SLOT_WEAPON = 7;

	public static final int SLOT_OFF_HAND = 8;

	Image display;

	int slot;

	public Equipment(Image display, int slot) {
		this.display = display;
		this.slot = slot;
	}

	public int getSlot() {
		return slot;
	}

	public void draw(float x, float y) {
		display.draw(x, y + (Datacenter.TILEHEIGHT - Datacenter.UNITHEIGHT - 1) * Datacenter.zoom, Datacenter.zoom);
	}
}
