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
	int gamestate = STATE_GAME;
	float tx, ty;
	float zoom = 2;
	float uiSize = 5;
	UnicodeFont font;
	Image testimg;
	Map testmap;

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
			testmap.setX(testmap.getX() + newx - oldx);
			testmap.setY(testmap.getY() + newy - oldy);
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
			ty = (b - a)/2;
			tx = b - ty;
			ty = Math.round(ty);
			tx = Math.round(tx);
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
		testmap = new Map();
		testmap.test(testimg.getSubImage(14, 0, 14, 8));
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
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			g.setAntiAlias(false);
			g.setBackground(Color.darkGray);
			g.setFont(font);
			testmap.draw(gc, zoom);
			// g.drawString("zoom:" + zoom, 600, 400);
			g.drawString(tx + "," + ty, 600, 400);
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
