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
