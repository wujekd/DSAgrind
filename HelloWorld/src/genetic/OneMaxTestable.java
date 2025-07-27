package genetic;

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
        OneMaxTestable oneMax = new OneMaxTestable(50, 20, 0.01, 1000);
        
        GAResult result1 = oneMax.evolve();
        System.out.println(result1);
        
        oneMax.setMutationRate(0.05);
        GAResult result2 = oneMax.evolve();
        System.out.println("\n" + result2);
        
        oneMax.setPopulationSize(100);
        GAResult result3 = oneMax.evolve();
        System.out.printf("\n" + result3);
    }
} 