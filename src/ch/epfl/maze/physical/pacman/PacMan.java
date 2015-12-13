package ch.epfl.maze.physical.pacman;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Pac-Man character, from the famous game of the same name.
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
    
    public Direction getOrientation() {
	return _orientation;
    }

    public void setOrientation(Direction d) {
	_orientation = d;
    }
    
    @Override
    public Direction move(Direction[] choices, Daedalus daedalus) {
	List<Predator> positions = new ArrayList<Predator>();
	for (Predator pos : daedalus.getPredators())
	    if (euclidianDistance(getPosition(), pos.getPosition()) <= 3)
		positions.add(pos);

	if (positions.size() == 0) {
	    Direction choice = moveToTarget0(choices, baryCentreTile(daedalus.getPredators(), daedalus, 10));
	    if (choice == getOrientation().unRelativeDirection(Direction.LEFT))
		setOrientation(getOrientation().rotateLeft());
	    else if (choice == getOrientation().unRelativeDirection(Direction.RIGHT))
		setOrientation(getOrientation().rotateRight());
	    else if (choice == getOrientation().unRelativeDirection(Direction.DOWN))
		setOrientation(getOrientation().reverse());

	    return choice;
	} else if (positions.size() == 1) {
	    Direction choice = moveToTarget1(choices, positions.get(0).getPosition());
	    if (choice == getOrientation().unRelativeDirection(Direction.LEFT))
		setOrientation(getOrientation().rotateLeft());
	    else if (choice == getOrientation().unRelativeDirection(Direction.RIGHT))
		setOrientation(getOrientation().rotateRight());
	    else if (choice == getOrientation().unRelativeDirection(Direction.DOWN))
		setOrientation(getOrientation().reverse());

	    return choice;
	} else {
	    Direction choice = moveToTarget0(choices, baryCentreTile(positions, daedalus, 4));
	    if (choice == getOrientation().unRelativeDirection(Direction.LEFT))
		setOrientation(getOrientation().rotateLeft());
	    else if (choice == getOrientation().unRelativeDirection(Direction.RIGHT))
		setOrientation(getOrientation().rotateRight());
	    else if (choice == getOrientation().unRelativeDirection(Direction.DOWN))
		setOrientation(getOrientation().reverse());

	    return choice;
	}
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

    public Vector2D baryCentreTile(List<Predator> pred, Daedalus daedalus, int distance) {
	int x = 0;
	int y = 0;
	List<Vector2D> positions = new ArrayList<Vector2D>();
	for (Predator pos : pred)
	    if (euclidianDistance(getPosition(), pos.getPosition()) <= distance)
		positions.add(pos.getPosition());

	if (positions.isEmpty())
	    return getPosition();
	
	for (Vector2D pos : positions) {
	    x += pos.getX();
	    y += pos.getY();
	}
	return new Vector2D(x/4, y/4);
    }

    private Direction moveToTarget0(Direction[] choices, Vector2D target) {
	if ((choices.length == 0) || (choices.length == 1))
	    return move(choices);

	Direction bestDirection = Direction.NONE;
	double bestValue = Double.NEGATIVE_INFINITY;
	for (Direction dir : choices) {
	    Vector2D newPos = getPosition().addDirectionTo(dir);
	    double distance = euclidianDistance(newPos, target);
	    if ((distance > bestValue) && (!dir.isOpposite(getLast()))) {
		bestValue = distance;
		bestDirection = dir;
	    }
	}

	setLast(bestDirection);
	return bestDirection;
    }

    private Direction moveToTarget1(Direction[] choices, Vector2D target) {
	if ((choices.length == 0) || (choices.length == 1))
	    return move(choices);

	Direction bestDirection = Direction.NONE;
	double bestValue = Double.NEGATIVE_INFINITY;
	for (Direction dir : choices) {
	    Vector2D newPos = getPosition().addDirectionTo(dir);
	    double distance = euclidianDistance(newPos, target);
	    if ((distance > bestValue)) {
		bestValue = distance;
		bestDirection = dir;
	    }
	}

	setLast(bestDirection);
	return bestDirection;
    }

    private double euclidianDistance(Vector2D a, Vector2D b) {
	Vector2D diff = a.sub(b);
	return diff.dist();
    }
}