package ch.epfl.maze.physical.pacman;

import java.util.List;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Pink ghost from the Pac-Man game, targets 4 squares in front of its target.
 * 
 */

public class Pinky extends Predator {

    private Prey _target = null;
    private Vector2D _previousPos = INVALID_POS;

    /**
     * Constructs a Pinky with a starting position.
     * 
     * @param position
     *            Starting position of Pinky in the labyrinth
     */

    public Pinky(Vector2D position) {
        super(position);
    }

    /**
     * Constructs a Blinky with specified position, last move and mode
     * 
     * @param position
     *            Position of Blinky in the labyrith
     * @param last
     *            Last move made by Blinky, defaults to INVALID_POS
     * @param mode
     *            Current mode Blinky, defaults to UNDEFINED_MODE
     * @param modeCount
     *            Number of steps since last mode swap, defaults to 0
     */

    public Pinky(Vector2D position, Vector2D previousPos, Prey target,
            Direction last, int mode, int modeCount) {
        super(position);
        setLast(last);
        setMode(mode);
        setTarget(target);
        setModeCount(modeCount);
        setPreviousTargetPos(previousPos);
    }

    @Override
    public Direction move(Direction[] choices, Daedalus daedalus) {
        if (daedalus.getPreys().isEmpty())
            return move(choices);

        int mode = computeMode();
        if (mode == CHASE_MODE) {
            return moveToTarget(choices, forecastPosition(daedalus));
        } else
            return moveToTarget(choices, getStartPosition());
    }

    @Override
    public Animal copy() {
        Pinky p = new Pinky(getPosition(), getPreviousTargetPos(), getTarget(),
                getLast(), getMode(), getModeCount());
        p.setStartPosition(getStartPosition());

        return p;
    }

    @Override
    public void resetAnimal() {
        super.resetAnimal();
    }

    public Vector2D getPreviousTargetPos() {
        return _previousPos;
    }

    public void setPreviousTargetPos(Vector2D pos) {
        _previousPos = pos;
    }

    public Prey getTarget() {
        return _target;
    }

    public void setTarget(Prey p) {
        _target = p;
    }

    private Vector2D forecastPosition(Daedalus d) {
        Direction orientation = computeTargetOrientation(d.getPreys());
        Vector2D forecast = getTarget().getPosition();
        for (int i = 0; i < 4; i++)
            forecast = forecast.addDirectionTo(orientation);

        return forecast;
    }

    private Direction computeTargetOrientation(List<Prey> preys) {
        if (preys.get(0) != getTarget()) {
            setTarget(preys.get(0));
            setPreviousTargetPos(getTarget().getPosition());
            return Direction.UP;
        } else if (getPreviousTargetPos() == INVALID_POS) {
            setPreviousTargetPos(getTarget().getPosition());
            return Direction.UP;
        }

        Vector2D diff = getTarget().getPosition().sub(getPreviousTargetPos());
        setPreviousTargetPos(getTarget().getPosition());
        return diff.toDirection();
    }
}
