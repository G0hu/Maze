package ch.epfl.maze.physical.zoo;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Bear A.I. that implements the Pledge Algorithm.
 * 
 */

public class Bear extends Animal {
    private Direction _prefered;
    private Direction _orientation;
    private int _counter;

    /**
     * Constructs a bear with a starting position.
     * 
     * @param position
     *            Starting position of the bear in the labyrinth
     */

    public Bear(Vector2D position) {
	super(position);
	_prefered = Direction.NONE;
	_orientation = Direction.UP;
	_counter = 0;
    }

    public Bear(Vector2D position, Direction prefered, Direction orientation, int counter) {
	super(position);
	_prefered = prefered;
	_orientation = orientation;
	_counter = counter;
    }

    /**
     * Moves according to the <i>Pledge Algorithm</i> : the bear tries to move
     * towards a favorite direction until it hits a wall. In this case, it will
     * turn right, put its paw on the left wall, count the number of times it
     * turns right, and subtract to this the number of times it turns left. It
     * will repeat the procedure when the counter comes to zero, or until it
     * leaves the maze.
     */

    @Override
    public Direction move(Direction[] choices) {
	boolean prefered = false;

	// choose favorite direction from the available one at Start position

	if (_prefered == Direction.NONE) {
	    if (choices.length == 0) {
		return Direction.NONE;
	    }
	    _prefered = choices[0];
	}
	// check if favorite direction available

	for (Direction dir : choices) {
	    if (dir == _prefered) {
		prefered = true;
	    }
	}

	if (_counter == 0) {
	    if (prefered) {
		if (_orientation.relativeDirection(_prefered) == Direction.LEFT) {
		    _orientation = _orientation.rotateLeft();
		} else if (_orientation.relativeDirection(_prefered) == Direction.DOWN) {
		    _orientation = _orientation.reverse();
		} else if (_orientation.relativeDirection(_prefered) == Direction.RIGHT) {
		    _orientation = _orientation.rotateRight();
		}
		return _prefered;
	    } else {
		_counter = 1;
		_orientation = _orientation.rotateRight();
		return monkeyMove(choices);
	    }
	} else {
	    return monkeyMove(choices);
	}

    }

    @Override
    public Animal copy() {
	return new Bear(getPosition(), _prefered, _orientation, _counter);
    }

    @Override
    public void resetAnimal() {
	super.resetAnimal();
	_prefered = Direction.NONE;
	_orientation = Direction.UP;
	_counter = 0;
    }

    private Direction monkeyMove(Direction[] choices) {
	boolean up = false;
	boolean left = false;
	boolean right = false;

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
	    _counter -= 1;
	    return dir;
	} else if (up) {
	    Direction dir = _orientation.unRelativeDirection(Direction.UP);
	    return dir;
	} else if (right) {
	    Direction dir = _orientation.unRelativeDirection(Direction.RIGHT);
	    _orientation = _orientation.rotateRight();
	    _counter += 1;
	    return dir;
	} else {
	    Direction dir = _orientation.unRelativeDirection(Direction.DOWN);
	    _orientation = _orientation.reverse();
	    _counter += 2;
	    return dir;
	}
    }
}