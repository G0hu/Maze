package ch.epfl.maze.physical;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

abstract public class WallFollower extends Animal {

    private Direction _orientation = Direction.UP;
    
    public WallFollower(Vector2D position) {
	super(position);
    }
    
    public WallFollower(Vector2D position, Direction orientation) {
	super(position);
	setOrientation(orientation);
    }
    
    /*
     * 		GETTERS AND SETTERS
     */
    
    public Direction getOrientation() {
	return _orientation;
    }

    public void setOrientation(Direction o) {
	_orientation = o;
    }
    
    /**
     * Computes the direction to take in order to always follow the left wall
     * 
     * @param choices
     * 		  Available free tiles around the animal
     * @param orientation
     * 		  Current orientation of the animal in the maze
     * @setOrientation(The direction to follow in order to keep the animal's hand on the left wall
     */

    public final Direction followLeftWall(Direction[] choices) {
	if (choices.length == 0)
	    return Direction.NONE;
	
	boolean right = false;
	boolean left = false;
	boolean up = false;
	for (Direction dir : choices) {
	    if (getOrientation().relativeDirection(dir) == Direction.LEFT)
		left = true;
	    else if (getOrientation().relativeDirection(dir) == Direction.UP)
		up = true;
	    else if (getOrientation().relativeDirection(dir) == Direction.RIGHT)
		right = true;
	}

	if (left)
	    return getOrientation().unRelativeDirection(Direction.LEFT);
	else if (up)
	    return getOrientation().unRelativeDirection(Direction.UP);
	else if (right)
	    return getOrientation().unRelativeDirection(Direction.RIGHT);
	else
	    return getOrientation().unRelativeDirection(Direction.DOWN);
    }

    /**
     * Computes the new orientation according to the current orientation of the animal in the maze
     * and the direction it just took
     * 
     * @param choice
     * 		  The direction chosen by the animal
     * @param orientation
     * 		  It's current orientation in the maze
     * @setOrientation(The resulting new orientation
     */

    public final void computeOrientation(Direction choice) {
	if (getOrientation().relativeDirection(choice) == Direction.LEFT)
	    setOrientation(getOrientation().rotateLeft());
	else if (getOrientation().relativeDirection(choice) == Direction.RIGHT)
	    setOrientation(getOrientation().rotateRight());
	else if (getOrientation().relativeDirection(choice) == Direction.DOWN)
	    setOrientation(getOrientation().reverse());
    }
}
