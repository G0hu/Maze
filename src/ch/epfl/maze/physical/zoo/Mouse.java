package ch.epfl.maze.physical.zoo;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.RandomChoose;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Mouse A.I. that remembers only the previous choice it has made.
 * 
 */

public class Mouse extends Animal implements RandomChoose {

    private Direction _last = Direction.NONE;

    /**
     * Constructs a mouse with a starting position.
     * 
     * @param position
     *            Starting position of the mouse in the labyrinth
     */

    public Mouse(Vector2D position) {
	super(position);
    }

    /**
     * Constructs a mouse with a starting position.
     * 
     * @param position
     *            Starting position of the mouse in the labyrinth
     * @param last
     *            Last direction selected by the mouse
     */

    public Mouse(Vector2D position, Direction last) {
	super(position);
	_last = last;
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
     * Moves according to an improved version of a <i>random walk</i> : the
     * mouse does not directly retrace its steps.
     */

    @Override
    public Direction move(Direction[] choices) {
	setLast(randomMove(choices, getLast()));
	return getLast();
    }

    @Override
    public Animal copy() {
	return new Mouse(getPosition(), getLast());
    }

    @Override
    public void resetAnimal() {
	super.resetAnimal();
	setLast(Direction.NONE);
    }
}
