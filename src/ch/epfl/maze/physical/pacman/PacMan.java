package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Pac-Man character, from the famous game of the same name.
 * 
 */

public class PacMan extends Prey {

    public PacMan(Vector2D position) {
	super(position);
	// TODO
    }

    @Override
    public Direction move(Direction[] choices, Daedalus daedalus) {
	// TODO
	return move(choices);
    }

    @Override
    public Animal copy() {
	PacMan p = new PacMan(getPosition());
	p.setStartPosition(getStartPosition());
	return p;
    }

    @Override
    public void resetAnimal() {
	super.resetAnimal();
	setLast(Direction.NONE);
    }
}
