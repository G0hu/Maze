package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Prey that is killed by a predator when they meet each other in the labyrinth.
 * 
 */

abstract public class Prey extends Animal {

    private Direction _last = Direction.NONE;

    /**
     * Constructs a prey with a specified position.
     * 
     * @param position
     *            Position of the prey in the labyrinth
     */

    public Prey(Vector2D position) {
	super(position);
    }

    public Prey(Vector2D position, Direction last) {
	super(position);
	setLast(last);
    }

    /**
     * Moves according to a <i>random walk</i>, used while not being hunted in a
     * {@code MazeSimulation}.
     * 
     */

    @Override
    public final Direction move(Direction[] choices) {
	if (choices.length == 0) {
	    setLast(Direction.NONE);
	    return Direction.NONE;
	} else if (choices.length == 1) {
	    setLast(choices[0]);
	    return choices[0];
	}

	List<Direction> filteredChoices = new ArrayList<Direction>();
	for (Direction dir : choices)
	    if (!dir.isOpposite(getLast()))
		filteredChoices.add(dir);

	Random rand = new Random();
	Direction choice = filteredChoices.get(rand.nextInt(filteredChoices.size()));

	setLast(choice);
	return choice;
    }

    public final Direction getLast() {
	return _last;
    }

    public final void setLast(Direction last) {
	_last = last;
    }

    /**
     * Retrieves the next direction of the animal, by selecting one choice among
     * the ones available from its position.
     * <p>
     * In this variation, the animal knows the world entirely. It can therefore
     * use the position of other animals in the daedalus to evade predators more
     * effectively.
     * 
     * @param choices
     *            The choices left to the animal at its current position (see
     *            {@link ch.epfl.maze.physical.World#getChoices(Vector2D)
     *            World.getChoices(Vector2D)})
     * @param daedalus
     *            The world in which the animal moves
     * @return The next direction of the animal, chosen in {@code choices}
     */

    abstract public Direction move(Direction[] choices, Daedalus daedalus);
}
