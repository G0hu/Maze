package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Panda A.I. that implements Trémeaux's Algorithm.
 * 
 */
public class Panda extends Animal {

    // Algorithm constants
    private static final int TILE_NEVER_MARKED = 1;
    private static final int TILE_MARKED_ONCE = 2;
    private static final int TILE_MARKED_TWICE = 3;

    private Direction _last = Direction.NONE;
    private ArrayList<Vector2D> _markedOnce = new ArrayList<Vector2D>();
    private ArrayList<Vector2D> _markedTwice = new ArrayList<Vector2D>();

    /**
     * Constructs a panda with a starting position.
     * 
     * @param position
     *            Starting position of the panda in the labyrinth
     */

    public Panda(Vector2D position) {
	super(position);
    }

    public Panda(Vector2D position, ArrayList<Vector2D> markedOnce, ArrayList<Vector2D> markedTwice,
	    Direction lastMove) {
	super(position);
	_last = lastMove;
	_markedOnce = markedOnce;
	_markedTwice = markedTwice;
    }

    /**
     * Moves according to <i>Trémeaux's Algorithm</i>: when the panda moves, it
     * will mark the ground at most two times (with two different colors). It
     * will prefer taking the least marked paths. Special cases have to be
     * handled, especially when the panda is at an intersection.
     */

    @Override
    public Direction move(Direction[] choices) {
	boolean mark = true;
	Direction choosen = Direction.NONE;
	ArrayList<Direction> never = sortNeverMarkedTiles(choices);
	ArrayList<Direction> once = sortMarkedOnceTiles(choices);
	ArrayList<Direction> twice = sortMarkedTwiceTiles(choices);

	if (isIntersection(choices) && (once.size() == choices.length))
	    choosen = _last.reverse();
	
	//We mark the tile only once because it will marked a second time before the return
	if ((choices.length == 1) && (getTileMark(getPosition().addDirectionTo(choices[0])) == TILE_MARKED_ONCE))
	    markTile(getPosition());
	
	if (choosen == Direction.NONE) {
	    if (!never.isEmpty())
		choosen = randomChoose(never);
	    else if (!once.isEmpty())
		choosen = randomChoose(once);
	    else if (!twice.isEmpty())
		choosen = randomChoose(twice);
	    else
		return Direction.NONE;
	}

	once.remove(choosen);
	never.remove(choosen);
	if (isIntersection(choices) && (getTileMark(getPosition()) == TILE_MARKED_ONCE) && ((never.size() + once.size()) >= 1))
	    mark = false;
	
	if (mark)
	    markTile(getPosition());

	_last = choosen;
	return choosen;
    }

    @Override
    public Animal copy() {
	return new Panda(getPosition(), _markedOnce, _markedTwice, _last);
    }

    @Override
    public void reset(Vector2D start) {
	setPosition(start);
	_markedOnce.clear();
	_markedTwice.clear();
	_last = Direction.NONE;
    }

    private Direction randomChoose(ArrayList<Direction> choices) {
	if (choices.size() == 1)
	    return choices.get(0);
	
	ArrayList<Direction> filteredChoices = new ArrayList<Direction>();
	for (Direction dir : choices)
	    if (!_last.isOpposite(dir))
		filteredChoices.add(dir);

	Random rand = new Random();
	return filteredChoices.get(rand.nextInt(filteredChoices.size()));
    }

    private void markTile(Vector2D v) {
	if (!_markedOnce.contains(v) && !_markedTwice.contains(v)) {
	    _markedOnce.add(v);
	} else if (_markedOnce.contains(v)) {
	    _markedOnce.remove(v);
	    _markedTwice.add(v);
	} else {
	    return;
	}
    }

    private int getTileMark(Vector2D v) {
	if (_markedOnce.contains(v))
	    return TILE_MARKED_ONCE;
	else if (_markedTwice.contains(v))
	    return TILE_MARKED_TWICE;
	else
	    return TILE_NEVER_MARKED;
    }

    private boolean isIntersection(Direction choices[]) {
	if (choices.length >= 3)
	    return true;

	return false;
    }

    private ArrayList<Direction> sortNeverMarkedTiles(Direction[] choices) {
	ArrayList<Direction> neverMarked = new ArrayList<Direction>();
	for (Direction dir : choices) {
	    Vector2D v = getPosition().addDirectionTo(dir);
	    if (!_markedOnce.contains(v) && !_markedTwice.contains(v))
		neverMarked.add(dir);
	}

	return neverMarked;

    }

    private ArrayList<Direction> sortMarkedOnceTiles(Direction[] choices) {
	ArrayList<Direction> markedOnce = new ArrayList<Direction>();
	for (Direction dir : choices) {
	    Vector2D v = getPosition().addDirectionTo(dir);
	    if (_markedOnce.contains(v) && !_markedTwice.contains(v))
		markedOnce.add(dir);
	}

	return markedOnce;
    }

    private ArrayList<Direction> sortMarkedTwiceTiles(Direction[] choices) {
	ArrayList<Direction> markedTwice = new ArrayList<Direction>();
	for (Direction dir : choices) {
	    Vector2D v = getPosition().addDirectionTo(dir);
	    if (!_markedOnce.contains(v) && _markedTwice.contains(v))
		markedTwice.add(dir);
	}

	return markedTwice;
    }
}
