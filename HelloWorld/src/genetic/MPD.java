package genetic;

import Graphs.WeightedGraph;
import genetic.testing.GAResult;

public class MPD extends GeneticAlgorithm {
    private static final int START_CITY = 0;
    private static final int OPTIMAL_DISTANCE = 198;
    
    private WeightedGraph<Integer> graph;
    private int numberOfCities;
    
    public MPD(WeightedGraph<Integer> graph, int populationSize, double mutationRate, int maxGenerations) {
        this.graph = graph;
        this.numberOfCities = graph.getSize();
        setPopulationSize(populationSize);
        setChromosomeLength(numberOfCities + 1);
        setMutationRate(mutationRate);
        setMaxGenerations(maxGenerations);
    }
    
    public static void main(String[] args) throws Exception {
        int numberOfCities = 10;
        int[][] edges = {
            {0, 1, 10}, {0, 2, 15}, {0, 3, 20}, {0, 4, 25}, {0, 5, 30}, {0, 6, 35}, {0, 7, 40}, {0, 8, 45}, {0, 9, 50},
            {1, 2, 12}, {1, 3, 18}, {1, 4, 22}, {1, 5, 28}, {1, 6, 32}, {1, 7, 38}, {1, 8, 42}, {1, 9, 48},
            {2, 3, 14}, {2, 4, 19}, {2, 5, 24}, {2, 6, 29}, {2, 7, 34}, {2, 8, 39}, {2, 9, 44},
            {3, 4, 16}, {3, 5, 21}, {3, 6, 26}, {3, 7, 31}, {3, 8, 36}, {3, 9, 41},
            {4, 5, 17}, {4, 6, 23}, {4, 7, 27}, {4, 8, 33}, {4, 9, 37},
            {5, 6, 18}, {5, 7, 24}, {5, 8, 28}, {5, 9, 34},
            {6, 7, 19}, {6, 8, 25}, {6, 9, 30},
            {7, 8, 20}, {7, 9, 26},
            {8, 9, 22}
        };
        
        WeightedGraph<Integer> graph = new WeightedGraph<>(edges, numberOfCities);
        graph.makeUndirected();
        
//        findOptimalSolution(graph);
        
        MPD tsp = new MPD(graph, 5, 0.01, 1000);
        GAResult result = tsp.evolve();
        
        System.out.println("Solution found: " + result.solutionFound);
        System.out.println("Generations: " + result.generations);
        System.out.println("Best fitness (distance): " + tsp.totalDistance(result.bestChromosome));
        System.out.print("Best tour: ");
        for (int city : result.bestChromosome) {
            System.out.print(city + " ");
        }
        System.out.println();
    }
    

    
    private static boolean nextPermutation(int[] arr) {
        int i = arr.length - 2;
        while (i >= 0 && arr[i] >= arr[i + 1]) {
            i--;
        }
        if (i < 0) {
            return false;
        }
        
        int j = arr.length - 1;
        while (arr[j] <= arr[i]) {
            j--;
        }
        
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        
        int left = i + 1;
        int right = arr.length - 1;
        while (left < right) {
            temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
        
        return true;
    }
    
    @Override
    public int calculateFitness(int[] chromosome) {
        int totalDistance = 0;
        for (int i = 0; i < chromosome.length - 1; i++) {
            try {
                totalDistance += graph.getWeight(chromosome[i], chromosome[i + 1]);
            } catch (Exception e) {
                return Integer.MAX_VALUE;
            }
        }
        return (int)(1_000_000.0 / (totalDistance + 1));
    }
    private int totalDistance(int[] chromosome) throws Exception {
        int distance = 0;
        // Walk each edge in the tour
        for (int i = 0; i < chromosome.length - 1; i++) {
            int from = chromosome[i];
            int to   = chromosome[i + 1];
            distance += graph.getWeight(from, to);
        }
        return distance;
    }

    
    @Override
    public boolean isSolutionFound(int[] fitness) {
        int targetFitness = (int)(1_000_000.0 / (OPTIMAL_DISTANCE + 1));
        for (int f : fitness) {
            if (f >= targetFitness) return true;
        }
        return false;
    }
    
    @Override
    protected void initializePopulation() {
        population = new int[populationSize][chromosomeLength];
        for (int i = 0; i < populationSize; i++) {
            population[i] = generateRandomCycle();
        }
        generation = 0;
    }
    private int[] generateRandomCycle() {
        int[] cycle = new int[chromosomeLength];
        boolean[] used = new boolean[numberOfCities];
        
        cycle[0] = START_CITY;
        used[START_CITY] = true;
        
        for (int i = 1; i < numberOfCities; i++) {
            int city;
            do {
                city = (int) (Math.random() * numberOfCities);
            } while (used[city]);
            cycle[i] = city;
            used[city] = true;
        }
        cycle[numberOfCities] = START_CITY;
        return cycle;
    }
    
    @Override
    protected int[] crossover(int[] parent1, int[] parent2) {
        return orderCrossover(parent1, parent2);
    }
    
    private int[] orderCrossover(int[] parent1, int[] parent2) {
        int[] child = new int[chromosomeLength];
        boolean[] used = new boolean[numberOfCities];
        
        child[0] = START_CITY;
        used[START_CITY] = true;
        
        int start = 1 + (int) (Math.random() * (numberOfCities - 1));
        int end = 1 + (int) (Math.random() * (numberOfCities - 1));
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }
        
        for (int i = start; i <= end; i++) {
            child[i] = parent1[i];
            used[parent1[i]] = true;
        }
        
        int parent2Index = 1;
        for (int i = 1; i < numberOfCities; i++) {
            if (i >= start && i <= end) continue;
            
            while (used[parent2[parent2Index]]) {
                parent2Index++;
            }
            child[i] = parent2[parent2Index];
            used[parent2[parent2Index]] = true;
        }
        
        child[numberOfCities] = START_CITY;
        return child;
    }
    
    @Override
    protected void mutate(int[] chromosome) {
        for (int i = 1; i < numberOfCities; i++) {
            if (Math.random() < mutationRate) {
                int j = 1 + (int) (Math.random() * (numberOfCities - 1));
                
                int temp = chromosome[i];
                chromosome[i] = chromosome[j];
                chromosome[j] = temp;
            }
        }
        chromosome[numberOfCities] = START_CITY;
    }
    
    @Override
    protected int findBestChromosome(int[] fitness) {
        int bestIndex = 0;
        int bestFitness = fitness[0];
        for (int i = 1; i < fitness.length; i++) {
            if (fitness[i] > bestFitness) {
                bestFitness = fitness[i];
                bestIndex = i;
            }
        }
        return bestIndex;
    }
    
    
    private static void findOptimalSolution(WeightedGraph<Integer> graph) {
        int n = graph.getSize();
        int[] cities = new int[n - 1];
        int index = 0;
        for (int i = 0; i < n; i++) {
            if (i != START_CITY) {
                cities[index++] = i;
            }
        }
        
        int[] bestTour = null;
        int bestDistance = Integer.MAX_VALUE;
        MPD tempTSP = new MPD(graph, 1, 0, 1);
        
        do {
            int[] fullTour = new int[n + 1];
            fullTour[0] = START_CITY;
            for (int i = 0; i < n - 1; i++) {
                fullTour[i + 1] = cities[i];
            }
            fullTour[n] = START_CITY;
            
            int distance = tempTSP.calculateFitness(fullTour);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestTour = fullTour.clone();
            }
        } while (nextPermutation(cities));
        
        System.out.printf("Optimal distance: " + bestDistance + "\n");
    }
}