package frc.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Piston {
    private final DoubleSolenoid solenoid;

    public Piston(int pcm, int forwardChannel, int reverseChannel) {
        solenoid = new DoubleSolenoid(pcm, forwardChannel, reverseChannel);
    }

    public void open() {
        solenoid.set(Value.kForward);
    }

    public void close() {
        solenoid.set(Value.kReverse);
    }

    public void toggle() {
        solenoid.set(solenoid.get() == Value.kForward ? Value.kReverse : Value.kForward);
    }
}