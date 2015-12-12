package ch.epfl.maze.physical.zoo;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.WallFollower;

import java.util.ArrayList;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Space Invader A.I. that implements an algorithm of your choice.
 * <p>
 * Note that this class is considered as a <i>bonus</i>, meaning that you do not
 * have to implement it (see statement: section 6, Extensions libres).
 * <p>
 * If you consider implementing it, you will have bonus points on your grade if
 * it can exit a simply-connected maze, plus additional points if it is more
 * efficient than the animals you had to implement.
 * <p>
 * The way we measure efficiency is made by the test case {@code Competition}.
 * 
 * @see ch.epfl.maze.tests.Competition Competition
 * 
 */

public class SpaceInvader extends WallFollower {

    private boolean _reseted = false;
    private boolean _unknown = false;
    private Direction _last = Direction.NONE;
    private Vector2D _lastPosition = INVALID_POS;
    private ArrayList<Vector2D> _markedOnce = new ArrayList<Vector2D>();
    private ArrayList<Vector2D> _markedTwice = new ArrayList<Vector2D>();

    /**
     * Constructs a space invader with a starting position.
     * 
     * @param position
     *            Starting position of the mouse in the labyrinth
     */

    public SpaceInvader(Vector2D position) {
        super(position);
        setReseted(false);
        setUnknown(false);
        setLast(Direction.NONE);
        setOrientation(Direction.UP);
        setLastPosition(INVALID_POS);
    }

    public SpaceInvader(Vector2D position, Direction orientation,
            Direction last, ArrayList<Vector2D> markedOnce,
            ArrayList<Vector2D> markedTwice, Vector2D lastPosition) {
        super(position);
        setLast(last);
        _markedOnce = markedOnce;
        _markedTwice = markedTwice;
        setOrientation(orientation);
        setLastPosition(lastPosition);
    }

    /*
     * GETTERS AND SETTERS
     */

    public boolean getReseted() {
        return _reseted;
    }

    public boolean getUnknown() {
        return _unknown;
    }

    public Vector2D getLastPosition() {
        return _lastPosition;
    }

    public Direction getLast() {
        return _last;
    }

    public void setReseted(boolean reseted) {
        _reseted = reseted;
    }

    public void setUnknown(boolean unknown) {
        _unknown = unknown;
    }

    public void setLastPosition(Vector2D lastPosition) {
        _lastPosition = lastPosition;
    }

    public void setLast(Direction last) {
        _last = last;
    }

    /**
     * Moves according to (... please complete with as many details as you can).
     */

    @Override
    public Direction move(Direction[] choices) {
        if (getPosition().equals(getLastPosition()))
            setUnknown(true);
        if (getUnknown())
            return discoveryMode(choices);
        else
            return memoryMode(choices);
    }

    @Override
    public Animal copy() {
        return new SpaceInvader(getPosition(), getOrientation(), getLast(),
                _markedOnce, _markedTwice, getLastPosition());
    }

    @Override
    public void resetAnimal() {
        setLastPosition(getPosition());
        super.resetAnimal();

        setReseted(true);
        setUnknown(false);
        setLast(Direction.NONE);
        setOrientation(Direction.UP);
    }

    private void markTile(Vector2D v) {
        if (_markedOnce.contains(v)) {
            _markedTwice.add(v);
            _markedOnce.remove(v);
        } else if (!_markedTwice.contains(v))
            _markedOnce.add(v);
    }

    private Direction discoveryMode(Direction[] choices) {
        setLast(followLeftWall((choices)));
        computeOrientation(getLast());
        markTile(getPosition());
        return getLast();
    }

    private boolean isIntersection(Direction choices[]) {
        if (choices.length >= 3)
            return true;

        return false;
    }

    private Direction memoryMode(Direction[] choices) {
        Direction next = Direction.NONE;
        if (isIntersection(choices)) {
            for (Direction dir : choices) {
                Vector2D v = getPosition().addDirectionTo(dir);
                if ((_markedOnce.contains(v)) && (!getLast().isOpposite(dir)))
                    next = dir;
            }

            if (next == Direction.NONE) {
                setLast(followLeftWall(choices));
                computeOrientation(getLast());
                return getLast();
            }

            setLast(next);
            if (getOrientation().relativeDirection(_last) == Direction.LEFT)
                setOrientation(getOrientation().rotateLeft());
            else if (getOrientation().relativeDirection(_last) == Direction.RIGHT)
                setOrientation(getOrientation().rotateRight());
            else if (getOrientation().relativeDirection(_last) == Direction.DOWN)
                setOrientation(getOrientation().reverse());

            return getLast();

        } else {
            setLast(followLeftWall(choices));
            computeOrientation(getLast());
            return getLast();
        }
    }
}