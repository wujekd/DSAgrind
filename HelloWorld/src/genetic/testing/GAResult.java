package genetic.testing;

public class GAResult {
    public final boolean solutionFound;
    public final int generations;
    public final int[] bestChromosome;
    public final int bestFitness;
    
    public GAResult(boolean solutionFound, int generations, int[] bestChromosome, int bestFitness) {
        this.solutionFound = solutionFound;
        this.generations = generations;
        this.bestChromosome = bestChromosome.clone(); // copy because its a reference type
        this.bestFitness = bestFitness;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Best Fitness: ").append(bestFitness).append("\n");
        sb.append("Generations: ").append(generations).append("\n");
        sb.append("Solution Found: ").append(solutionFound ? "Yes" : "No").append("\n");
        sb.append("Best Chromosome: ");
        for (int gene : bestChromosome) {
            sb.append(gene);
        }
        return sb.toString();
    }
}