package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.util.Direction;

public interface RandomChoose {

    /**
     * Selects a random direction from all the ones that are available, making
     * sure that the animal does not go backwards.
     * 
     * @param choices
     *            ArrayList of available directions around the animal
     * @param lastMove
     *            Last move made by the animal
     * @return The random direction selected, without going backwards
     */

    default Direction randomMove(ArrayList<Direction> choices, Direction lastMove) {
	if (choices.isEmpty())
	    return Direction.NONE;
	else if (choices.size() == 1)
	    return choices.get(0);

	ArrayList<Direction> available = new ArrayList<Direction>();
	for (int i = 0; i < choices.size(); i++)
	    if (!choices.get(i).isOpposite(lastMove))
		available.add(choices.get(i));

	Random r = new Random();
	return available.get(r.nextInt(available.size()));
    }

    /**
     * Method implemented for compatibility reasons, please referrer to
     * {@link ch.epfl.maze.physical.RandomChoose#randomMove(ArrayList, Direction)}
     * )
     * 
     * @param choices
     *            Array of free directions around the animal
     * @param lastMove
     *            Last move made by the animal
     * @return The random direction selected, without going backwards
     */

    default Direction randomMove(Direction[] choices, Direction lastMove) {
	ArrayList<Direction> c = new ArrayList<Direction>();
	for (Direction dir : choices)
	    c.add(dir);

	return randomMove(c, lastMove);
    }

}
