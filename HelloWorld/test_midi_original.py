#!/usr/bin/env python3

import mido
import time

def test_midi_output():
    print("Available MIDI ports:")
    for port in mido.get_output_names():
        print(f"  - {port}")
    
    # Look for IAC Bus
    iac_port = None
    for port in mido.get_output_names():
        if "Bus 1" in port or "IAC" in port:
            iac_port = port
            break
    
    if not iac_port:
        print("No IAC Bus found!")
        return
    
    print(f"\nConnecting to: {iac_port}")
    
    try:
        with mido.open_output(iac_port) as port:
            print("Connected successfully!")
            
            # Send a simple C4 note
            print("Sending C4 note (note 60)...")
            note_on = mido.Message('note_on', note=60, velocity=100, channel=0)
            note_off = mido.Message('note_off', note=60, velocity=0, channel=0)
            
            port.send(note_on)
            print("Note ON sent")
            time.sleep(1)
            port.send(note_off)
            print("Note OFF sent")
            
            # Send a few more notes
            print("\nSending a simple melody...")
            notes = [60, 62, 64, 65, 67, 69, 71, 72]  # C major scale
            
            for note in notes:
                print(f"Sending note {note} (C major scale)")
                port.send(mido.Message('note_on', note=note, velocity=100, channel=0))
                time.sleep(0.5)
                port.send(mido.Message('note_off', note=note, velocity=0, channel=0))
                time.sleep(0.1)
            
            print("Test complete!")
            
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    test_midi_output() 