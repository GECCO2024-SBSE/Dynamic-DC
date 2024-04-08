package org.evosuite.ga.metaheuristics;

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.ga.FitnessFunction;
import org.evosuite.utils.Randomness;

import java.util.ArrayList;
import java.util.List;

public class StandardGA<T extends Chromosome<T>> extends GeneticAlgorithm<T> {

    private static final long serialVersionUID = 5043503777821916152L;

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(StandardGA.class);

    // Constructor for StandardGA
    public StandardGA(ChromosomeFactory<T> factory) {
        super(factory);
    }

    // The main evolution process
    @Override
    protected void evolve() {
        Properties.POPULATION=200;
        // Constants for controlling the selection of individuals
        double weightBest = 0.9999;
        double weightWorst = 0.0001;
        double fitness1 = 0.0;
        double fitness2 = 0.0;

        // Create a new generation with elites from the previous generation
        List<T> newGeneration = new ArrayList<>(elitism());

        // Continue evolving until the next population is full
        while (!isNextPopulationFull(newGeneration)) {
            // Select two parents using the selection function
            T parent1 = selectionFunction.select(population);
            T parent2 = selectionFunction.select(population);

            // Clone the parents to create two offspring
            T offspring1 = parent1.clone();
            T offspring2 = parent2.clone();

            try {
                // Apply crossover with a certain probability
                if (Randomness.nextDouble() <= Properties.CROSSOVER_RATE) {
                    crossoverFunction.crossOver(offspring1, offspring2);
                }

                // Mutate the offspring
                notifyMutation(offspring1);
                offspring1.mutate();
                notifyMutation(offspring2);
                offspring2.mutate();

                // Update age and evaluate fitness
                if (offspring1.isChanged()) {
                    offspring1.updateAge(currentIteration);
                }
                if (offspring2.isChanged()) {
                    offspring2.updateAge(currentIteration);
                }

                // Evaluate fitness using multiple fitness functions
                for (FitnessFunction<T> fitnessFunction : fitnessFunctions) {
                    fitness1 = fitnessFunction.getFitness(offspring1) + weightWorst * offspring1.calculateUtility(0, 1);
                    fitness2 = fitnessFunction.getFitness(offspring2) + weightWorst * offspring2.calculateUtility(0, 1);
                    notifyEvaluation(offspring1);
                    notifyEvaluation(offspring2);
                }

                // Calculate a threshold fitness for selection
                double thresholdFitness = (weightBest * getBestIndividual().getFitness() + weightWorst * population.get(0).calculateUtility(0, 1));

                // Select offspring based on fitness and the threshold
                if (fitness1 < thresholdFitness) {
                    newGeneration.add(offspring1);
                } else {
                    notifyMutation(offspring1);
                    offspring1.mutate();
                }

                if (fitness2 < thresholdFitness) {
                    newGeneration.add(offspring2);
                } else {
                    notifyMutation(offspring2);
                    offspring2.mutate();
                }

            } catch (ConstructionFailedException e) {
                // Log if crossover or mutation fails
                logger.info("CrossOver/Mutation failed.");
                continue;
            }
        }

        // Print the size of the first individual in the population
        System.out.println("Test case: " + population.get(0).size());

        // Update the population with the new generation
        population = newGeneration;
        updateFitnessFunctionsAndValues();
        currentIteration++;
    }

    // Initialize the population and start the search
    @Override
    public void initializePopulation() {
        notifySearchStarted();
        currentIteration = 0;

        // Generate an initial population
        generateInitialPopulation(Properties.POPULATION);
        calculateFitnessAndSortPopulation();
        this.notifyIteration();
    }

    // Generate a solution by evolving the population
    @Override
    public void generateSolution() {
        // Disable the first secondary criterion if needed
        if (Properties.ENABLE_SECONDARY_OBJECTIVE_AFTER > 0 || Properties.ENABLE_SECONDARY_OBJECTIVE_STARVATION) {
            disableFirstSecondaryCriterion();
        }

        // Initialize the population if it's empty
        if (population.isEmpty()) {
            initializePopulation();
        }

        logger.debug("Starting evolution");
        int starvationCounter = 0;
        double bestFitness = Double.MAX_VALUE;
        double lastBestFitness = Double.MAX_VALUE;

        // Adjust initial values for maximization functions
        if (getFitnessFunction().isMaximizationFunction()) {
            bestFitness = 0.0;
            lastBestFitness = 0.0;
        }

        // Continue evolving until the termination condition is met
        while (!isFinished()) {
            logger.debug("Current population: " + getAge() + "/" + Properties.SEARCH_BUDGET);
            logger.info("Best fitness: " + getBestIndividual().getFitness());

            // Evolve the population
            evolve();
            calculateFitnessAndSortPopulation();

            // Apply local search if needed
            applyLocalSearch();

            // Update best fitness and check for stagnation
            double newFitness = getBestIndividual().getFitness();
            if (getFitnessFunction().isMaximizationFunction()) {
                assert newFitness >= bestFitness : "best fitness was: " + bestFitness + ", now best fitness is " + newFitness;
            } else {
                assert newFitness <= bestFitness : "best fitness was: " + bestFitness + ", now best fitness is " + newFitness;
            }
            bestFitness = newFitness;

            // Check for stagnation and update counters
            if (Double.compare(bestFitness, lastBestFitness) == 0) {
                starvationCounter++;
            } else {
                logger.info("reset starvationCounter after " + starvationCounter + " iterations");
                starvationCounter = 0;
                lastBestFitness = bestFitness;
            }

            // Update secondary criterion based on stagnation
            updateSecondaryCriterion(starvationCounter);

            this.notifyIteration();
        }

        // Update the best individual from the archive
        updateBestIndividualFromArchive();
        notifySearchFinished();
    }
}
