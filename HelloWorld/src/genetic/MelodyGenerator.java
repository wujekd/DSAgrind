package genetic;

import genetic.testing.GAResult;
import audio.MelodyConverter;
import audio.MidiTransmitter;
import audio.PythonMidiBridge;

public class MelodyGenerator extends GeneticAlgorithm {
    private static final int[] C_MAJOR_SCALE = {0, 2, 4, 5, 7, 9, 11, 12};
    private static final int MELODY_LENGTH = 4;
    private static final boolean DEBUG_PRINTS = true;
    
    private int rootNote;
    private double movementWeight;
    private double consonanceWeight;
    
    public MelodyGenerator(int rootNote, double movementWeight, double consonanceWeight) {
        this.rootNote = rootNote;
        this.movementWeight = movementWeight;
        this.consonanceWeight = consonanceWeight;
        setChromosomeLength(MELODY_LENGTH);
    }
    
    @Override
    protected void initializePopulation() {
        population = new int[populationSize][chromosomeLength];
        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < chromosomeLength; j++) {
                population[i][j] = C_MAJOR_SCALE[(int)(Math.random() * C_MAJOR_SCALE.length)];
            }
        }
        generation = 0;
    }
    
    @Override
    public int calculateFitness(int[] chromosome) {
        int movementScore = calculateMovementScore(chromosome);
        int consonanceScore = calculateConsonanceScore(chromosome);
        
        return (int)(movementWeight * movementScore + consonanceWeight * consonanceScore);
    }
    
    private int calculateMovementScore(int[] chromosome) {
        int movement = 0;
        for (int i = 1; i < chromosome.length; i++) {
            movement += Math.abs(chromosome[i] - chromosome[i-1]);
        }
        return movement;
    }
    
    private int calculateConsonanceScore(int[] chromosome) {
        int score = 0;
        for (int note : chromosome) {
            int interval = note % 12;
            if (interval == 0 || interval == 4 || interval == 7) {
                score += 10;
            } else if (interval == 5 || interval == 9) {
                score += 5;
            } else if (interval == 2 || interval == 11) {
                score += 1;
            }
        }
        return score;
    }
    
    @Override
    public boolean isSolutionFound(int[] fitness) {
        int bestFitness = fitness[findBestChromosome(fitness)];
        return bestFitness >= 35;
    }
    
    @Override
    protected void mutate(int[] chromosome) {
        for (int i = 0; i < chromosomeLength; i++) {
            if (Math.random() < mutationRate) {
                chromosome[i] = C_MAJOR_SCALE[(int)(Math.random() * C_MAJOR_SCALE.length)];
            }
        }
    }
    
    public int[] generateMelody() {
        GAResult result = evolve();
        int[] scaleIndices = new int[result.bestChromosome.length];
        for (int i = 0; i < result.bestChromosome.length; i++) {
            int note = result.bestChromosome[i];
            for (int j = 0; j < C_MAJOR_SCALE.length; j++) {
                if (C_MAJOR_SCALE[j] == note) {
                    scaleIndices[i] = j;
                    break;
                }
            }
        }
        return scaleIndices;
    }
    
    public int[] generateMelodyWithRoot() {
        int[] melody = generateMelody();
        int[] melodyWithRoot = new int[melody.length];
        for (int i = 0; i < melody.length; i++) {
            melodyWithRoot[i] = rootNote + melody[i];
        }
        return melodyWithRoot;
    }
    
    public void playGeneratedMelody() {
        int[] melody = generateMelody();
        
        if (DEBUG_PRINTS) {
            System.out.println("Generated melody (scale indices):");
            for (int note : melody) {
                System.out.print(note + " ");
            }
        }
        
        MelodyConverter.Melody convertedMelody = MelodyConverter.convertToMelody(melody);
        MelodyConverter.printMelody(convertedMelody);
        

        
        MidiTransmitter transmitter = new PythonMidiBridge();
        transmitter.streamMelody(convertedMelody);
        

    }
    
    public static void main(String[] args) {
        MelodyGenerator generator = new MelodyGenerator(60, 0.3, 0.7);
        generator.playGeneratedMelody();
    }
} 