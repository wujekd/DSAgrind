package genetic.testing;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class GATestResults {
    public final int steps;
    public final int populationMin, populationMax;
    public final double mutationRateMin, mutationRateMax;
    public final int repeat;
    
    private final List<GAResultsForSettings> results;
    
    private final double bestOverallFitness;
    private final double averageSuccessRate;
    private final double averageGenerations;
    private final GAResultsForSettings bestResult;
    
    public GATestResults(int steps, int populationMin, int populationMax,
                        double mutationRateMin, double mutationRateMax, int repeat,
                        List<GAResultsForSettings> results) {
        this.steps = steps;
        this.populationMin = populationMin;
        this.populationMax = populationMax;
        this.mutationRateMin = mutationRateMin;
        this.mutationRateMax = mutationRateMax;
        this.repeat = repeat;
        this.results = new ArrayList<>(results);
        
        this.bestOverallFitness = calculateBestOverallFitness();
        this.averageSuccessRate = calculateAverageSuccessRate();
        this.averageGenerations = calculateAverageGenerations();
        this.bestResult = findBestResult();
    }
    
    private double calculateBestOverallFitness() {
        if (results.isEmpty()) return 0.0;
        
        double best = results.get(0).getBestFitness();
        for (GAResultsForSettings result : results) {
            if (result.getBestFitness() > best) {
                best = result.getBestFitness();
            }
        }
        return best;
    }
    
    private double calculateAverageSuccessRate() {
        if (results.isEmpty()) return 0.0;
        
        double sum = 0.0;
        for (GAResultsForSettings result : results) {
            sum += result.getSuccessRate();
        }
        return sum / results.size();
    }
    
    private double calculateAverageGenerations() {
        if (results.isEmpty()) return 0.0;
        
        double sum = 0.0;
        for (GAResultsForSettings result : results) {
            sum += result.getMeanGenerations();
        }
        return sum / results.size();
    }
    
    private GAResultsForSettings findBestResult() {
        if (results.isEmpty()) return null;
        
        GAResultsForSettings best = results.get(0);
        for (GAResultsForSettings result : results) {
            if (result.getBestFitness() > best.getBestFitness()) {
                best = result;
            }
        }
        return best;
    }
    
    public List<GAResultsForSettings> getResults() { return new ArrayList<>(results); }
    public int getNumberOfResults() { return results.size(); }
    public double getBestOverallFitness() { return bestOverallFitness; }
    public double getAverageSuccessRate() { return averageSuccessRate; }
    public double getAverageGenerations() { return averageGenerations; }
    public GAResultsForSettings getBestResult() { return bestResult; }
    
    public Map<String, Double> getParameterSensitivity() {
        Map<String, Double> sensitivity = new HashMap<>();
        
        double popSensitivity = calculatePopulationSensitivity();
        sensitivity.put("populationSize", popSensitivity);
        
        double mutSensitivity = calculateMutationRateSensitivity();
        sensitivity.put("mutationRate", mutSensitivity);
        
        return sensitivity;
    }
    
    private double calculatePopulationSensitivity() {
        double sum = 0.0;
        int count = 0;
        
        for (GAResultsForSettings result : results) {
            sum += result.populationSize * result.getMeanFitness();
            count++;
        }
        
        return count > 0 ? sum / count : 0.0;
    }
    
    private double calculateMutationRateSensitivity() {
        double sum = 0.0;
        int count = 0;
        
        for (GAResultsForSettings result : results) {
            sum += result.mutationRate * result.getMeanFitness();
            count++;
        }
        
        return count > 0 ? sum / count : 0.0;
    }
    
    public void printSummary() {
        System.out.println("=== GA Test Results Summary ===");
        System.out.printf("  Total individual tests performed: %d\n", results.size() * repeat);
        System.out.println("Test Parameters:");
        System.out.printf("  Population: %d to %d (%d steps)\n", populationMin, populationMax, steps);
        System.out.printf("  Mutation Rate: %.3f to %.3f (%d steps)\n", mutationRateMin, mutationRateMax, steps);
        System.out.printf("  Trials per combination: %d\n", repeat);
        System.out.printf("  Total combinations tested: %d\n", results.size());
        System.out.printf("  Total individual tests performed: %d\n", results.size() * repeat);
        System.out.println();
        
        System.out.println("Overall Statistics:");
        System.out.printf("  Best overall fitness: %.2f\n", bestOverallFitness);
        System.out.printf("  Average success rate: %.1f%%\n", averageSuccessRate * 100);
        System.out.printf("  Average generations: %.1f\n", averageGenerations);
        System.out.println();
        
        if (bestResult != null) {
            System.out.println("Best performing settings:");
            System.out.println("  " + bestResult);
        }
    }
    
    @Override
    public String toString() {
        return String.format("GATestResults[%d combinations, bestFitness=%.2f, avgSuccess=%.1f%%, avgGen=%.1f]",
                           results.size(), bestOverallFitness, averageSuccessRate * 100, averageGenerations);
    }
} 