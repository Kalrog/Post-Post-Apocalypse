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
	/** The window's width */
	public static final int WINDOW_WIDTH = 800;
	/** The window's height */
	public static final int WINDOW_HEIGHT = 600;

	boolean showFPS = true;
	boolean init = true;
	boolean build = false;
	boolean rect = false;
	boolean drag = false;
	boolean dragr = false;
	int gamestate = STATE_MAINMENU;
	float tx, ty, ux, uy;
	int startx, starty;
	float uiSize = 5;
	UnicodeFont font;
	Map map;
	AStarPathFinder pathfinder;
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
			if (c == 'r')
				rect = !rect;
			if (c == 'g')
				path = pathfinder.findPath(null, (int) tx, (int) ty, (int) ux,
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
			if (change < 0 && ITPT.zoom > 1) {
				ITPT.zoom--;
			}
			if (change > 0) {
				ITPT.zoom++;
			}
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
				ITPT.xoffset = ITPT.xoffset + newx - oldx;
				ITPT.yoffset = ITPT.yoffset + newy - oldy;
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
			if (button == 0)
				drag = true;
			if (button == 1)
				dragr = true;
			startx = x;
			starty = y;
			float sx = ITPT.tileatX(x, y);
			float sy = ITPT.tileatY(x, y);
			float ax = ITPT.tilewidth / 2 * ITPT.zoom + ITPT.tileX(sx, sy);
			float ay = ITPT.tileheight / 2 * ITPT.zoom + ITPT.tileY(sx, sy);
			if (build && !rect) {
				if (ax < x) {
					if (ay < y) {
						map.getTile((int) sx, (int) sy).setWall(Tile.WALL_LR,
								button == 0);
					} else {
						map.getTile((int) sx, (int) sy - 1).setWall(
								Tile.WALL_LL, button == 0);
					}
				} else {
					if (ay < y) {
						map.getTile((int) sx, (int) sy).setWall(Tile.WALL_LL,
								button == 0);
					} else {
						map.getTile((int) sx - 1, (int) sy).setWall(
								Tile.WALL_LR, button == 0);
					}
				}
			} else {
				if (button == 0) {
					ty = sy;
					tx = sx;
				}
				if (button == 1) {
					uy = sy;
					ux = sx;
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
			if (build && rect && (drag || dragr)) {
				float sx, sy, ex, ey;
				sx = ITPT.tileatX(startx, starty);
				sy = ITPT.tileatY(startx, starty);
				ex = ITPT.tileatX(x, y);
				ey = ITPT.tileatY(x, y);
				if (sx + sy > ex + ey) {
					for (int i = (int) ex; i <= sx; i++) {
						for (int j = (int) ey; j <= sy; j++) {
							if (dragr) {
								map.getTile(i, j).setWall(Tile.WALL_LR, false);
								map.getTile(i, j).setWall(Tile.WALL_LL, false);
							}
							if (drag) {
								if (i == (int) ex) {
									map.getTile(i - 1, j).setWall(Tile.WALL_LR,
											true);
								}
								if (j == (int) ey) {
									map.getTile(i, j - 1).setWall(Tile.WALL_LL,
											true);
								}
								if (i == (int) sx) {
									map.getTile(i, j).setWall(Tile.WALL_LR,
											true);
								}
								if (j == (int) sy) {
									map.getTile(i, j).setWall(Tile.WALL_LL,
											true);
								}
							}
						}
					}
				} else {
					for (int i = (int) sx; i <= ex; i++) {
						for (int j = (int) sy; j <= ey; j++) {
							if (dragr) {
								map.getTile(i, j).setWall(Tile.WALL_LR, false);
								map.getTile(i, j).setWall(Tile.WALL_LL, false);
							}
							if (drag) {
								if (i == (int) sx) {
									map.getTile(i - 1, j).setWall(Tile.WALL_LR,
											true);
								}
								if (j == (int) sy) {
									map.getTile(i, j - 1).setWall(Tile.WALL_LL,
											true);
								}
								if (i == (int) ex) {
									map.getTile(i, j).setWall(Tile.WALL_LR,
											true);
								}
								if (j == (int) ey) {
									map.getTile(i, j).setWall(Tile.WALL_LL,
											true);
								}
							}
						}
					}
				}
			}
			drag = false;
			dragr = false;
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

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc) throws SlickException {
		ITPT.configure(gc.getWidth() / 2, -gc.getHeight() / 2, 14, 8, 8, 25, 2);
		map = new Map(new Image("res/Tiles.png"), new Image("res/Walls.png"));
		map.test();
		pathfinder = new AStarPathFinder(map, 300, false);
		gc.setShowFPS(showFPS);
		gc.setVSync(true);
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
			g.drawString("Keys:\n" + "toggle build mode:b\n"
					+ "toggle rectangle build mode:r\n" + "point 1:LMB\n"
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
			map.draw(gc);
			if (path != null) {
				for (int n = 1; n < path.getLength(); n++) {
					float ax, ay, bx, by;
					ax = ITPT.tilewidth
							/ 2
							* ITPT.zoom
							+ ITPT.tileX(path.getStep(n).getX(), path
									.getStep(n).getY());
					ay = ITPT.tileheight
							/ 2
							* ITPT.zoom
							+ ITPT.tileY(path.getStep(n).getX(), path
									.getStep(n).getY());
					bx = ITPT.tilewidth
							/ 2
							* ITPT.zoom
							+ ITPT.tileX(path.getStep(n - 1).getX(), path
									.getStep(n - 1).getY());
					;
					by = ITPT.tileheight
							/ 2
							* ITPT.zoom
							+ ITPT.tileY(path.getStep(n - 1).getX(), path
									.getStep(n - 1).getY());
					;
					g.setColor(Color.red);
					g.drawLine(ax, ay, bx, by);
					if (n == 1)
						g.drawRect(bx - 4, by - 4, 8, 8);
					if (n == path.getLength() - 1)
						g.drawRect(ax - 4, ay - 4, 8, 8);
					if (n > 1 && n < path.getLength() - 1) {
						if (path.getStep(n).getX() - path.getStep(n - 1).getX() != path
								.getStep(n + 1).getX() - path.getStep(n).getX())
							g.drawRect(ax - 4, ay - 4, 8, 8);
						if (path.getStep(n).getY() - path.getStep(n - 1).getY() != path
								.getStep(n + 1).getY() - path.getStep(n).getY())
							g.drawRect(ax - 4, ay - 4, 8, 8);
					}

				}
			}
			g.setColor(Color.white);
			g.drawString(tx + "," + ty, 600, 400);
			g.drawString(ux + "," + uy, 600, 430);
			g.drawString("x" + ITPT.zoom, 600, 460);
			g.drawString("Build:" + build, 600, 490);
			g.drawString("Rectangle:" + rect, 600, 520);
			break;
		case STATE_PAUSE:
			break;
		}
	}
}
