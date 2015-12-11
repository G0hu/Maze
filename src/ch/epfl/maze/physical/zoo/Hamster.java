package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.RandomChoose;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Hamster A.I. that remembers the previous choice it has made and the dead ends
 * it has already met.
 * 
 */

public class Hamster extends Animal implements RandomChoose {

    private ArrayList<Vector2D> _deadLock = new ArrayList<Vector2D>();
    private Direction _last = Direction.NONE;

    /**
     * Constructs a hamster with a starting position.
     * 
     * @param position
     *            Starting position of the hamster in the labyrinth
     */

    public Hamster(Vector2D position) {
	super(position);
    }

    public Hamster(Vector2D position, ArrayList<Vector2D> deadLocks, Direction last) {
	super(position);
	_last = last;
	_deadLock = deadLocks;
    }

    /*
     * GETTERS AND SETTERS
     */

    public Direction getLast() {
	return _last;
    }

    public void setLast(Direction d) {
	_last = d;
    }

    /**
     * Moves without retracing directly its steps and by avoiding the dead-ends
     * it learns during its journey.
     */

    @Override
    public Direction move(Direction[] choices) {
	ArrayList<Direction> newChoices = removeDeadLockTiles(choices);
	if (newChoices.isEmpty()) {
	    return Direction.NONE;
	} else if (newChoices.size() == 1) {
	    _deadLock.add(getPosition());
	    return newChoices.get(0);
	} else {
	    setLast(randomMove(newChoices, getLast()));
	    return getLast();
	}
    }

    @Override
    public Animal copy() {
	return new Hamster(getPosition(), _deadLock, getLast());
    }

    @Override
    public void resetAnimal() {
	super.resetAnimal();
	_deadLock.clear();
	setLast(Direction.NONE);
    }

    /**
     * Computes the new list of available choices without the deadlocks
     * 
     * @param choices
     *            The list of available directions
     * @return
     */

    private ArrayList<Direction> removeDeadLockTiles(Direction[] choices) {
	ArrayList<Direction> newChoices = new ArrayList<Direction>();
	for (Direction dir : choices) {
	    Vector2D newPos = getPosition().addDirectionTo(dir);
	    if (!_deadLock.contains(newPos))
		newChoices.add(dir);
	}

	return newChoices;
    }
}
