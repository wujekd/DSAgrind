package genetic;

import genetic.testing.GAResult;

public class OneMaxTestable extends GeneticAlgorithm {
    public OneMaxTestable(int populationSize, int chromosomeLength, double mutationRate, int maxGenerations) {
        setPopulationSize(populationSize);
        setChromosomeLength(chromosomeLength);
        setMutationRate(mutationRate);
        setMaxGenerations(maxGenerations);
    }

    @Override
    public int calculateFitness(int[] chromosome) {
        int sum = 0;
        for (int gene : chromosome) {
            sum += gene;
        }
        return sum;
    }
    
    @Override
    public boolean isSolutionFound(int[] fitness) {
        for (int f : fitness) {
            if (f == chromosomeLength) {
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        OneMaxTestable oneMax = new OneMaxTestable(50, 20, 0.01, 10);
        
        genetic.testing.GATester tester = new genetic.testing.GATester();
        genetic.testing.GATestResults results = tester.test(oneMax, 20, 10, 100, 0.01, 0.07, 10);
        
        results.printSummary();
    }
} 