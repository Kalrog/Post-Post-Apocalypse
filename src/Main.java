
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
	MouseOverArea test;
	Image walls;
	
	public Main(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.getGraphics().setAntiAlias(false);
		gc.setShowFPS(false);
		walls = new Image ("res/Walls.png");
		test = new MouseOverArea(gc,new Image("res/Buttonn.jpg"),100,100);
		test.setMouseOverImage(new Image("res/Button.jpg"));
		test.setMouseDownImage( new Image("res/Buttond.jpg"));
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setBackground(new Color(200,200,200));
		test.render(gc, g);
		g.drawImage(walls.getSubImage(0, 0, 40, 145), 5, 5);
		
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Main("ZOMBIES!"));
			appgc.setDisplayMode(640, 480, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}