package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

public class SuffleboardHandler {
    private final NetworkTableEntry kp;
    private final NetworkTableEntry kd;
    private final NetworkTableEntry ki;
    private final NetworkTableEntry setpoint;
    private final NetworkTableEntry limit;
    private final NetworkTableEntry pidSource;

    public SuffleboardHandler(NetworkTable table) {
        kp = table.getEntry("kp");
        kd = table.getEntry("kd");
        ki = table.getEntry("ki");
        setpoint = table.getEntry("setpoint");
        limit = table.getEntry("limit");
        pidSource = table.getEntry("pidsource");
        setDefaults();
    }

    public void setDefaults() {
        kp.setDouble(0.0);
        ki.setDouble(0.0);
        kd.setDouble(0.0);
        setpoint.setDouble(0.0);
        limit.setDouble(0.0);
        pidSource.setDouble(0.0);
    }
}