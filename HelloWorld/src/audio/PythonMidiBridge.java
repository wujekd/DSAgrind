package audio;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class PythonMidiBridge implements MidiTransmitter {
    private static final boolean DEBUG_PRINTS = false;

    public void streamMelody(MelodyConverter.Melody melody) {

        try {
            String pythonScript = createPythonScript(melody);
            String scriptPath = "temp_midi_script.py";

            writePythonScript(pythonScript, scriptPath);

            ProcessBuilder pb = new ProcessBuilder("/opt/homebrew/bin/python3", scriptPath);
            pb.inheritIO();

            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
//                System.out.println("Python MIDI streaming completed successfully");
            } else {
//                System.err.println("Python MIDI streaming failed with exit code: " + exitCode);
            }

            new File(scriptPath).delete();

        } catch (Exception e) {
            System.err.println("Error in Python MIDI bridge: " + e.getMessage());
        }
    }

    private String createPythonScript(MelodyConverter.Melody melody) {
        StringBuilder script = new StringBuilder();
        script.append("#!/usr/bin/env python3\n\n");
        script.append("import mido\n");
        script.append("import time\n\n");
        script.append("DEBUG_PRINTS = ").append(DEBUG_PRINTS ? "True" : "False").append("\n\n");
        script.append("def stream_melody():\n");
        script.append("    try:\n");
        script.append("        with mido.open_output('IAC Driver Bus 1') as port:\n");

        for (int i = 0; i < melody.notes.size(); i++) {
            int note = melody.notes.get(i);
            int velocity = melody.velocities.get(i);
            double duration = melody.durations.get(i);

            if (velocity > 0 && note > 0) {
                script.append("            if DEBUG_PRINTS:\n");
                script.append("                print(f'Sending note ").append(note).append(" (velocity: ").append(velocity).append(", duration: ").append(duration).append("s)')\n");
                script.append("            port.send(mido.Message('note_on', note=").append(note).append(", velocity=").append(velocity).append(", channel=0))\n");
                script.append("            time.sleep(").append(duration).append(")\n");
                script.append("            port.send(mido.Message('note_off', note=").append(note).append(", velocity=0, channel=0))\n");
                script.append("            time.sleep(0.05)\n");
            } else {
                script.append("            if DEBUG_PRINTS:\n");
                script.append("                print(f'Rest for ").append(duration).append("s')\n");
                script.append("            time.sleep(").append(duration).append(")\n");
            }
        }

        script.append("            if DEBUG_PRINTS:\n");
        script.append("                print('Melody streaming completed')\n");
        script.append("    except Exception as e:\n");
        script.append("        print(f'Error: {e}')\n\n");
        script.append("if __name__ == '__main__':\n");
        script.append("    stream_melody()\n");

        return script.toString();
    }

    private void writePythonScript(String script, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(script);
        }
    }
} 