package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Hamster A.I. that remembers the previous choice it has made and the dead ends
 * it has already met.
 * 
 */

public class Hamster extends Animal {

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
	    _deadLock.add(_position);
	    return newChoices.get(0);
	} else {
	    return randomChoose(newChoices);
	}
    }

    @Override
    public Animal copy() {
	return new Hamster(_position, _deadLock, _last);
    }

    @Override
    public void reset(Vector2D start) {
	_position = start;
	_deadLock.clear();
	_last = Direction.NONE;
    }
    
    /**
     * Computes the new list of available choices without the deadlocks
     * 
     * @param choices
     * 			The list of available directions
     * @return 
     */
    
    private ArrayList<Direction> removeDeadLockTiles(Direction[] choices) {
	ArrayList<Direction> newChoices = new ArrayList<Direction>();
	for (Direction dir : choices) {
	    Vector2D newPos = _position.addDirectionTo(dir);
	    if (!_deadLock.contains(newPos))
		newChoices.add(dir);
	}

	return newChoices;
    }

    /**
     * Chooses a random tile using the mouse's algorithm
     * 
     * @param choices
     * 			Available choices without deadlocks
     * @return The chosen direction
     */
    
    private Direction randomChoose(ArrayList<Direction> choices) {
	ArrayList<Direction> available = new ArrayList<Direction>();
	for (int i = 0; i < choices.size(); i++)
	    if (!choices.get(i).isOpposite(_last))
		available.add(choices.get(i));

	Random r = new Random();
	_last = available.get(r.nextInt(available.size()));
	return _last;
    }
}
