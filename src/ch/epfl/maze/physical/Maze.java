package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;

/**
 * Maze in which an animal starts from a starting point and must find the exit.
 * Every animal added will have its position set to the starting point. The
 * animal is removed from the maze when it finds the exit.
 * 
 */

public final class Maze extends World {

    private List<Animal> _animalsIn = new ArrayList<Animal>();
    private List<Animal> _animalsOut = new ArrayList<Animal>();

    /**
     * Constructs a Maze with a labyrinth structure.
     * 
     * @param labyrinth
     *            Structure of the labyrinth, an NxM array of tiles
     */

    public Maze(int[][] labyrinth) {
	super(labyrinth);
    }

    @Override
    public boolean isSolved() {
	return _animalsIn.isEmpty();
    }

    @Override
    public List<Animal> getAnimals() {
	return _animalsIn;
    }

    /**
     * Determines if the maze contains an animal.
     * 
     * @param a
     *            The animal in question
     * @return <b>true</b> if the animal belongs to the world, <b>false</b>
     *         otherwise.
     */

    public boolean hasAnimal(Animal a) {
	for (Animal an : _animalsIn)
	    if (an == a)
		return true;

	return false;
    }

    /**
     * Adds an animal to the maze.
     * 
     * @param a
     *            The animal to add
     */

    public void addAnimal(Animal a) {
	a.reset(getStart());
	_animalsIn.add(a);
    }

    /**
     * Removes an animal from the maze.
     * 
     * @param a
     *            The animal to remove
     */

    public void removeAnimal(Animal a) {
	int index = -1;
	_animalsOut.add(a.copy());
	for (int i = 0; i < _animalsIn.size(); i++)
	    if (_animalsIn.get(i) == a)
		index = i;

	if (index >= 0)
	    _animalsIn.remove(index);
    }

    @Override
    public void reset() {
	for (Animal a : _animalsIn)
	    _animalsOut.add(a.copy());

	_animalsIn.clear();
	for (Animal a : _animalsOut)
	    addAnimal(a);

	_animalsOut.clear();
    }
}
