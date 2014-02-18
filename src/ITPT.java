/** Isometric Tile Position Transformation */
public final class ITPT {
	static float xoffset;
	static float yoffset;
	static int tilewidth;
	static int tileheight;
	static int wallwidth;
	static int wallheight;
	static float zoom;

	
	public static void configure(float x, float y, int tw, int th, int ww, int wh, float z) {
		xoffset = x;
		yoffset = y;
		tilewidth = tw;
		tileheight = th;
		wallwidth = ww;
		wallheight = wh;
		zoom = z;
	}

	public static float tileX(float x, float y) {
		return xoffset + (x - y) * (tilewidth / 2 + 1) * zoom;
	}

	public static float tileY(float x, float y) {
		return yoffset + (x + y) * tileheight / 2 * zoom;
	}

	public static float tileatX(float mx, float my) {
		return Math
				.round((my - yoffset - tileheight / 2 * zoom)
						/ (tileheight / 2 * zoom)
						- (((my - yoffset - tileheight / 2 * zoom) / (tileheight / 2 * zoom)) - (mx
								- xoffset - tilewidth / 2 * zoom)
								/ ((tilewidth / 2 + 1) * zoom)) / 2);
	}

	public static float tileatY(float mx, float my) {
		return Math
				.round((((my - yoffset - tileheight / 2 * zoom) / (tileheight / 2 * zoom)) - (mx
						- xoffset - tilewidth / 2 * zoom)
						/ ((tilewidth / 2 + 1) * zoom)) / 2);
	}
}
