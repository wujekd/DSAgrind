package audio;

import javax.sound.midi.*;

public class sweepMIDIChannelsTest {
    private Receiver receiver;
    
    public static void main(String[] args) {
        sweepMIDIChannelsTest test = new sweepMIDIChannelsTest();
        try {
            test.sweepChannelsTest();
        } catch (InterruptedException e) {
            System.err.println("Test interrupted: " + e.getMessage());
        } finally {
            test.close();
        }
    }
    
    public sweepMIDIChannelsTest() {
        setupMidiDevice();
    }
    
    private void setupMidiDevice() {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        
        for (MidiDevice.Info info : infos) {
            if (info.getName().contains("Bus 1")) {
                try {
                    MidiDevice device = MidiSystem.getMidiDevice(info);
                    if (device.getMaxReceivers() != 0) {
                        device.open();
                        receiver = device.getReceiver();
                        System.out.println("Connected to MIDI device: " + info.getName());
                        return;
                    }
                } catch (MidiUnavailableException e) {
                    System.out.println("Could not connect to " + info.getName() + ": " + e.getMessage());
                }
            }
        }
        
        System.out.println("IAC Driver Bus 1 not found or not available for output.");
    }
    
    public void sweepChannelsTest() throws InterruptedException {
        if (receiver == null) {
            System.out.println("[sweep] No receiver");
            return;
        }
        System.out.println("[sweep] Sending C4 on channels 1→16…");
        for (int ch = 0; ch < 16; ch++) {
            try {
                ShortMessage on  = new ShortMessage();
                ShortMessage off = new ShortMessage();
                on.setMessage(ShortMessage.NOTE_ON,  ch, 60, 100);
                off.setMessage(ShortMessage.NOTE_OFF, ch, 60,   0);
                System.out.printf("[sweep] channel %d → NOTE_ON%n", ch+1);
                receiver.send(on,  -1);
                Thread.sleep(400);
                receiver.send(off, -1);
                Thread.sleep(200);
            } catch (InvalidMidiDataException e) {
                System.err.println("Error sending MIDI message on channel " + ch + ": " + e.getMessage());
            }
        }
        System.out.println("[sweep] done.");
    }
    
    public void close() {
        if (receiver != null) {
            receiver.close();
        }
    }
}
