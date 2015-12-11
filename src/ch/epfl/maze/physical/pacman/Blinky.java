package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Red ghost from the Pac-Man game, chases directly its target.
 * 
 */

public class Blinky extends Predator {

    /**
     * Constructs a Blinky with a starting position.
     * 
     * @param position
     *            Starting position of Blinky in the labyrinth
     */

    public Blinky(Vector2D position) {
	super(position);
    }

    /**
     * Constructs a Blinky with specified position, last move and mode
     * 
     * @param position
     *            Position of Blinky in the labyrith
     * @param last
     *            Last move made by Blinky, defaults to INVALID_POS
     * @param mode
     *            Current mode Blinky, defaults to UNDEFINED_MODE
     * @param modeCount
     *            Number of steps since last mode swap, defaults to 0
     */

    public Blinky(Vector2D position, Direction last, int mode, int modeCount) {
	super(position, last, mode, modeCount);
    }

    @Override
    public Direction move(Direction[] choices, Daedalus daedalus) {
	if (daedalus.getPreys().isEmpty())
	    return move(choices);

	Vector2D target = null;
	int mode = computeMode();
	if (mode == CHASE_MODE)
	    target = daedalus.getPreys().get(0).getPosition();
	else
	    target = getStartPosition();

	return moveToTarget(choices, target);
    }

    @Override
    public Animal copy() {
	Blinky b = new Blinky(getPosition(), getLast(), getMode(), getModeCount());
	b.setStartPosition(getStartPosition());

	return b;
    }

    @Override
    public void resetAnimal() {
	super.resetAnimal();
    }
}
