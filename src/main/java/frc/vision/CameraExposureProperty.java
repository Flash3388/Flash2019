package frc.vision;

import edu.wpi.first.networktables.NetworkTableInstance;
import frc.tables.NtDoubleProperty;

public class CameraExposureProperty extends NtDoubleProperty {

    public CameraExposureProperty() {
        super(NetworkTableInstance.getDefault().getTable("cameraCtrl").getEntry("exposure"));
    }
}
