import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.util.pathfinding.Path;

public class Unit {
	/** The unit is controlled by the zombie AI */
	public static final int ZOMBIE = 0;
	/** The unit is controlled by the player */
	public static final int PLAYER = 1;
	/** The x-coordinate of the unit */
	int x;
	/** The y-coordinate of the unit */
	int y;
	/**
	 * The team the unit is on
	 * 
	 * @param team
	 *            One of: {@link Unit#PLAYER}, {@link Unit#ZOMBIE}
	 * */
	int team;
	/** The Text that is displayed as unit information */
	String tooltip;
	/** The unit's inventory */
	List<Item> inventory;
	/** The image used to display the unit */
	Image display;
	/** The path the Unit is following*/
	Path path;
}
