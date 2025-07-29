package audio;

import java.util.ArrayList;
import java.util.List;

public class MelodyConverter {
    private static final int[] C_MAJOR_SCALE = {60, 62, 64, 65, 67, 69, 71, 72};
    private static final int DEFAULT_VELOCITY = 127;
    private static final double NOTE_LENGTH = 0.25; // 16th note at 120 BPM
    
    public static class Melody {
        public List<Integer> notes;
        public List<Integer> velocities;
        public List<Double> durations;
        
        public Melody() {
            this.notes = new ArrayList<>();
            this.velocities = new ArrayList<>();
            this.durations = new ArrayList<>();
        }
    }
    
    public static Melody convertToMelody(int[] encodedMelody) {
        Melody melody = new Melody();
        
        for (int i = 0; i < encodedMelody.length; i++) {
            int value = encodedMelody[i];
            
            if (value >= 8) {
                if (!melody.notes.isEmpty() && melody.velocities.get(melody.velocities.size() - 1) > 0) {
                    melody.durations.set(melody.durations.size() - 1, 
                                       melody.durations.get(melody.durations.size() - 1) + NOTE_LENGTH);
                } else {
                    melody.notes.add(0);
                    melody.velocities.add(0);
                    melody.durations.add(NOTE_LENGTH);
                }
            } else {
                int midiNote = C_MAJOR_SCALE[value];
                
                if (!melody.notes.isEmpty() && melody.notes.get(melody.notes.size() - 1) == midiNote && 
                    melody.velocities.get(melody.velocities.size() - 1) > 0) {
                    melody.durations.set(melody.durations.size() - 1, 
                                       melody.durations.get(melody.durations.size() - 1) + NOTE_LENGTH);
                } else {
                    melody.notes.add(midiNote);
                    melody.velocities.add(DEFAULT_VELOCITY);
                    melody.durations.add(NOTE_LENGTH);
                }
            }
        }
        
        return melody;
    }
    
    public static void printMelody(Melody melody) {
        System.out.println("Converted melody:");
        System.out.println("Notes: " + melody.notes);
        System.out.println("Velocities: " + melody.velocities);
        System.out.println("Durations: " + melody.durations);
        
        System.out.println("\nMelody breakdown:");
        for (int i = 0; i < melody.notes.size(); i++) {
            if (melody.velocities.get(i) > 0) {
                System.out.printf("Note: %d (MIDI), Duration: %.2fs%n", 
                                 melody.notes.get(i), melody.durations.get(i));
            } else {
                System.out.printf("Rest, Duration: %.2fs%n", melody.durations.get(i));
            }
        }
    }
} 