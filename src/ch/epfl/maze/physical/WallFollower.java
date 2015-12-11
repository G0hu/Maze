package ch.epfl.maze.physical;

import ch.epfl.maze.util.Direction;

public interface WallFollower {

    /**
     * Computes the direction to take in order to always follow the left wall
     * 
     * @param choices
     * 		  Available free tiles around the animal
     * @param orientation
     * 		  Current orientation of the animal in the maze
     * @return The direction to follow in order to keep the animal's hand on the left wall
     */

    default public Direction followLeftWall(Direction[] choices, Direction orientation) {
	if (choices.length == 0)
	    return Direction.NONE;

	boolean right = false;
	boolean left = false;
	boolean up = false;
	for (Direction dir : choices) {
	    if (orientation.relativeDirection(dir) == Direction.LEFT)
		left = true;
	    else if (orientation.relativeDirection(dir) == Direction.UP)
		up = true;
	    else if (orientation.relativeDirection(dir) == Direction.RIGHT)
		right = true;
	}

	if (left)
	    return orientation.unRelativeDirection(Direction.LEFT);
	else if (up)
	    return orientation.unRelativeDirection(Direction.UP);
	else if (right)
	    return orientation.unRelativeDirection(Direction.RIGHT);
	else
	    return orientation.unRelativeDirection(Direction.DOWN);
    }

    /**
     * Computes the new orientation according to the current orientation of the animal in the maze
     * and the direction it just took
     * 
     * @param choice
     * 		  The direction chosen by the animal
     * @param orientation
     * 		  It's current orientation in the maze
     * @return The resulting new orientation
     */

    default public Direction computeOrientation(Direction choice, Direction orientation) {
	if (orientation.relativeDirection(choice) == Direction.LEFT)
	    return orientation.rotateLeft();
	else if (orientation.relativeDirection(choice) == Direction.RIGHT)
	    return orientation.rotateRight();
	else if (orientation.relativeDirection(choice) == Direction.DOWN)
	    return orientation.reverse();
	else
	    return orientation;
    }

    /**
     * Computes the rotation counter depending on the direction the animal just took and the previous
     * value of the counter
     * 
     * @param choice
     * 		  The direction chosen by the animal
     * @param orientation
     * 		  It's current orientation in the maze
     * @param counter
     * 		  The current value of the animal's rotation counter
     * @return
     */

    default public int computeRotationCounter(Direction choice, Direction orientation, int counter) {
	if (orientation.relativeDirection(choice) == Direction.LEFT)
	    return counter -= 1;
	else if (orientation.relativeDirection(choice) == Direction.RIGHT)
	    return counter += 1;
	else if (orientation.relativeDirection(choice) == Direction.DOWN)
	    return counter += 2;
	else
	    return counter;
    }
}
