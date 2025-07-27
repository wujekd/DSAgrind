package genetic;

import java.util.List;
import java.util.ArrayList;

public class GAResultsForSettings {

    public final int populationSize;
    public final int chromosomeLength;
    public final double mutationRate;
    public final int maxGenerations;
    
    private final List<GAResult> results;
    
    private final double meanFitness;
    private final double stdDevFitness;
    private final double successRate;
    private final double meanGenerations;
    private final double bestFitness;
    
    public GAResultsForSettings(int populationSize, int chromosomeLength, 
                               double mutationRate, int maxGenerations, 
                               List<GAResult> results) {
        this.populationSize = populationSize;
        this.chromosomeLength = chromosomeLength;
        this.mutationRate = mutationRate;
        this.maxGenerations = maxGenerations;
        this.results = new ArrayList<>(results);
        
        this.meanFitness = calculateMeanFitness();
        this.stdDevFitness = calculateStdDevFitness();
        this.successRate = calculateSuccessRate();
        this.meanGenerations = calculateMeanGenerations();
        this.bestFitness = findBestFitness();
    }
    
    private double calculateMeanFitness() {
        if (results.isEmpty()) return 0.0;
        
        double sum = 0.0;
        for (GAResult result : results) {
            sum += result.bestFitness;
        }
        return sum / results.size();
    }
    
    private double calculateStdDevFitness() {
        if (results.size() <= 1) return 0.0;
        
        double mean = meanFitness;
        double sumSquaredDiff = 0.0;
        
        for (GAResult result : results) {
            double diff = result.bestFitness - mean;
            sumSquaredDiff += diff * diff;
        }
        
        return Math.sqrt(sumSquaredDiff / (results.size() - 1));
    }
    
    private double calculateSuccessRate() {
        if (results.isEmpty()) return 0.0;
        
        int successes = 0;
        for (GAResult result : results) {
            if (result.solutionFound) {
                successes++;
            }
        }
        return (double) successes / results.size();
    }
    
    private double calculateMeanGenerations() {
        if (results.isEmpty()) return 0.0;
        
        double sum = 0.0;
        for (GAResult result : results) {
            sum += result.generations;
        }
        return sum / results.size();
    }
    
    private double findBestFitness() {
        if (results.isEmpty()) return 0.0;
        
        double best = results.get(0).bestFitness;
        for (GAResult result : results) {
            if (result.bestFitness > best) {
                best = result.bestFitness;
            }
        }
        return best;
    }
    
    public List<GAResult> getResults() { return new ArrayList<>(results); }
    public int getNumberOfTrials() { return results.size(); }
    public double getMeanFitness() { return meanFitness; }
    public double getStdDevFitness() { return stdDevFitness; }
    public double getSuccessRate() { return successRate; }
    public double getMeanGenerations() { return meanGenerations; }
    public double getBestFitness() { return bestFitness; }
    
    @Override
    public String toString() {
        return String.format("Settings[pop=%d, len=%d, mut=%.3f, maxGen=%d] -> " +
                           "meanFitness=%.2f±%.2f, successRate=%.1f%%, meanGen=%.1f, best=%.0f (%d trials)",
                           populationSize, chromosomeLength, mutationRate, maxGenerations,
                           meanFitness, stdDevFitness, successRate * 100, meanGenerations, bestFitness, results.size());
    }
} 