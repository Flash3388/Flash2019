package frc.time;

import edu.wpi.first.wpilibj.Timer;

public class FpgaClock implements Clock {

    @Override
    public long currentTimeMillis() {
        return (long) (Timer.getFPGATimestamp() * 1000);
    }
}
