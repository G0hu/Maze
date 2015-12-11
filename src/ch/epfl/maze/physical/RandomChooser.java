package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

abstract public class RandomChooser extends Animal {

    private Direction _last = Direction.NONE;
    
    /**
     * 
     * @param position
     */
    
    public RandomChooser(Vector2D position) {
	super(position);
    }
    
    /**
     * 
     * @param position
     * @param last
     */
    
    public RandomChooser(Vector2D position, Direction last) {
	super(position);
	setLast(last);
    }
    
    /*
     * 		GETTERS AND SETTERS
     */
    
    public Direction getLast() {
	return _last;
    }

    public void setLast(Direction d) {
	_last = d;
    }
    
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

    public final Direction randomMove(ArrayList<Direction> choices) {
	if (choices.isEmpty())
	    return Direction.NONE;
	else if (choices.size() == 1)
	    return choices.get(0);

	ArrayList<Direction> available = new ArrayList<Direction>();
	for (int i = 0; i < choices.size(); i++)
	    if (!choices.get(i).isOpposite(getLast()))
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

    public final Direction randomMove(Direction[] choices) {
	ArrayList<Direction> c = new ArrayList<Direction>();
	for (Direction dir : choices)
	    c.add(dir);

	return randomMove(c);
    }

}
