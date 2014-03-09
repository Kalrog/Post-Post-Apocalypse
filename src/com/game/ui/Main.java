package com.game.ui;

import java.awt.BasicStroke;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

import com.game.data.*;

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

	static AppGameContainer appgc;

	boolean showFPS = true;

	boolean build = false;

	boolean wall = false;

	boolean rect = false;

	int gamestate = STATE_MAINMENU;

	TextField t;

	int[] lt = new int[2];

	int[] rt = new int[2];

	int[] start = new int[2];

	UnicodeFont font;

	Map map;

	Tile selected;

	/** Position of the selected Tile (Selected Position) */
	int sp = 0;

	AStarPathFinder pathfinder;

	Path path;

	public Main(String gamename) {
		super(gamename);
	}

	void selectTile() {
		selected = Datacenter.Tilelist.get(sp).getCopy();
		selected.setWall(Tile.WALL_LL, true);
		selected.setWall(Tile.WALL_LR, true);
	}

	public void keyPressed(int key, char c) {
		if (Datacenter.LOG)
			System.out.println("Pressed " + c);
		switch (gamestate) {
		case STATE_MAINMENU:
			if (key == Input.KEY_ESCAPE)
				appgc.exit();
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			if (key == Input.KEY_ESCAPE)
				gamestate = STATE_MAINMENU;
			if (c == 'n')
				try {
					map = Mapgen.generate(Long.valueOf(t.getText()));
				} catch (NumberFormatException nfe) {
					map = Mapgen.generate(123);
				}
			if (c == 'u')
				Datacenter.createTiles();
			if (c == 'o' && sp > 0) {
				sp--;
				selectTile();
			}
			if (c == 'p' && sp + 1 < Datacenter.Tilelist.size()) {
				sp++;
				selectTile();
			}
			if (c == 'w')
				wall = !wall;
			if (c == 'b')
				build = !build;
			if (c == 'r')
				rect = !rect;
			if (c == 'g' && lt[0] >= 0 && lt[0] < Map.MAP_SIZE && lt[1] >= 0 && lt[1] < Map.MAP_SIZE && rt[0] >= 0 && rt[0] < Map.MAP_SIZE && rt[1] >= 0 && rt[1] < Map.MAP_SIZE)
				path = pathfinder.findPath(null, lt[0], lt[1], rt[0], rt[1]);
			break;
		case STATE_PAUSE:
			break;
		}
	}

	public void keyReleased(int key, char c) {
		if (Datacenter.LOG)
			System.out.println("Released " + c);
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

	public void mouseWheelMoved(int change) {
		if (Datacenter.LOG)
			System.out.println("Mousewheel " + change);
		switch (gamestate) {
		case STATE_MAINMENU:
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			if (change < 0) {
				Datacenter.zoom /= 2;
			}
			if (change > 0) {
				Datacenter.zoom *= 2;
			}
			break;
		case STATE_PAUSE:
			break;
		}
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		switch (gamestate) {
		case STATE_MAINMENU:
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			if (!build) {
				Datacenter.xoffset += (newx - oldx) / Datacenter.zoom;
				Datacenter.yoffset += (newy - oldy) / Datacenter.zoom;
			}
			break;
		case STATE_PAUSE:
			break;
		}
	}

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

	public void mousePressed(int button, int x, int y) {
		if (Datacenter.LOG)
			System.out.println("Mouse Pressed (" + x + "|" + y + ")" + button);
		switch (gamestate) {
		case STATE_MAINMENU:
			gamestate = STATE_GAME;
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			start[0] = x;
			start[1] = y;

			/** Position of the tile that was pressed */
			int[] s = Datacenter.tileat(x, y);

			if (!(s[0] < 0 || s[1] > Map.MAP_SIZE || s[0] > Map.MAP_SIZE || s[1] < 0))
				if (build && !rect && !wall) {
					map.setTile(s[0], s[1], selected.getCopy());
				} else if (build && !rect && wall) {
					/** Center of the pressed tile */
					int[] a = Datacenter.tile(s[0], s[1]);
					a[0] += Datacenter.tilewidth * Datacenter.zoom / 2;
					a[1] += Datacenter.tileheight * Datacenter.zoom / 2;
					/* Create a wall depending on where the tile was pressed
					 * 
					 *		XNNNNNNNNNNNNNN
					 *		Xs[0]|s[1]    N
					 *		X             N
					 *		X     ##      N
					 *		X     ##      N
					 *		X             N
					 *		X             N
					 *		XXXXXXXXXXXXXXX
					 *		# = a[0]|a[1]
					 *		X = tile walls
					 *		N = neighbour walls
					 * 
					 *  */
					map.getTile(s[0] - ((a[0] > x && a[1] > y) ? 1 : 0), s[1] - ((a[0] < x && a[1] > y) ? 1 : 0)).setWall((a[0] < x == a[1] < y) ? Tile.WALL_LR : Tile.WALL_LL, button == 0);
				} else {
					if (button == 0) {
						map.getTile(lt[0], lt[1]).setColor(new Color(255, 255, 255, 255));
						map.getTile(s[0], s[1]).setColor(new Color(50, 50, 50, 255));
						lt = s;
					}
					if (button == 1) {
						map.getTile(rt[0], rt[1]).setColor(new Color(255, 255, 255, 255));
						map.getTile(s[0], s[1]).setColor(new Color(100, 100, 100, 255));
						rt = s;

					}
				}
			break;
		case STATE_PAUSE:
			break;
		}
	}

	public void mouseReleased(int button, int x, int y) {
		if (Datacenter.LOG)
			System.out.println("Mouse Released (" + x + "|" + y + ")" + button);
		switch (gamestate) {
		case STATE_MAINMENU:
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			if (build && rect) {
				int[] s = Datacenter.tileat(start[0], start[1]);
				int[] e = Datacenter.tileat(x, y);
				map.rect(e[0], e[1], s[0], s[1], button == 0, wall, selected);
			}
			break;
		case STATE_PAUSE:
			break;
		}
	}

	public static void main(String[] args) {
		try {
			appgc = new AppGameContainer(new Main("ZOMBIES!"));
			appgc.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@SuppressWarnings("unchecked")
	public void init(GameContainer gc) throws SlickException {
		gc.setShowFPS(showFPS);
		gc.setVSync(true);
		Datacenter.setup(0, 0, WINDOW_WIDTH - 230, WINDOW_HEIGHT, 2);
		Datacenter.createTiles();
		selectTile();
		map = Mapgen.generate(123);
		map.test();
		pathfinder = new AStarPathFinder(map, 300, false);
		font = new UnicodeFont("res/C&C Red Alert [INET].ttf", 25, false, false);
		font.setPaddingLeft(5);
		font.setPaddingRight(5);
		font.setPaddingTop(5);
		font.setPaddingBottom(5);
		font.setPaddingAdvanceX(-9);
		font.setPaddingAdvanceY(-10);
		font.getEffects().add(new OutlineEffect(5, java.awt.Color.BLACK));
		((OutlineEffect) font.getEffects().get(0)).setJoin(BasicStroke.JOIN_MITER);
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		font.addNeheGlyphs();
		font.loadGlyphs();
		t = new TextField(gc, font, 600, 250, 170, 30);
		t.setBackgroundColor(Color.lightGray);
		t.setBorderColor(Color.black);
		t.setAcceptingInput(true);
	}

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

	public void render(GameContainer gc, Graphics g) throws SlickException {
		switch (gamestate) {
		case STATE_MAINMENU:
			g.setAntiAlias(false);
			g.setBackground(Color.darkGray);
			g.setFont(font);
			g.drawString("Keys:\n" + "toggle build mode : b\n" + "toggle rectangle build mode : r\n" + "toggle wall build mode : w\n" + "point 1 : LMB\n" + "point 2 : RMB\n" + "select next tile:p\n" + "select previous tile : o\n" + "generate path from point 1 to point 2 : g\n" + "click anywhere to start", 300, 200);
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			g.setAntiAlias(false);
			g.setBackground(Color.black);
			g.setFont(font);
			map.draw();
			if (path != null) {
				for (int n = 1; n < path.getLength(); n++) {
					int[] a = Datacenter.tile(path.getStep(n).getX(), path.getStep(n).getY());
					int[] b = Datacenter.tile(path.getStep(n - 1).getX(), path.getStep(n - 1).getY());
					a[0] += Datacenter.tilewidth / 2 * Datacenter.zoom;
					a[1] += Datacenter.tileheight / 2 * Datacenter.zoom;
					b[0] += Datacenter.tilewidth / 2 * Datacenter.zoom;
					b[1] += Datacenter.tileheight / 2 * Datacenter.zoom;
					g.setColor(Color.red);
					g.drawLine(a[0], a[1], b[0], b[1]);
					if (n == path.getLength() - 1 || n == 1)
						g.fillRect(a[0] - 4, a[1] - 4, 8, 8);
					if (n > 1 && n < path.getLength() - 1)
						if (path.getStep(n).getY() - path.getStep(n - 1).getY() != path.getStep(n + 1).getY() - path.getStep(n).getY() || path.getStep(n).getX() - path.getStep(n - 1).getX() != path.getStep(n + 1).getX() - path.getStep(n).getX())
							g.fillRect(a[0] - 4, a[1] - 4, 8, 8);
				}
			}
			g.setColor(Color.lightGray);
			g.fillRect(570, 0, WINDOW_WIDTH - 570, WINDOW_HEIGHT);
			t.render(gc, g);
			g.setColor(Color.white);
			selected.drawwall(600, 340, 2);
			selected.draw(600, 370, 2);
			g.drawString(lt[0] + "," + lt[1], 600, 400);
			g.drawString(rt[0] + "," + rt[1], 600, 430);
			g.drawString("x" + Datacenter.zoom, 600, 460);
			g.drawString("Build:" + build, 600, 490);
			g.drawString("Wall:" + wall, 600, 520);
			g.drawString("Rectangle:" + rect, 600, 550);
			break;
		case STATE_PAUSE:
			break;
		}
	}
}
