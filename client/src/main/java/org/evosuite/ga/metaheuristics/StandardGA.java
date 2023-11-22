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

    public StandardGA(ChromosomeFactory<T> factory) {
        super(factory);
    }

    @Override
    protected void evolve() {
        double weightBest = 0.9999;
        double weightWorst = 0.0001;
        double fitness1=0.0;
        double fitness2=0.0;

        List<T> newGeneration = new ArrayList<>(elitism());

        while (!isNextPopulationFull(newGeneration)) {
            T parent1 = selectionFunction.select(population);
            T parent2 = selectionFunction.select(population);
            T offspring1 = parent1.clone();
            T offspring2 = parent2.clone();

            try {
                if (Randomness.nextDouble() <= Properties.CROSSOVER_RATE) {
                    crossoverFunction.crossOver(offspring1, offspring2);
                }

                notifyMutation(offspring1);
                offspring1.mutate();
                notifyMutation(offspring2);
                offspring2.mutate();

                if (offspring1.isChanged()) {
                    offspring1.updateAge(currentIteration);
                }
                if (offspring2.isChanged()) {
                    offspring2.updateAge(currentIteration);
                }

                for (FitnessFunction<T> fitnessFunction : fitnessFunctions) {
                    fitness1 = fitnessFunction.getFitness(offspring1) + weightWorst * offspring1.calculateUtility(0, 1);
                    fitness2 = fitnessFunction.getFitness(offspring2) + weightWorst * offspring2.calculateUtility(0, 1);
                    notifyEvaluation(offspring1);
                    notifyEvaluation(offspring2);
                }

                double thresholdFitness = (weightBest * getBestIndividual().getFitness() + weightWorst * population.get(0).calculateUtility(0, 1));
                //System.out.println("thresholdFitness: " + thresholdFitness);
                //System.out.println("fitness1s: " + fitness1);
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
                logger.info("CrossOver/Mutation failed.");
                continue;
            }
        }
        population = newGeneration;
        updateFitnessFunctionsAndValues();
        currentIteration++;
    }

    @Override
    public void initializePopulation() {
        notifySearchStarted();
        currentIteration = 0;

        generateInitialPopulation(Properties.POPULATION);
        calculateFitnessAndSortPopulation();
        this.notifyIteration();
    }

    @Override
    public void generateSolution() {
        if (Properties.ENABLE_SECONDARY_OBJECTIVE_AFTER > 0 || Properties.ENABLE_SECONDARY_OBJECTIVE_STARVATION) {
            disableFirstSecondaryCriterion();
        }
        if (population.isEmpty()) {
            initializePopulation();
        }

        logger.debug("Starting evolution");
        int starvationCounter = 0;
        double bestFitness = Double.MAX_VALUE;
        double lastBestFitness = Double.MAX_VALUE;
        if (getFitnessFunction().isMaximizationFunction()) {
            bestFitness = 0.0;
            lastBestFitness = 0.0;
        }

        while (!isFinished()) {
            logger.debug("Current population: " + getAge() + "/" + Properties.SEARCH_BUDGET);
            logger.info("Best fitness: " + getBestIndividual().getFitness());

            evolve();
            calculateFitnessAndSortPopulation();

            applyLocalSearch();

            double newFitness = getBestIndividual().getFitness();

            if (getFitnessFunction().isMaximizationFunction()) {
                assert newFitness >= bestFitness : "best fitness was: " + bestFitness + ", now best fitness is " + newFitness;
            } else {
                assert newFitness <= bestFitness : "best fitness was: " + bestFitness + ", now best fitness is " + newFitness;
            }
            bestFitness = newFitness;

            if (Double.compare(bestFitness, lastBestFitness) == 0) {
                starvationCounter++;
            } else {
                logger.info("reset starvationCounter after " + starvationCounter + " iterations");
                starvationCounter = 0;
                lastBestFitness = bestFitness;
            }

            updateSecondaryCriterion(starvationCounter);

            this.notifyIteration();
        }

        updateBestIndividualFromArchive();
        notifySearchFinished();
    }
}
