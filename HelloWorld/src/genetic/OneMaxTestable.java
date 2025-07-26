package genetic;

public class OneMaxTestable extends GeneticAlgorithm {
    
    public OneMaxTestable() {
        setPopulationSize(50);
        setChromosomeLength(20);
        setMutationRate(0.01);
        setMaxGenerations(1000);
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
        OneMaxTestable oneMax = new OneMaxTestable();
        
        System.out.println("Testing OneMax:");
        GAResult result1 = oneMax.evolve();
        System.out.println(result1);
        
        OneMaxTestable oneMax2 = new OneMaxTestable();
        oneMax2.setMutationRate(0.05);
        System.out.println("\nTesting with mutation rate 0.05:");
        GAResult result2 = oneMax2.evolve();
        System.out.println(result2);
        
        OneMaxTestable oneMax3 = new OneMaxTestable();
        oneMax3.setPopulationSize(100);
        System.out.println("\nTesting with population size 100:");
        GAResult result3 = oneMax3.evolve();
        System.out.println(result3);
    }
} 