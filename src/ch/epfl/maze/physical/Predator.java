package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Predator that kills a prey when they meet with each other in the labyrinth.
 * 
 */

abstract public class Predator extends Animal {

    /* constants relative to the Pac-Man game */
    public static final int SCATTER_DURATION = 14;
    public static final int CHASE_DURATION = 40;
    public static final int UNDEFINED_MODE = 0;
    public static final int SCATTER_MODE = 1;
    public static final int CHASE_MODE = 2;

    private Direction _last = Direction.NONE;
    private int _mode = UNDEFINED_MODE;
    private int _modeCount = 0;

    /**
     * Constructs a predator with a specified position.
     * 
     * @param position
     *            Position of the predator in the labyrinth
     */

    public Predator(Vector2D position) {
        super(position);
    }

    /**
     * Constructs a predator with specified position, last move and mode
     * 
     * @param position
     *            Position of the predator in the labyrith
     * @param last
     *            Last move made by the predator, defaults to INVALID_POS
     * @param mode
     *            Current mode of predator, defaults to UNDEFINED_MODE
     * @param modeCount
     *            Number of steps since last mode swap, defaults to 0
     */

    public Predator(Vector2D position, Direction last, int mode, int modeCount) {
        super(position);
        setLast(last);
        setMode(mode);
        setModeCount(modeCount);
    }

    @Override
    public void resetAnimal() {
        super.resetAnimal();
        setModeCount(0);
        setMode(UNDEFINED_MODE);
        setLast(Direction.NONE);
    }

    /**
     * @return Last move made by the predator
     */

    public final Direction getLast() {
        return _last;
    }

    /**
     * Sets the new value for the last move
     * 
     * @param last
     *            The last move made by the predators
     */

    public final void setLast(Direction last) {
        _last = last;
    }

    /**
     * @return Last move made by the predator
     */

    public final int getMode() {
        return _mode;
    }

    /**
     * Sets the new value for the mode
     * 
     * @param mode
     *            Last move made by the predator
     */

    public final void setMode(int mode) {
        if ((mode == SCATTER_MODE) || (mode == CHASE_MODE))
            _mode = mode;
    }

    /**
     * @return the number of steps done since last mode change
     */

    public final int getModeCount() {
        return _modeCount;
    }

    /**
     * Sets the new value for the mode count
     * 
     * @param count
     *            Number of steps since last mode change, must be greater than 0
     */

    public final void setModeCount(int count) {
        if (count >= 0)
            _modeCount = count;
    }

    /**
     * Increments the mode count by one
     */

    public final void incrementModeCount() {
        setModeCount(getModeCount() + 1);
    }

    /**
     * Computes the mode in which should be the predator, and increments the
     * countMode if needed. This method is the same for all the ghosts
     * 
     * @return The current mode of the predator
     */

    public final int computeMode() {
        if (getMode() == SCATTER_MODE) {
            if (getModeCount() >= SCATTER_DURATION) {
                setMode(CHASE_MODE);
                setModeCount(0);
            }

            incrementModeCount();
            return getMode();
        } else if (getMode() == CHASE_MODE) {
            if (getModeCount() >= CHASE_DURATION) {
                setMode(SCATTER_MODE);
                setModeCount(0);
            }

            incrementModeCount();
            return getMode();
        }

        setMode(CHASE_MODE);
        setModeCount(1);
        return getMode();
    };

    /**
     * Computes the Euclidian distance between to vectors
     * 
     * @param a
     *            Vector2D start
     * @param b
     *            Vector2D end
     * @return the Euclidian distance
     */

    public final double euclidianDistance(Vector2D a, Vector2D b) {
        Vector2D diff = a.sub(b);
        return diff.dist();
    }

    /**
     * Moves according to a <i>random walk</i>, used while not hunting in a
     * {@code MazeSimulation}.
     * 
     */

    @Override
    public final Direction move(Direction[] choices) {
        if (choices.length == 0) {
            setLast(Direction.NONE);
            return Direction.NONE;
        } else if (choices.length == 1) {
            setLast(choices[0]);
            return choices[0];
        }

        List<Direction> filteredChoices = new ArrayList<Direction>();
        for (Direction dir : choices)
            if (!dir.isOpposite(getLast()))
                filteredChoices.add(dir);

        Random rand = new Random();
        Direction choice = filteredChoices.get(rand.nextInt(filteredChoices
                .size()));

        setLast(choice);
        return choice;
    }

    /**
     * Computes the best direction to take to go to the target
     * 
     * @param choices
     *            Available free tiles
     * @param target
     *            Vector2D to the target
     * @return Direction to the target
     */

    public final Direction moveToTarget(Direction[] choices, Vector2D target) {
        if ((choices.length == 0) || (choices.length == 1))
            return move(choices);

        Direction bestDirection = Direction.NONE;
        double bestValue = Double.POSITIVE_INFINITY;
        for (Direction dir : choices) {
            Vector2D newPos = getPosition().addDirectionTo(dir);
            double distance = euclidianDistance(newPos, target);
            if ((distance < bestValue) && (!dir.isOpposite(getLast()))) {
                bestValue = distance;
                bestDirection = dir;
            }
        }

        setLast(bestDirection);
        return bestDirection;
    }

    /**
     * Retrieves the next direction of the animal, by selecting one choice among
     * the ones available from its position.
     * <p>
     * In this variation, the animal knows the world entirely. It can therefore
     * use the position of other animals in the daedalus to hunt more
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
