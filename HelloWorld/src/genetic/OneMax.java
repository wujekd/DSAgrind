package genetic;

public class OneMax {
	
	static final int POPULATION_SIZE = 50;
    static final int CHROMOSOME_LENGTH = 20;
    static final double MUTATION_RATE = 0.01;
    static final int MAX_GENERATIONS = 1000;
    
	public static void main(String[] args) {

		int[][] population = new int[POPULATION_SIZE][CHROMOSOME_LENGTH];
		for (int i = 0; i < POPULATION_SIZE; i++) {
			for (int j = 0; j < CHROMOSOME_LENGTH; j++) {
                population[i][j] = Math.random() < 0.5 ? 1 : 0;
            }
		}
		
		int generation = 0;
		while (generation < MAX_GENERATIONS) {
			// evaluate fitness
			int[] fitness = new int[POPULATION_SIZE];
			int bestFitness = 0;
			int bestIndex = 0;
			for (int i = 0; i < POPULATION_SIZE; i++) {
				int sum = 0;
				for (int gene : population[i]) sum += gene;
				fitness[i] = sum;
				if (sum > bestFitness) {
					bestFitness = sum;
					bestIndex = i;
				}
			}
			// check for solution
			if (bestFitness == CHROMOSOME_LENGTH) {
				System.out.println("solution found in " + generation + " generation.");
				printChromosome(population[bestIndex]);
				break;
			}
			
			//create new populaiton by breeding individuals from a previous one
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
            	// System.out.println("mutation!");
                chromosome[i] = 1 - chromosome[i];
            }
        }
    }
	
	static void printChromosome(int[] chromosome) {
        for (int gene : chromosome) System.out.print(gene);
        System.out.println();
    }
}