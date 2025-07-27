package genetic;

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
        if (solutionFound) {
            sb.append("Solution found in ").append(generations).append(" generations.\n");
        } else {
            sb.append("Max generations reached, no solution found\n");
        }
        for (int gene : bestChromosome) {
            sb.append(gene);
        }
        return sb.toString();
    }
}