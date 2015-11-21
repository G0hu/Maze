package ch.epfl.maze.physical.pacman;

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
		// TODO
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		// TODO
		return Direction.NONE;
	}

	@Override
	public Animal copy() {
		// TODO
		return null;
	}
}
