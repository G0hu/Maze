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

    private Direction _orientation = Direction.UP;

    public PacMan(Vector2D position) {
	super(position);
    }

    public PacMan(Vector2D position, Direction o) {
	super(position);
	setOrientation(o);
    }

    @Override
    public Direction move(Direction[] choices, Daedalus daedalus) {
	Direction choice = move(choices);
	if (choice == _orientation.unRelativeDirection(Direction.LEFT))
	    setOrientation(_orientation.rotateLeft());
	else if (choice == _orientation.unRelativeDirection(Direction.RIGHT))
	    setOrientation(_orientation.rotateRight());
	else if (choice == _orientation.unRelativeDirection(Direction.DOWN))
	    setOrientation(_orientation.reverse());

	return choice;
    }

    @Override
    public Animal copy() {
	PacMan p = new PacMan(getPosition(), getOrientation());
	p.setStartPosition(getStartPosition());
	return p;
    }

    @Override
    public void resetAnimal() {
	super.resetAnimal();
	setLast(Direction.NONE);
	setOrientation(Direction.UP);
    }

    public Direction getOrientation() {
	return _orientation;
    }

    public void setOrientation(Direction d) {
	_orientation = d;
    }
}
