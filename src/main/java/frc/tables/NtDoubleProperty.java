package frc.tables;

import edu.flash3388.flashlib.util.beans.DoubleProperty;
import edu.wpi.first.networktables.NetworkTableEntry;

public class NtDoubleProperty implements DoubleProperty {

    private final NetworkTableEntry mEntry;

    public NtDoubleProperty(NetworkTableEntry entry) {
        mEntry = entry;
    }

    @Override
    public void set(double v) {
        mEntry.setDouble(v);
    }

    @Override
    public double get() {
        return mEntry.getDouble(0.0);
    }

    @Override
    public void setValue(Double aDouble) {
        set(aDouble == null ? 0.0 : aDouble);
    }

    @Override
    public Double getValue() {
        return get();
    }
}
