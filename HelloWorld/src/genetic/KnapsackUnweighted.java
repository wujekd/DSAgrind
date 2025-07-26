package genetic;

public class KnapsackUnweighted {
    static final int POPULATION_SIZE = 5;
    static final int CHROMOSOME_LENGTH = 7;
    static final double MUTATION_RATE = 0.01;
    static final int MAX_GENERATIONS = 1000;
    static final int WEIGHT_LIMIT = 15;
    static final int[] WEIGHTS = {4,7,6,4,3,2,1};


    public static void main(String[] args) {
        // initial random population
        int[][] population = new int[POPULATION_SIZE][CHROMOSOME_LENGTH];
        for (int i = 0; i < POPULATION_SIZE; i++){
            for (int j = 0; j < CHROMOSOME_LENGTH; j++){
                population[i][j] = Math.random() <  0.5? 1:0;
            }
        }
//        printPopulation(population);
        
        int generation = 0;
        while (generation < MAX_GENERATIONS) {
        	int[] fitness = new int[POPULATION_SIZE];
        	int bestFitness = 0;
        	int bestIndex = 0;
        	
        	for (int i=0; i < POPULATION_SIZE; i++) {
        		int weight = 0;
        		
        		for (int j = 0; j < CHROMOSOME_LENGTH; j++) {
        			if (population[i][j] == 1) weight += WEIGHTS[j];
        		}
        		if (weight > WEIGHT_LIMIT) {
        			weight *= 0.2;
        		}
        		
        		fitness[i] =  weight;
        		
        		if (weight > bestFitness) {
					bestFitness = weight;
					bestIndex = i;
				}
        	}
        	// FOUND SOLUTION
    		if (bestFitness == WEIGHT_LIMIT) {
				System.out.println("solution found in " + generation + " generation.");
				System.out.println("chromosome " + (bestIndex + 1) + " : ");
				printChromosome(population[bestIndex]);
//				printPopulation(population);
				break;
			}
    		// breed new population
    		int[][] newPopulation = new int[POPULATION_SIZE][CHROMOSOME_LENGTH];
			for (int i = 0; i < POPULATION_SIZE; i++) {
				int[] parent1 = tournamentSelect(population, fitness);
				int[] parent2 = tournamentSelect(population, fitness);
				int[] child = crossover(parent1, parent2);
				
				mutate(child);
				newPopulation[i] = child;
			}
			population = newPopulation;
			generation++;
        }
        if (generation == MAX_GENERATIONS) {
			System.out.println("max generations reached, no solution found");
		}
    }
    
    static int[] tournamentSelect(int[][] population, int[] fitness) {
		int tournamentSize = 3;
		
		int best = (int) (Math.random() * POPULATION_SIZE);
		for (int i = 1; i < tournamentSize; i++) {
			int idx = (int) (Math.random() * POPULATION_SIZE);
			if (fitness[idx] > fitness[best]) best = idx;
		}
		return population[best].clone();
	}
    
    static int[] crossover(int[] p1, int[] p2) {
        int[] child = new int[CHROMOSOME_LENGTH];
        int crossoverPoint = (int) (Math.random() * CHROMOSOME_LENGTH);
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            child[i] = i < crossoverPoint ? p1[i] : p2[i];
        }
        return child;
    }

    static void mutate(int[] chromosome) {
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            if (Math.random() < MUTATION_RATE) {
                chromosome[i] = 1 - chromosome[i];
            }
        }
    }
    
    static void printChromosome(int[] chromosome) {
        for (int gene : chromosome) System.out.print(gene);
        System.out.println();
    }
    
    public static void printPopulation(int[][] population) {
        System.out.println("Current Population:");
        for (int i = 0; i < population.length; i++) {
            System.out.print("Chromosome " + (i + 1) + ": ");
            int weight = 0;
            for (int j = 0; j < population[i].length; j++) {
                System.out.print(population[i][j]);
                if (population[i][j] == 1) {
                    weight += WEIGHTS[j];
                }
            }
            System.out.println(" | Weight: " + weight);
        }
    }
}