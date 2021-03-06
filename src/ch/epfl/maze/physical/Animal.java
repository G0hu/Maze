package ch.epfl.maze.physical;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Animal inside a {@code World} that can move depending on the available
 * choices it has at its position.
 * 
 */

abstract public class Animal {

    final public Vector2D INVALID_POS = new Vector2D(-1, -1);
    private Vector2D _position = INVALID_POS;
    private Vector2D _startPos = INVALID_POS;

    /**
     * Constructs an animal with a specified position.
     * 
     * @param position
     *            Position of the animal in the labyrinth
     */

    public Animal(Vector2D position) {
        _position = position;
    }

    /**
     * Retrieves the next direction of the animal, by selecting one choice among
     * the ones available from its position.
     * 
     * @param choices
     *            The choices left to the animal at its current position (see
     *            {@link ch.epfl.maze.physical.World#getChoices(Vector2D)
     *            World.getChoices(Vector2D)})
     * @return The next direction of the animal, chosen in {@code choices}
     */

    abstract public Direction move(Direction[] choices);

    /**
     * Updates the animal position with a direction.
     * <p>
     * <b>Note</b> : Do not call this function in {@code move(Direction[]
     * choices)} !
     * 
     * @param dir
     *            Direction that the animal has taken
     */

    public final void update(Direction dir) {
        _position = _position.addDirectionTo(dir);
    }

    /**
     * Sets new position for Animal.
     * <p>
     * <b>Note</b> : Do not call this function in {@code move(Direction[]
     * choices)} !
     * 
     * @param position
     */

    public final void setPosition(Vector2D position) {
        _position = position;
    }

    /**
     * Returns position vector of animal.
     * 
     * @return Current position of animal.
     */

    public final Vector2D getPosition() {
        return _position;
    }

    public final Vector2D getStartPosition() {
        return _startPos;
    }

    public final void setStartPosition(Vector2D pos) {
        if (_startPos.equals(INVALID_POS))
            _startPos = pos;
    }

    public void resetAnimal() {
        setPosition(getStartPosition());
    };

    abstract public Animal copy();
}
