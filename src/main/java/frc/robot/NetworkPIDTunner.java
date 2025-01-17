package frc.robot;

import edu.flash3388.flashlib.util.beans.DoubleProperty;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkPIDTunner {
    private static final String TABLE_NAME = "pid_data_table";
    private static final String P_KEY = "p_key";
    private static final String D_KEY = "d_key";
    private static final String I_KEY = "i_key";
    private static final String F_KEY = "f_key";
    private static final String SETPOINT_KEY = "setpoint_key";

    private final NetworkTable mPIDTable;

    public NetworkPIDTunner() {
        mPIDTable = NetworkTableInstance.getDefault().getTable(TABLE_NAME);

        setSetpoint(0.0);
        setP(0.0);
        setI(0.0);
        setD(0.0);
    }

    public void setSetpoint(double d) {
        mPIDTable.getEntry(SETPOINT_KEY).setDouble(d);
    }

    public void setP(double p) {
        mPIDTable.getEntry(P_KEY).setDouble(p);
    }

    public void setI(double i) {
        mPIDTable.getEntry(I_KEY).setDouble(i);
    }

    public void setD(double d) {
        mPIDTable.getEntry(D_KEY).setDouble(d);
    }

    public DoubleProperty getSetpoint() {
        return new NetworkDoubleProp(mPIDTable.getEntry(SETPOINT_KEY));
    }
    
    public DoubleProperty getD() {
        return new NetworkDoubleProp(mPIDTable.getEntry(D_KEY));
    }
    
    public DoubleProperty getP() {
        return new NetworkDoubleProp(mPIDTable.getEntry(P_KEY));
    }
    
    public DoubleProperty getI() {
        return new NetworkDoubleProp(mPIDTable.getEntry(I_KEY));
    }


    public DoubleProperty getF() {
        return new NetworkDoubleProp(mPIDTable.getEntry(F_KEY));
    }
}
