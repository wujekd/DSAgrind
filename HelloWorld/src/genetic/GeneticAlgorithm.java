package genetic;

public abstract class GeneticAlgorithm {
    
    protected int populationSize = 50;
    protected int chromosomeLength = 20;
    protected double mutationRate = 0.01;
    protected int maxGenerations = 1000;
    protected int[][] population;
    protected int generation;
    
    public GeneticAlgorithm() {
        this.generation = 0;
    }
    
    public void setPopulationSize(int size) { this.populationSize = size; }
    public void setChromosomeLength(int length) { this.chromosomeLength = length; }
    public void setMutationRate(double rate) { this.mutationRate = rate; }
    public void setMaxGenerations(int generations) { this.maxGenerations = generations; }
    
    public int getGeneration() { return generation; }
    public int[][] getPopulation() { return population; }
    
    public abstract int calculateFitness(int[] chromosome);
    public abstract boolean isSolutionFound(int[] fitness);
    
    public GAResult evolve() {
        initializePopulation();
        while (generation < maxGenerations) {
            int[] fitness = evaluatePopulation();
            
            if (isSolutionFound(fitness)) {
                int bestIndex = findBestChromosome(fitness);
                return new GAResult(true, generation, population[bestIndex], fitness[bestIndex]);
            }
            createNewGeneration(fitness);
            generation++;
        }
        int[] fitness = evaluatePopulation();
        int bestIndex = findBestChromosome(fitness);
        return new GAResult(false, generation, population[bestIndex], fitness[bestIndex]);
    }
    

    
    protected void initializePopulation() {
        population = new int[populationSize][chromosomeLength];
        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < chromosomeLength; j++) {
                population[i][j] = Math.random() < 0.5 ? 1 : 0;
            }
        }
        generation = 0;
    }
    
    protected int[] evaluatePopulation() {
        int[] fitness = new int[populationSize];
        for (int i = 0; i < populationSize; i++) {
            fitness[i] = calculateFitness(population[i]);
        }
        return fitness;
    }
    
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
    
    protected void createNewGeneration(int[] fitness) {
        int[][] newPopulation = new int[populationSize][chromosomeLength];
        for (int i = 0; i < populationSize; i++) {
            int[] parent1 = tournamentSelect(fitness);
            int[] parent2 = tournamentSelect(fitness);
            int[] child = crossover(parent1, parent2);
            mutate(child);
            newPopulation[i] = child;
        }
        population = newPopulation;
    }
    
    // Tournament selection (same as your existing implementations)
    protected int[] tournamentSelect(int[] fitness) {
        int tournamentSize = 3;
        int best = (int) (Math.random() * populationSize);
        for (int i = 1; i < tournamentSize; i++) {
            int idx = (int) (Math.random() * populationSize);
            if (fitness[idx] > fitness[best]) {
                best = idx;
            }
        }
        return population[best].clone();
    }
    
    protected int[] crossover(int[] parent1, int[] parent2) {
        int[] child = new int[chromosomeLength];
        int crossoverPoint = (int) (Math.random() * chromosomeLength);
        for (int i = 0; i < chromosomeLength; i++) {
            child[i] = i < crossoverPoint ? parent1[i] : parent2[i];
        }
        return child;
    }
    
    protected void mutate(int[] chromosome) {
        for (int i = 0; i < chromosomeLength; i++) {
            if (Math.random() < mutationRate) {
                chromosome[i] = 1 - chromosome[i];
            }
        }
    }
    
    protected void printChromosome(int[] chromosome) {
        for (int gene : chromosome) {
            System.out.print(gene);
        }
        System.out.println();
    }
}