package ch.epfl.maze.physical.pacman;

import java.util.List;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Blue ghost from the Pac-Man game, targets the result of two times the vector
 * from Blinky to its target.
 * 
 */

public class Inky extends Predator {

    /**
     * Constructs a Inky with a starting position.
     * 
     * @param position
     *            Starting position of Inky in the labyrinth
     */

    public Inky(Vector2D position) {
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
    
    public Inky(Vector2D position, Direction last, int mode, int modeCount) {
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
	if (mode == CHASE_MODE) {
	    Blinky blinky = getBlinky(daedalus.getPredators());
	    if (blinky == null)
		return Direction.NONE;

	    Vector2D target = daedalus.getPreys().get(0).getPosition().mul(2).sub(blinky.getPosition());
	    return moveToTarget(choices, target);
	} else
	    return moveToTarget(choices, getStartPosition());
    }

    @Override
    public Animal copy() {
	Inky i = new Inky(getPosition(), getLast(), getMode(), getModeCount());
	i.setStartPosition(getStartPosition());

	return i;
    }

    @Override
    public void resetAnimal() {
	super.resetAnimal();
    }

    private Blinky getBlinky(List<Predator> predators) {
	for (Predator p : predators)
	    if (p instanceof Blinky)
		return (Blinky) p;

	return null;
    }
}
