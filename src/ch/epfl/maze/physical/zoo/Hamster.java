package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.RandomChooser;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Hamster A.I. that remembers the previous choice it has made and the dead ends
 * it has already met.
 * 
 */

public class Hamster extends RandomChooser {

    private ArrayList<Vector2D> _deadLock = new ArrayList<Vector2D>();

    /**
     * Constructs a hamster with a starting position.
     * 
     * @param position
     *            Starting position of the hamster in the labyrinth
     */

    public Hamster(Vector2D position) {
        super(position);
    }

    /**
     * 
     * @param position
     * @param deadLocks
     * @param last
     */

    public Hamster(Vector2D position, ArrayList<Vector2D> deadLocks,
            Direction last) {
        super(position);
        setLast(last);
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
            _deadLock.add(getPosition());
            return newChoices.get(0);
        } else {
            setLast(randomMove(newChoices));
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
