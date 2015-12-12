package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;

/**
 * Daedalus in which predators hunt preys. Once a prey has been caught by a
 * predator, it will be removed from the daedalus.
 * 
 */

public final class Daedalus extends World {

    List<Prey> _preys = new ArrayList<Prey>();
    List<Prey> _deadPreys = new ArrayList<Prey>();
    List<Predator> _predators = new ArrayList<Predator>();
    List<Predator> _predatorsOut = new ArrayList<Predator>();

    /**
     * Constructs a Daedalus with a labyrinth structure
     * 
     * @param labyrinth
     *            Structure of the labyrinth, an NxM array of tiles
     */

    public Daedalus(int[][] labyrinth) {
        super(labyrinth);
    }

    @Override
    public boolean isSolved() {
        if (_preys.isEmpty())
            return true;

        return false;
    }

    /**
     * Adds a predator to the daedalus.
     * 
     * @param p
     *            The predator to add
     */

    public void addPredator(Predator p) {
        p.setStartPosition(p.getPosition());
        p.resetAnimal();
        _predators.add(p);
    }

    /**
     * Adds a prey to the daedalus.
     * 
     * @param p
     *            The prey to add
     */

    public void addPrey(Prey p) {
        p.setStartPosition(p.getPosition());
        p.resetAnimal();
        _preys.add(p);
    }

    /**
     * Removes a predator from the daedalus.
     * 
     * @param p
     *            The predator to remove
     */

    public void removePredator(Predator p) {
        int index = -1;
        for (int i = 0; i < _predators.size(); i++)
            if (_predators.get(i) == p)
                index = i;

        if (index >= 0) {
            _predatorsOut.add((Predator) p.copy());
            _predators.remove(index);
        }
    }

    /**
     * Removes a prey from the daedalus.
     * 
     * @param p
     *            The prey to remove
     */

    public void removePrey(Prey p) {
        int index = -1;
        for (int i = 0; i < _preys.size(); i++)
            if (_preys.get(i) == p)
                index = i;

        if (index >= 0) {
            _deadPreys.add((Prey) p.copy());
            _preys.remove(index);
        }
    }

    @Override
    public List<Animal> getAnimals() {
        List<Animal> animals = new ArrayList<Animal>();
        for (Prey prey : _preys)
            animals.add(prey);
        for (Predator pred : _predators)
            animals.add(pred);

        return animals;
    }

    /**
     * Returns a copy of the list of all current predators in the daedalus.
     * 
     * @return A list of all predators in the daedalus
     */

    public List<Predator> getPredators() {
        List<Predator> copy = new ArrayList<Predator>();
        for (Predator pred : _predators)
            copy.add(pred);

        return copy;
    }

    /**
     * Returns a copy of the list of all current preys in the daedalus.
     * 
     * @return A list of all preys in the daedalus
     */

    public List<Prey> getPreys() {
        List<Prey> copy = new ArrayList<Prey>();
        for (Prey prey : _preys)
            copy.add(prey);

        return copy;
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
        for (Predator pred : _predators)
            if (pred == p)
                return true;

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
        for (Prey prey : _preys)
            if (prey == p)
                return true;

        return false;
    }

    @Override
    public void reset() {
        for (Prey p : _preys)
            _deadPreys.add((Prey) p.copy());
        _preys.clear();

        for (Prey p : _deadPreys)
            addPrey(p);
        _deadPreys.clear();

        for (Predator p : _predators)
            _predatorsOut.add((Predator) p.copy());
        _predators.clear();

        for (Predator p : _predatorsOut)
            addPredator(p);
        _predatorsOut.clear();
    }
}
