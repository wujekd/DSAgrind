package genetic;
import genetic.testing.GAResult;
import genetic.testing.GAResultsForSettings;
import genetic.testing.GATester;

public class KnapsackWeighted extends GeneticAlgorithm {
	static final int WEIGHT_LIMIT = 15;
	static final int[] WEIGHTS =	{4,7,6,4,3,2,1};
	static final int[] COSTS =		{4,7,6,4,7,2,4};
	static final int SOLUTION = 22;
	
	public KnapsackWeighted(int populationSize, double mutationRate, int maxGenerations){
		setPopulationSize(populationSize);
		setChromosomeLength(WEIGHTS.length);
		setMutationRate(mutationRate);
		setMaxGenerations(maxGenerations);
	}

	// obvious fitness calculation
	@Override
	public int calculateFitness(int[] chromosome) {
		
		int fitness = 0;
		int weight = 0;
		for (int i = 0; i < chromosome.length; i++) {
			if (chromosome[i] == 1) {
				fitness += COSTS[i];
				weight += WEIGHTS[i];
			}
		}
		if (weight > WEIGHT_LIMIT) {
			int excess = weight - WEIGHT_LIMIT;
			fitness -= excess * 10;
		}
		return fitness;
	}

	@Override
	public boolean isSolutionFound(int[] fitness) {
		for (int res : fitness) {
			if (res == SOLUTION) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		KnapsackWeighted ga = new KnapsackWeighted(10, 0.02, 10);
		GATester tester = new GATester();
		
		GAResultsForSettings resbulk = tester.testSettings(ga, 10);
		System.out.println(resbulk);
		
//		GAResult res = ga.evolve();
//		System.out.println(res);
	}
}
