package ch.epfl.maze.physical;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Prey that is killed by a predator when they meet each other in the labyrinth.
 * 
 */

abstract public class Prey extends Animal {

	/**
	 * Constructs a prey with a specified position.
	 * 
	 * @param position
	 *            Position of the prey in the labyrinth
	 */

	public Prey(Vector2D position) {
		super(position);
		// TODO
	}

	/**
	 * Moves according to a <i>random walk</i>, used while not being hunted in a
	 * {@code MazeSimulation}.
	 * 
	 */

	@Override
	public final Direction move(Direction[] choices) {
		// TODO
		return Direction.NONE;
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
