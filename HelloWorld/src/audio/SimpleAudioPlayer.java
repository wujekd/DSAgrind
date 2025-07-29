package audio;

import javax.sound.sampled.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SimpleAudioPlayer {
    private static final float SAMPLE_RATE = 44100.0f;
    private static final int SAMPLE_SIZE_IN_BITS = 16;
    private static final int CHANNELS = 1;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = false;
    
    public static void playMelody(MelodyConverter.Melody melody) {
        try {
            byte[] audioData = generateAudioData(melody);
            playAudioData(audioData);
        } catch (Exception e) {
            System.err.println("Error playing melody: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static byte[] generateAudioData(MelodyConverter.Melody melody) throws IOException {
        ByteArrayOutputStream audioStream = new ByteArrayOutputStream();
        
        for (int i = 0; i < melody.notes.size(); i++) {
            int midiNote = melody.notes.get(i);
            int velocity = melody.velocities.get(i);
            double duration = melody.durations.get(i);
            
            if (velocity > 0 && midiNote > 0) {
                byte[] noteData = generateNote(midiNote, velocity, duration);
                audioStream.write(noteData);
            } else {
                byte[] silenceData = generateSilence(duration);
                audioStream.write(silenceData);
            }
        }
        
        return audioStream.toByteArray();
    }
    
    private static byte[] generateNote(int midiNote, int velocity, double duration) {
        double frequency = midiToFrequency(midiNote);
        double amplitude = velocity / 127.0;
        int numSamples = (int) (duration * SAMPLE_RATE);
        
        byte[] audioData = new byte[numSamples * 2];
        int dataIndex = 0;
        
        for (int i = 0; i < numSamples; i++) {
            double time = (double) i / SAMPLE_RATE;
            double sample = Math.sin(2 * Math.PI * frequency * time) * amplitude;
            
            short sampleValue = (short) (sample * 32767);
            audioData[dataIndex++] = (byte) (sampleValue & 0xFF);
            audioData[dataIndex++] = (byte) ((sampleValue >> 8) & 0xFF);
        }
        
        return audioData;
    }
    
    private static byte[] generateSilence(double duration) {
        int numSamples = (int) (duration * SAMPLE_RATE);
        return new byte[numSamples * 2];
    }
    
    private static double midiToFrequency(int midiNote) {
        return 440.0 * Math.pow(2.0, (midiNote - 69) / 12.0);
    }
    
    private static void playAudioData(byte[] audioData) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
        
        ByteArrayInputStream audioInputStream = new ByteArrayInputStream(audioData);
        AudioInputStream ais = new AudioInputStream(audioInputStream, format, audioData.length / format.getFrameSize());
        
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException("Audio format not supported");
        }
        
        SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
        audioLine.open(format);
        audioLine.start();
        
        byte[] buffer = new byte[4096];
        int bytesRead;
        
        while ((bytesRead = ais.read(buffer)) != -1) {
            audioLine.write(buffer, 0, bytesRead);
        }
        
        audioLine.drain();
        audioLine.close();
        ais.close();
    }
    
    public static void playMelodyWithDelay(MelodyConverter.Melody melody, long delayMs) {
        new Thread(() -> {
            try {
                Thread.sleep(delayMs);
                playMelody(melody);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
} 