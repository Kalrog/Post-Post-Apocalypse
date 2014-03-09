package com.game.data;

import java.util.Random;

public class Mapgen {
	static Random r;

	private static class Room {
		public static final int UR = 0;

		public static final int UL = 1;

		public static final int LR = 2;

		public static final int LL = 3;

		int x;

		int y;

		int width;

		int height;

		int doorwall;

		int doorpos;

		boolean pub; // lic

		Room(int x, int y, int w, int h, int dy, int dx, boolean p) {
			this.x = x;
			this.y = y;
			width = w;
			height = h;
			doorwall = dx;
			doorpos = dy;
			pub = p;
		}

		void create(Map m, Tile t) {
			m.rect(x, y, x + width, y + height, true, false, t);
			m.rect(x, y, x + width, y + height, true, true, t);
			switch (doorwall) {
			case UR:
				m.getTile(x + doorpos, y - 1).setWall(Tile.WALL_LL, false);
				break;
			case UL:
				m.getTile(x - 1, y + doorpos).setWall(Tile.WALL_LR, false);
				break;
			case LR:
				m.getTile(x + width, y + doorpos).setWall(Tile.WALL_LR, false);
				break;
			case LL:
				m.getTile(x + doorpos, y + height).setWall(Tile.WALL_LL, false);
				break;
			}
		}
	}

	static public Map generate(long seed) {
		Map map = new Map();
		Room room = new Room(10, 12, 20, 10, 5, Room.LL, true);
		Room room2 = new Room(10, 11 - 10, 20, 10, 5, Room.LL, false);
		Room room3 = new Room(11 + 20, 12, 20, 10, 5, Room.UL, false);
		r = new Random(seed);
		map.rect(0, 0, Map.MAP_SIZE, Map.MAP_SIZE, true, false, Datacenter.Tilelist.get(0).getCopy());
		room.create(map, Datacenter.Tilelist.get(4).getCopy());
		room2.create(map, Datacenter.Tilelist.get(5).getCopy());
		room3.create(map, Datacenter.Tilelist.get(5).getCopy());
		return map;
	}
}
