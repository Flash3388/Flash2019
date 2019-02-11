package frc.tables;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class TargetData {
    private final static String TARGET_DATA_TABLE = "target_data_table";
    private final static String X_OFFSET_KEY = "x_offset_key";
    private final static String VISION_DISTANCE_KEY = "vision_distance_key";

    private NetworkTable mTargetDataTable;

    TargetData() {
        mTargetDataTable = NetworkTableInstance.getDefault().getTable(TARGET_DATA_TABLE);
    }

    public void setXOffset(int xOffset) {
        mTargetDataTable.getEntry(X_OFFSET_KEY).setDouble((double)(xOffset));
    }

    public int getXOffset(int defaultValue) {
        return (int)mTargetDataTable.getEntry(VISION_DISTANCE_KEY).getDouble((double)defaultValue);
    }

    public void setVisionDistance(int visionDistance) {
        mTargetDataTable.getEntry(VISION_DISTANCE_KEY).setDouble((double)(visionDistance));
    }

    public int getVisionDistance(int defaultValue) {
        return (int)mTargetDataTable.getEntry(VISION_DISTANCE_KEY).getDouble((double)defaultValue);
    }
    

} 