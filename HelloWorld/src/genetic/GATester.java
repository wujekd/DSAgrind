package genetic;

import java.util.List;
import java.util.ArrayList;

public class GATester {
    
    public GATester() {}
    
    public List<GAResultsForSettings> test(
        GeneticAlgorithm ga,
        int steps,
        int populationMin, int populationMax,
        double mutationRateMin, double mutationRateMax,
        int repeat
    ) {

        List<Integer> populationSizes = generateLinearSteps(populationMin, populationMax, steps);
        List<Double> mutationRates = generateLinearSteps(mutationRateMin, mutationRateMax, steps);
        
        List<GAResultsForSettings> results = new ArrayList<>();
        
        for (int popSize : populationSizes) {
            for (double mutRate : mutationRates) {
                ga.setPopulationSize(popSize);
                ga.setMutationRate(mutRate);
                
                List<GAResult> trials = new ArrayList<>();
                for (int trial = 0; trial < repeat; trial++) {
                    trials.add(ga.evolve());
                }
                
                GAResultsForSettings result = new GAResultsForSettings(
                    popSize, ga.getChromosomeLength(), mutRate, ga.getMaxGenerations(), trials
                );
                results.add(result);
            }
        }
        return results;
    }
    
    private List<Integer> generateLinearSteps(int min, int max, int steps) {
        List<Integer> values = new ArrayList<>();
        double step = (double)(max - min) / (steps - 1);
        
        for (int i = 0; i < steps; i++) {
            values.add((int) Math.round(min + i * step));
        }
        
        return values;
    }
    
    private List<Double> generateLinearSteps(double min, double max, int steps) {
        List<Double> values = new ArrayList<>();
        double step = (max - min) / (steps - 1);
        
        for (int i = 0; i < steps; i++) {
            values.add(min + i * step);
        }
        
        return values;
    }
} 