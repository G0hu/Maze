package ch.epfl.maze.physical.zoo;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.WallFollower;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Bear A.I. that implements the Pledge Algorithm.
 * 
 */

public class Bear extends Animal implements WallFollower {

    private Direction _orientation = Direction.UP;
    private Direction _preferred = Direction.NONE;
    private int _counter = 0;

    /**
     * Constructs a bear with a starting position.
     * 
     * @param position
     *            Starting position of the bear in the labyrinth
     */

    public Bear(Vector2D position) {
	super(position);
    }

    /**
     * Constructs a bear with a starting position, a preferred direction, an
     * orientation and a rotation counter
     * 
     * @param position
     *            Starting position of the bear in the labyrinth
     * @param preferred
     *            Preferred direction when the rotation counter is 0
     * @param orientation
     *            Current orientation of the bear in the maze
     * @param counter
     *            Rotation counter
     */

    public Bear(Vector2D position, Direction preferred, Direction orientation, int counter) {
	super(position);
	setCounter(_counter);
	setPreferred(preferred);
	setOrientation(orientation);
    }

    /*
     * GETTERS AND SETTERS
     */

    public Direction getPreferred() {
	return _preferred;
    }

    public Direction getOrientation() {
	return _orientation;
    }

    public int getCounter() {
	return _counter;
    }

    public void setPreferred(Direction d) {
	_preferred = d;
    }

    public void setOrientation(Direction d) {
	_orientation = d;
    }

    public void setCounter(int c) {
	_counter = c;
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
	if (getPreferred() == Direction.NONE) {
	    if (choices.length == 0)
		return Direction.NONE;

	    // choose favorite direction from the available one at Start
	    // position
	    setPreferred(choices[0]);
	}

	// check if favorite direction is available
	boolean preferred = false;
	for (Direction dir : choices)
	    if (dir == getPreferred())
		preferred = true;

	if (getCounter() == 0) {
	    if (preferred) {
		if (getOrientation().relativeDirection(getPreferred()) == Direction.LEFT)
		    setOrientation(getOrientation().rotateLeft());
		else if (getOrientation().relativeDirection(getPreferred()) == Direction.DOWN)
		    setOrientation(getOrientation().reverse());
		else if (getOrientation().relativeDirection(getPreferred()) == Direction.RIGHT)
		    setOrientation(getOrientation().rotateRight());

		return getPreferred();

	    } else {
		setCounter(1);
		setOrientation(getOrientation().rotateRight());

		// Monkey Move
		Direction choice = followLeftWall(choices, getOrientation());
		setOrientation(computeOrientation(choice, getOrientation()));
		setCounter(computeRotationCounter(choice, getOrientation(), getCounter()));

		return choice;
	    }

	} else {
	    // Monkey Move
	    Direction choice = followLeftWall(choices, getOrientation());
	    setOrientation(computeOrientation(choice, getOrientation()));
	    setCounter(computeRotationCounter(choice, getOrientation(), getCounter()));

	    return choice;
	}
    }

    @Override
    public Animal copy() {
	return new Bear(getPosition(), getPreferred(), getOrientation(), getCounter());
    }

    @Override
    public void resetAnimal() {
	super.resetAnimal();
	setCounter(0);
	setPreferred(Direction.NONE);
	setOrientation(Direction.UP);
    }
}