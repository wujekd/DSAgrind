package genetic;

import audio.MelodyConverter;
import audio.MidiTransmitter;
import audio.PythonMidiBridge;

public class Music1 {
    private static final int BITS_PER_NOTE = 4;
    private static final int NOTES_PER_BAR = 16;
    private static final int[] C_MAJOR_SCALE = {60, 62, 64, 65, 67, 69, 71, 72};

    public static void main(String[] args) {
        int[] melody = createHardcodedMelody();
        System.out.println("Hard-coded melody:");
        printMelody(melody);

        System.out.println("\n" + "=".repeat(40));

        MelodyConverter.Melody convertedMelody = MelodyConverter.convertToMelody(melody);
        MelodyConverter.printMelody(convertedMelody);

        System.out.println("\n" + "=".repeat(40));
        System.out.println("Streaming melody to Ableton Live...");
 
        MidiTransmitter transmitter = new PythonMidiBridge();
        transmitter.streamMelody(convertedMelody);

        System.out.println("MIDI streaming complete!");
    }

    private static int[] createHardcodedMelody() {
        return new int[] {
            0, 0, 2, 4,  // C, C, D, E (C should merge)
            1, 3, 5, 8,  // D, E, F, rest  
            4, 4, 6, 0,  // E, E, G, C (E should merge)
            2, 4, 6, 0   // D, E, G, C
        };
    }

    private static void printMelody(int[] melody) {
        String[] noteNames = {"C", "D", "E", "F", "G", "A", "B", "C", "rest"};
        for (int i = 0; i < melody.length; i++) {
            System.out.print(noteNames[melody[i]] + " ");
            if ((i + 1) % 4 == 0) System.out.println();
        }
    }
}
