package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;

/**
 * Daedalus in which predators hunt preys. Once a prey has been caught by a
 * predator, it will be removed from the daedalus.
 * 
 */

public final class Daedalus extends World {

	/**
	 * Constructs a Daedalus with a labyrinth structure
	 * 
	 * @param labyrinth
	 *            Structure of the labyrinth, an NxM array of tiles
	 */

	public Daedalus(int[][] labyrinth) {
		super(labyrinth);
		// TODO
	}

	@Override
	public boolean isSolved() {
		// TODO
		return false;
	}

	/**
	 * Adds a predator to the daedalus.
	 * 
	 * @param p
	 *            The predator to add
	 */

	public void addPredator(Predator p) {
		// TODO
	}

	/**
	 * Adds a prey to the daedalus.
	 * 
	 * @param p
	 *            The prey to add
	 */

	public void addPrey(Prey p) {
		// TODO
	}

	/**
	 * Removes a predator from the daedalus.
	 * 
	 * @param p
	 *            The predator to remove
	 */

	public void removePredator(Predator p) {
		// TODO
	}

	/**
	 * Removes a prey from the daedalus.
	 * 
	 * @param p
	 *            The prey to remove
	 */

	public void removePrey(Prey p) {
		// TODO
	}

	@Override
	public List<Animal> getAnimals() {
		// TODO
		return null;
	}

	/**
	 * Returns a copy of the list of all current predators in the daedalus.
	 * 
	 * @return A list of all predators in the daedalus
	 */

	public List<Predator> getPredators() {
		// TODO
		return new ArrayList<Predator>();
	}

	/**
	 * Returns a copy of the list of all current preys in the daedalus.
	 * 
	 * @return A list of all preys in the daedalus
	 */

	public List<Prey> getPreys() {
		// TODO
		return new ArrayList<Prey>();
	}

	/**
	 * Determines if the daedalus contains a predator.
	 * 
	 * @param p
	 *            The predator in question
	 * @return <b>true</b> if the predator belongs to the world, <b>false</b>
	 *         otherwise.
	 */

	public boolean hasPredator(Predator p) {
		// TODO
		return false;
	}

	/**
	 * Determines if the daedalus contains a prey.
	 * 
	 * @param p
	 *            The prey in question
	 * @return <b>true</b> if the prey belongs to the world, <b>false</b>
	 *         otherwise.
	 */

	public boolean hasPrey(Prey p) {
		// TODO
		return false;
	}

	@Override
	public void reset() {
		// TODO
	}
}
