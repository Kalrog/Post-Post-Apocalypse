package com.game.data;

import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class Overworld implements TileBasedMap {

	/** Size of the Overworldmap */
	public static final int OVERWORLD_SIZE = 100;

	/** The tiles that make up the map */
	Tile[][] tiles = new Tile[OVERWORLD_SIZE][OVERWORLD_SIZE];

	/** The units that are currently on the map */
	Squad[][][] squad = new Squad[OVERWORLD_SIZE][OVERWORLD_SIZE][10];

	public void draw() {

		for (int i = 0; i < OVERWORLD_SIZE; i++) {
			for (int j = 0; j < OVERWORLD_SIZE; j++) {
			}
		}

	}

	public int getWidthInTiles() {
		return 0;
	}

	public int getHeightInTiles() {
		return 0;
	}

	public void pathFinderVisited(int x, int y) {

	}

	public boolean blocked(PathFindingContext context, int tx, int ty) {
		return false;
	}

	public float getCost(PathFindingContext context, int tx, int ty) {
		return 0;
	}

}
