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
 * 
 */

public class PacMan extends Prey {
    private final int CRITIC_DISTANCE = 1;
    private final int MEDIUM_DISTANCE = 5;
    private final int LARGE_DISTANCE = 10;

    private Direction _orientation = Direction.UP;

    List<Vector2D> _deadLock = new ArrayList<Vector2D>();

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
	boolean critic = false;
	List<Predator> positions = new ArrayList<Predator>();
	for (Predator pos : daedalus.getPredators())
	    if (euclidianDistance(getPosition(), pos.getPosition()) <= MEDIUM_DISTANCE)
		positions.add(pos);
	for (Predator pos : daedalus.getPredators())
	    if (euclidianDistance(getPosition(), pos.getPosition()) <= CRITIC_DISTANCE)
		critic = true;
	if (critic) {
	    Direction choice = moveToTarget1(choices, positions.get(0).getPosition(), daedalus);
	    computeOrientation(choice);
	    return choice;
	}

	if (positions.size() == 0) {
	    Direction choice = moveToTarget0(choices, baryCentreTile(daedalus.getPredators(), daedalus, LARGE_DISTANCE),
		    daedalus);
	    computeOrientation(choice);
	    return choice;
	} else if (positions.size() == 1) {
	    Direction choice = moveToTarget1(choices, positions.get(0).getPosition(), daedalus);
	    computeOrientation(choice);
	    return choice;
	} else {
	    Direction choice = moveToTarget0(choices, baryCentreTile(positions, daedalus, MEDIUM_DISTANCE), daedalus);
	    computeOrientation(choice);
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
	return new Vector2D(x / 4, y / 4);

    }

    private Direction moveToTarget0(Direction[] choices, Vector2D target, Daedalus daedalus) {

	Direction deadlockNext = Direction.NONE;

	if (_deadLock.isEmpty())
	    _deadLock.equals(analyseDaedalus(daedalus));

	if (_deadLock.contains(getPosition())) {
	    for (Direction dir : choices) {
		Vector2D newPos = getPosition().addDirectionTo(dir);
		if (_deadLock.contains(newPos))
		    deadlockNext = dir;
	    }
	}

	if ((choices.length == 0) || (choices.length == 1))
	    return move(choices);

	Direction bestDirection = Direction.NONE;
	double bestValue = Double.NEGATIVE_INFINITY;
	for (Direction dir : choices) {
	    Vector2D newPos = getPosition().addDirectionTo(dir);
	    double distance = euclidianDistance(newPos, target);
	    if ((distance > bestValue) && (!dir.isOpposite(getLast())) && !(dir == deadlockNext)) {
		bestValue = distance;
		bestDirection = dir;
	    }
	}

	setLast(bestDirection);
	return bestDirection;
    }

    private Direction moveToTarget1(Direction[] choices, Vector2D target, Daedalus daedalus) {
	Direction deadlockNext = Direction.NONE;
	if (_deadLock.isEmpty())
	    _deadLock.equals(analyseDaedalus(daedalus));

	if (_deadLock.contains(getPosition())) {
	    for (Direction dir : choices) {
		Vector2D newPos = getPosition().addDirectionTo(dir);
		if (_deadLock.contains(newPos))
		    deadlockNext = dir;
	    }
	}

	if ((choices.length == 0) || (choices.length == 1))
	    return move(choices);

	Direction bestDirection = Direction.NONE;
	double bestValue = Double.NEGATIVE_INFINITY;
	for (Direction dir : choices) {
	    Vector2D newPos = getPosition().addDirectionTo(dir);
	    double distance = euclidianDistance(newPos, target);
	    if ((distance > bestValue) && !(deadlockNext == dir)) {
		bestValue = distance;
		bestDirection = dir;
	    }
	}

	setLast(bestDirection);
	return bestDirection;
    }

    private List<Vector2D> analyseDaedalus(Daedalus d) {
	Boolean[][] daedalus = new Boolean[d.getHeight()][d.getHeight()];
	for (int y = 0; y < d.getHeight(); y++) {
	    for (int x = 0; x < d.getHeight(); x++) {
		if (d.isFree(y, x))
		    daedalus[y][x] = true;
		else
		    daedalus[y][x] = false;
	    }
	}

	List<Vector2D> deadLock = new ArrayList<Vector2D>();
	for (int y = 0; y < d.getHeight(); y++) {
	    for (int x = 0; x < d.getHeight(); x++) {
		Vector2D pos = new Vector2D(y, x);
		if (daedalus[y][x] && (d.getChoices(pos).length == 1)) {
		    do {
			deadLock.add(pos);
			pos = next(pos, deadLock, d.getChoices(pos));
		    } while ((d.getChoices(pos).length < 3) && (pos != null));
		}
	    }
	}

	return deadLock;
    }

    private Vector2D next(Vector2D pos, List<Vector2D> deadLock, Direction[] choices) {
	if (choices.length > 2)
	    return null;
	List<Vector2D> v = new ArrayList<Vector2D>();
	for (Direction dir : choices)
	    if (!deadLock.contains(pos.addDirectionTo(dir)))
		v.add(pos.addDirectionTo(dir));
	
	if (v.isEmpty())
	    return null;
	else
	    return v.get(0);
    }

    private final void computeOrientation(Direction choice) {
	if (getOrientation().relativeDirection(choice) == Direction.LEFT)
	    setOrientation(getOrientation().rotateLeft());
	else if (getOrientation().relativeDirection(choice) == Direction.RIGHT)
	    setOrientation(getOrientation().rotateRight());
	else if (getOrientation().relativeDirection(choice) == Direction.DOWN)
	    setOrientation(getOrientation().reverse());
    }
    
    private double euclidianDistance(Vector2D a, Vector2D b) {
	Vector2D diff = a.sub(b);
	return diff.dist();
    }
}