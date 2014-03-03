/** Isometric Tile Position Transformation */
public final class ITPT {
	static float xoffset;
	static float yoffset;
	static int tilewidth;
	static int tileheight;
	static int wallwidth;
	static int wallheight;
	static int unitwidth;
	static int unitheight;
	static int windowwidth;
	static int windowheight;
	static float zoom;

	public static void configure(float x, float y, int tw, int th, int ww,
			int wh, int uw, int uh, int winw, int winh, float z) {
		xoffset = x;
		yoffset = y;
		tilewidth = tw;
		tileheight = th;
		wallwidth = ww;
		wallheight = wh;
		unitwidth = uw;
		unitheight = uh;
		windowwidth = winw;
		windowheight = winh;
		zoom = z;
	}

	public static float tileX(float x, float y) {
		return windowwidth / 2 + xoffset * zoom + (x - y) * tilewidth / 2
				* zoom;
	}

	public static float tileY(float x, float y) {
		return windowheight / 2 + yoffset * zoom + (x + y) * tileheight / 2
				* zoom;
	}

	public static float tileatX(float mx, float my) {
		return Math
				.round(-(-((my - windowheight / 2 - yoffset * zoom - tileheight
						/ 2 * zoom) / (tileheight / 2 * zoom)) - (mx
						- windowwidth / 2 - xoffset * zoom - tilewidth / 2
						* zoom)
						/ (tilewidth / 2 * zoom)) / 2);

	}

	public static float tileatY(float mx, float my) {
		return Math
				.round((((my - windowheight / 2 - yoffset * zoom - tileheight
						/ 2 * zoom) / (tileheight / 2 * zoom)) - (mx
						- windowwidth / 2 - xoffset * zoom - tilewidth / 2
						* zoom)
						/ (tilewidth / 2 * zoom)) / 2);
	}
}
