package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Orange ghost from the Pac-Man game, alternates between direct chase if far
 * from its target and SCATTER if close.
 * 
 */

public class Clyde extends Predator {

    /**
     * Constructs a Clyde with a starting position.
     * 
     * @param position
     *            Starting position of Clyde in the labyrinth
     */

    public Clyde(Vector2D position) {
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

    public Clyde(Vector2D position, Direction last, int mode, int modeCount) {
	super(position);
	setLast(last);
	setMode(mode);
	setModeCount(modeCount);
    }

    @Override
    public Direction move(Direction[] choices, Daedalus daedalus) {
	if (daedalus.getPreys().isEmpty())
	    return move(choices);

	int mode = computeMode();
	boolean fakingScatter = false;
	double dist = euclidianDistance(getPosition(), daedalus.getPreys().get(0).getPosition());
	if (dist <= 4)
	    fakingScatter = true;

	Vector2D target = null;
	if (mode == SCATTER_MODE || fakingScatter)
	    target = getStartPosition();
	else if (mode == CHASE_MODE)
	    target = daedalus.getPreys().get(0).getPosition();

	if (target == null)
	    return move(choices);

	return moveToTarget(choices, target);
    }

    @Override
    public Animal copy() {
	Clyde c = new Clyde(getPosition(), getLast(), getMode(), getModeCount());
	c.setStartPosition(getStartPosition());
	
	return c;
    }

    @Override
    public void resetAnimal() {
	super.resetAnimal();
    }
}
