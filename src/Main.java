import java.awt.BasicStroke;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

public class Main extends BasicGame {
	/** The game is in the main menu */
	public static final int STATE_MAINMENU = 0;
	/** The game is displaying the options menu */
	public static final int STATE_OPTIONSMENU = 1;
	/** The game is in-game */
	public static final int STATE_GAME = 2;
	/** The game is displaying the in-game pause menu */
	public static final int STATE_PAUSE = 3;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;

	boolean showFPS = true;
	boolean init = true;
	boolean build = false;
	int gamestate = STATE_MAINMENU;
	float tx, ty, ux, uy;
	float zoom = 2;
	float uiSize = 5;
	UnicodeFont font;
	Image testimg;
	Image testimgw;
	Map testmap;
	AStarPathFinder testfinder;
	Path path;

	public Main(String gamename) {
		super(gamename);
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (gamestate) {
		case STATE_MAINMENU:
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			if (c == 'b')
				build = !build;
			if (c == 'g')
				path = testfinder.findPath(null, (int) tx, (int) ty, (int) ux,
						(int) uy);
			break;
		case STATE_PAUSE:
			break;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		switch (gamestate) {
		case STATE_MAINMENU:
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			break;
		case STATE_PAUSE:
			break;
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		switch (gamestate) {
		case STATE_MAINMENU:
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			break;
		case STATE_PAUSE:
			break;
		}
	}

	@Override
	public void mouseWheelMoved(int change) {
		switch (gamestate) {
		case STATE_MAINMENU:
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			if (change < 0 && zoom > 1)
				zoom--;
			if (change > 0)
				zoom++;
			break;
		case STATE_PAUSE:
			break;
		}

	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		switch (gamestate) {
		case STATE_MAINMENU:
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			if (!build) {
				testmap.setX(testmap.getX() + newx - oldx);
				testmap.setY(testmap.getY() + newy - oldy);
			}
			break;
		case STATE_PAUSE:
			break;
		}
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		switch (gamestate) {
		case STATE_MAINMENU:
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			break;
		case STATE_PAUSE:
			break;
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		switch (gamestate) {
		case STATE_MAINMENU:
			gamestate = STATE_GAME;
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			float a = (x - testmap.getX() - testmap.getTile(0, 0).getDisplay()
					.getWidth()
					/ 2 * zoom)
					/ ((testmap.getTile(0, 0).getDisplay().getWidth() / 2 + 1) * zoom);
			float b = (y - testmap.getY() - testmap.getTile(0, 0).getDisplay()
					.getHeight()
					/ 2 * zoom)
					/ ((testmap.getTile(0, 0).getDisplay().getHeight() / 2) * zoom);
			if (button == 1) {
				uy = (b - a) / 2;
				ux = b - uy;
				uy = Math.round(uy);
				ux = Math.round(ux);
				if (build) {
					float ax, ay;
					ax = testmap.getTile(0, 0).getDisplay().getWidth()
							/ 2
							* zoom
							+ testmap.getX()
							+ (ux - uy)
							* (testmap.getTile(0, 0).getDisplay().getWidth() / 2 + 1)
							* zoom;
					ay = testmap.getTile(0, 0).getDisplay().getHeight() / 2
							* zoom + testmap.getY() + (ux + uy)
							* testmap.getTile(0, 0).getDisplay().getHeight()
							/ 2 * zoom;
					if (ax < x) {
						if (ay < y) {
							testmap.getTile((int) ux, (int) uy).setWall(
									Tile.WALL_LR, false);
						} else {
							testmap.getTile((int) ux, (int) uy - 1).setWall(
									Tile.WALL_LL, false);
						}
					} else {
						if (ay < y) {
							testmap.getTile((int) ux, (int) uy).setWall(
									Tile.WALL_LL, false);
						} else {
							testmap.getTile((int) ux - 1, (int) uy).setWall(
									Tile.WALL_LR, false);
						}
					}
				}
			}
			if (button == 0) {
				ty = (b - a) / 2;
				tx = b - ty;
				ty = Math.round(ty);
				tx = Math.round(tx);
				if (build) {
					float ax, ay;
					ax = testmap.getTile(0, 0).getDisplay().getWidth()
							/ 2
							* zoom
							+ testmap.getX()
							+ (tx - ty)
							* (testmap.getTile(0, 0).getDisplay().getWidth() / 2 + 1)
							* zoom;
					ay = testmap.getTile(0, 0).getDisplay().getHeight() / 2
							* zoom + testmap.getY() + (tx + ty)
							* testmap.getTile(0, 0).getDisplay().getHeight()
							/ 2 * zoom;
					if (ax < x) {
						if (ay < y) {
							testmap.getTile((int) tx, (int) ty).setWall(
									Tile.WALL_LR, true);
						} else {
							testmap.getTile((int) tx, (int) ty - 1).setWall(
									Tile.WALL_LL, true);
						}
					} else {
						if (ay < y) {
							testmap.getTile((int) tx, (int) ty).setWall(
									Tile.WALL_LL, true);
						} else {
							testmap.getTile((int) tx - 1, (int) ty).setWall(
									Tile.WALL_LR, true);
						}
					}
				}
			}

			break;
		case STATE_PAUSE:
			break;
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		switch (gamestate) {
		case STATE_MAINMENU:
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			break;
		case STATE_PAUSE:
			break;
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		testimg = new Image("res/Tiles.png");
		testimg.setFilter(Image.FILTER_NEAREST);
		testimgw = new Image("res/Walls.png");
		testimgw.setFilter(Image.FILTER_NEAREST);
		testmap = new Map();
		testmap.test(testimg.getSubImage(0, 0, 14, 8),
				testimgw.getSubImage(0, 29, 8, 29),
				testimgw.getSubImage(0, 0, 8, 29));
		testfinder = new AStarPathFinder(testmap, 100, false);
		gc.setShowFPS(showFPS);
		font = new UnicodeFont("res/C&C Red Alert [INET].ttf", 25, false, false);
		font.setPaddingLeft(5);
		font.setPaddingRight(5);
		font.setPaddingTop(5);
		font.setPaddingBottom(5);
		font.setPaddingAdvanceX(-9);
		font.setPaddingAdvanceY(-10);
		font.getEffects().add(new OutlineEffect(5, java.awt.Color.BLACK));
		((OutlineEffect) font.getEffects().get(0))
				.setJoin(BasicStroke.JOIN_MITER);
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		font.addNeheGlyphs();
		font.loadGlyphs();
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		switch (gamestate) {
		case STATE_MAINMENU:
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			break;
		case STATE_PAUSE:
			break;
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		switch (gamestate) {
		case STATE_MAINMENU:
			g.setAntiAlias(false);
			g.setBackground(Color.darkGray);
			g.setFont(font);
			g.drawString("Keys:\n" + "toggle build mode:b\n" + "point 1:LMB\n"
					+ "point 2:RMB\n"
					+ "generate path from point 1 to point 2:g\n"
					+ "click anywhere to start", 300, 200);
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			g.setAntiAlias(false);
			g.setBackground(Color.black);
			g.setFont(font);
			testmap.draw(gc, zoom);
			if (path != null) {
				for (int n = 1; n < path.getLength(); n++) {
					float ax, ay, bx, by;
					ax = testmap.getTile(0, 0).getDisplay().getWidth()
							/ 2
							* zoom
							+ testmap.getX()
							+ (path.getStep(n).getX() - path.getStep(n).getY())
							* (testmap.getTile(0, 0).getDisplay().getWidth() / 2 + 1)
							* zoom;
					ay = testmap.getTile(0, 0).getDisplay().getHeight() / 2
							* zoom + testmap.getY()
							+ (path.getStep(n).getX() + path.getStep(n).getY())
							* testmap.getTile(0, 0).getDisplay().getHeight()
							/ 2 * zoom;
					bx = testmap.getTile(0, 0).getDisplay().getWidth()
							/ 2
							* zoom
							+ testmap.getX()
							+ (path.getStep(n - 1).getX() - path.getStep(n - 1)
									.getY())
							* (testmap.getTile(0, 0).getDisplay().getWidth() / 2 + 1)
							* zoom;
					by = testmap.getTile(0, 0).getDisplay().getHeight()
							/ 2
							* zoom
							+ testmap.getY()
							+ (path.getStep(n - 1).getX() + path.getStep(n - 1)
									.getY())
							* testmap.getTile(0, 0).getDisplay().getHeight()
							/ 2 * zoom;
					g.setColor(Color.red);
					g.drawLine(ax, ay, bx, by);
					if (n > 1 && n < path.getLength() - 1)
						if (path.getStep(n).getX() - path.getStep(n - 1).getX() != path
								.getStep(n + 1).getX() - path.getStep(n).getX())
							g.drawRect(ax - 4, ay - 4, 8, 8);

				}
			}
			g.setColor(Color.white);
			g.drawString(tx + "," + ty, 600, 400);
			g.drawString(ux + "," + uy, 600, 430);
			g.drawString("x" + zoom, 600, 460);
			break;
		case STATE_PAUSE:
			break;
		}
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Main("ZOMBIES!"));
			appgc.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
