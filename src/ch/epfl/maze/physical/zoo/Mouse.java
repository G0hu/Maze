package ch.epfl.maze.physical.zoo;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

import java.util.ArrayList;
import java.util.Random;

/**
 * Mouse A.I. that remembers only the previous choice it has made.
 * 
 */

public class Mouse extends Animal {

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

    public Mouse(Vector2D position, Direction last) {
	super(position);
	_last = last;
    }

    /**
     * Moves according to an improved version of a <i>random walk</i> : the
     * mouse does not directly retrace its steps.
     */

    @Override
    public Direction move(Direction[] choices) {
	if (choices.length == 1) {
	    _last = choices[0];
	    return _last;
	}

	ArrayList<Direction> available = new ArrayList<Direction>();
	for (int i = 0; i < choices.length; i++)
	    if (!choices[i].isOpposite(_last))
		available.add(choices[i]);

	Random r = new Random();
	_last = available.get(r.nextInt(available.size()));
	return _last;
    }

    @Override
    public Animal copy() {
	return new Mouse(getPosition(), _last);
    }

    @Override
    public void resetAnimal() {
	super.resetAnimal();
	_last = Direction.NONE;
    }
}
