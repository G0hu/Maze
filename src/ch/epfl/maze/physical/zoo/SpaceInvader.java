package ch.epfl.maze.physical.zoo;

import ch.epfl.maze.physical.Animal;
import java.util.ArrayList;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Space Invader A.I. that implements an algorithm of your choice.
 * <p>
 * Note that this class is considered as a <i>bonus</i>, meaning that you do not
 * have to implement it (see statement: section 6, Extensions libres).
 * <p>
 * If you consider implementing it, you will have bonus points on your grade if
 * it can exit a simply-connected maze, plus additional points if it is more
 * efficient than the animals you had to implement.
 * <p>
 * The way we measure efficiency is made by the test case {@code Competition}.
 * 
 * @see ch.epfl.maze.tests.Competition Competition
 * 
 */

public class SpaceInvader extends Animal {
    private Direction _orientation;
    private boolean _reseted;
    private boolean _unknown = false;

    private Vector2D _lastPosition;
    private Direction _last;
    private ArrayList<Vector2D> _markedOnce = new ArrayList<Vector2D>();
    private ArrayList<Vector2D> _markedTwice = new ArrayList<Vector2D>();

    /**
     * Constructs a space invader with a starting position.
     * 
     * @param position
     *            Starting position of the mouse in the labyrinth
     */

    public SpaceInvader(Vector2D position) {
	super(position);
	_orientation = Direction.UP;
	_last = Direction.NONE;
	_lastPosition = new Vector2D(-1, -1);
	_unknown = false;
	_reseted = false;

    }

    public SpaceInvader(Vector2D position, Direction orientation, Direction last, ArrayList<Vector2D> markedOnce,
	    ArrayList<Vector2D> markedTwice, Vector2D lastPosition) {
	super(position);
	_orientation = orientation;
	_last = last;
	_markedOnce = markedOnce;
	_markedTwice = markedTwice;
	_lastPosition = lastPosition;
    }

    /**
     * Moves according to (... please complete with as many details as you can).
     */

    @Override
    public Direction move(Direction[] choices) {
	if (getPosition().equals(_lastPosition)) {
	    _unknown = true;
	}
	if (_unknown) {
	    return discoveryMode(choices);

	} else {

	    return memoryMode(choices);

	}
    }

    @Override
    public Animal copy() {
	return new SpaceInvader(getPosition(), _orientation, _last, _markedOnce, _markedTwice, _lastPosition);
    }

    @Override
    public void resetAnimal() {
	_lastPosition = getPosition();
	super.resetAnimal();
	
	_orientation = Direction.UP;
	_last = Direction.NONE;
	_unknown = false;
	_reseted = true;
    }

    private void markTile(Vector2D v) {
	if (_markedOnce.contains(v)) {
	    _markedTwice.add(v);
	    _markedOnce.remove(v);
	} else if (!(_markedTwice.contains(v))) {
	    _markedOnce.add(v);
	}
    }

    private Direction monkeyMove(Direction[] choices) {

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
	    _last = dir;
	    return dir;
	} else if (up) {
	    Direction dir = _orientation.unRelativeDirection(Direction.UP);
	    _last = dir;
	    return dir;
	} else if (right) {
	    Direction dir = _orientation.unRelativeDirection(Direction.RIGHT);
	    _orientation = _orientation.rotateRight();
	    _last = dir;
	    return dir;
	} else {
	    Direction dir = _orientation.unRelativeDirection(Direction.DOWN);
	    _orientation = _orientation.reverse();
	    _last = dir;
	    return dir;
	}
    }

    private Direction discoveryMode(Direction[] choices) {
	markTile(getPosition());
	return monkeyMove(choices);
    }

    private boolean isIntersection(Direction choices[]) {
	if (choices.length >= 3)
	    return true;

	return false;
    }

    private Direction memoryMode(Direction[] choices) {
	Direction next = Direction.NONE;
	if (isIntersection(choices)) {
	    for (Direction dir : choices) {
		Vector2D v = getPosition().addDirectionTo(dir);
		if ((_markedOnce.contains(v)) && (!_last.isOpposite(dir))) {
		    next = dir;
		}
	    }

	    if (next == Direction.NONE) {
		return monkeyMove(choices);
	    }

	    _last = next;
	    if (_orientation.relativeDirection(_last) == Direction.LEFT)
		_orientation = _orientation.rotateLeft();
	    else if (_orientation.relativeDirection(_last) == Direction.RIGHT) {
		_orientation = _orientation.rotateRight();
	    } else if (_orientation.relativeDirection(_last) == Direction.DOWN) {
		_orientation = _orientation.reverse();
	    }

	    return _last;

	} else {
	    return monkeyMove(choices);
	}
    }
}