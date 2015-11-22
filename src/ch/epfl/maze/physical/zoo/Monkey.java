package ch.epfl.maze.physical.zoo;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Monkey A.I. that puts its hand on the left wall and follows it.
 * 
 */

public class Monkey extends Animal {

    private Direction _orientation = Direction.UP;
    
    /**
     * Constructs a monkey with a starting position.
     * 
     * @param position
     *            Starting position of the monkey in the labyrinth
     */

    public Monkey(Vector2D position) {
	super(position);
    }
    
    public Monkey(Vector2D position, Direction orientation) {
	super(position);
	_orientation = orientation;
    }

    /**
     * Moves according to the relative left wall that the monkey has to follow.
     */

    @Override
    public Direction move(Direction[] choices) {
	boolean right = false;
	boolean left = false;
	boolean up = false;
	for (Direction dir : choices) {
	    if (_orientation.relativeDirection(dir) == Direction.LEFT)
		left = true;
	    else if (_orientation.relativeDirection(dir) == Direction.UP)
		up = true;
	    else if (_orientation.relativeDirection(dir) == Direction.RIGHT)
	    	right = true;
	}
	
	if (left) {
	    Direction dir = _orientation.unRelativeDirection(Direction.LEFT);
	    _orientation = _orientation.rotateLeft();
	    return dir;
	} else if (up) {
	    Direction dir = _orientation.unRelativeDirection(Direction.UP);
	    return dir;
	} else if (right) {
	    Direction dir = _orientation.unRelativeDirection(Direction.RIGHT);
	    _orientation = _orientation.rotateRight();
	    return dir;
	} else {
	    Direction dir = _orientation.unRelativeDirection(Direction.DOWN);
	    _orientation = _orientation.reverse();
	    return dir;
	}
    }
    
    @Override
    public Animal copy() {
	return new Monkey(_position, _orientation);
    }
    
    @Override
    public void reset(Vector2D start) {
	_position = start;
	_orientation = Direction.UP;
    }
}
