package frc.io;

import edu.flash3388.flashlib.robot.devices.RangeFinder;
import edu.wpi.first.wpilibj.AnalogInput;

public class MaxSonarAnalogSonic implements RangeFinder {

    private static final double MVOLTAGE_TO_MM = 4.88 / 5.0;

    private AnalogInput mAnalogInput;

    public MaxSonarAnalogSonic(AnalogInput analogInput) {
        mAnalogInput = analogInput;
    }

    @Override
    public double getRangeCM() {
        return (MVOLTAGE_TO_MM * (mAnalogInput.getVoltage()*1000.0)) / 10.0;
    }

    @Override
    public void free() {
        if (mAnalogInput != null) {
            mAnalogInput.close();
            mAnalogInput = null;
        }
    }
}
