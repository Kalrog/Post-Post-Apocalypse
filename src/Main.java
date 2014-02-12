import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

public class Main extends BasicGame {

	public static final int STATE_MAINMENU = 0;
	public static final int STATE_OPTIONSMENU = 1;
	public static final int STATE_GAME = 2;
	public static final int STATE_PAUSE = 3;

	int gamestate = STATE_MAINMENU;
	boolean fps = true;

	public Main(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setShowFPS(fps);
		gc.setAlwaysRender(false);
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
			break;
		case STATE_OPTIONSMENU:
			break;
		case STATE_GAME:
			break;
		case STATE_PAUSE:
			break;
		}
	}

	public void menu() {

	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Main("ZOMBIES!"));
			appgc.setDisplayMode(appgc.getScreenWidth() / 2,
					appgc.getScreenHeight() / 2, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
