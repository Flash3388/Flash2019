package frc.robot;
import edu.flash3388.flashlib.util.beans.DoubleProperty;
import edu.wpi.first.networktables.NetworkTableEntry;

public class NetworkDoubleProp implements DoubleProperty{
    private NetworkTableEntry doubleEntry;

    public NetworkDoubleProp(NetworkTableEntry entry) {
        doubleEntry = entry;
    }

    @Override
    public void setValue(Double arg0) {

    }

    @Override
    public Double getValue() {
        return get();
    }

    @Override
    public double get() {
        return doubleEntry.getDouble(0);
    }

    @Override
    public void set(double arg0) {
    }
}
