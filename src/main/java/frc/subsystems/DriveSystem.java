package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.flash3388.flashlib.robot.PIDController;
import edu.flash3388.flashlib.robot.PIDSource;
import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.FlashDrive;
import edu.flash3388.flashlib.robot.systems.TankDriveSystem;
import edu.flash3388.flashlib.util.FlashUtil;
import edu.flash3388.flashlib.util.beans.DoubleProperty;
import edu.flash3388.flashlib.util.beans.PropertyHandler;
import edu.flash3388.flashlib.util.beans.SimpleDoubleProperty;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.NetworkPIDTunner;

public class DriveSystem extends Subsystem implements TankDriveSystem {
    public static final double DRIVE_LIMIT = 1.0;
    public static final double ROTATE_LIMIT = 1.0;

    private final double CAMERA_FOV = 65.0;
    private final double CAMERA_WIDTH = 320.0;
    private final double DEGREES_IN_A_CIRCLE = 360.0;

    private final double WHEEL_DIAMETER = 15.24;
    private final double PPR = 4096.0;

    private static final String DISTANCE_NAME = "distanceSetPoint";
    private static final String ROTATION_NAME = "rotationSetPoint";
    private static final String OFFSET_ENTRY_KEY = "xoffset";

    private final NetworkPIDTunner mRotationTunner;

    public PIDController distancePID;
    public PIDController rotatePID;
    public PIDSource distanceSource;
    public PIDSource rotationSource;

    public DoubleProperty distanceSetPoint = PropertyHandler.putNumber(DISTANCE_NAME, 0.0);
    public DoubleProperty rotationSetPoint = PropertyHandler.putNumber(ROTATION_NAME, 0.0);

    private final NetworkTableEntry mOffsetEntry;
    private final NetworkTableEntry mDistanceEntry;
    private final ADXRS450_Gyro mGyro;

    private final VictorSP mRearRight;
    private final VictorSP mFrontRight;
    private final VictorSP mRearLeft;
    private final TalonSRX mFrontLeft;

    public final DoubleProperty motherFuckingProperty = new SimpleDoubleProperty();

    public DriveSystem(int frontRight, int frontLeft, int rearRight, int rearLeft, NetworkTable table) {
        mFrontRight = new VictorSP(frontRight);
        mRearRight = new VictorSP(rearRight);
        mFrontLeft = new TalonSRX(frontLeft);
        mRearLeft = new VictorSP(rearLeft);

        mFrontLeft.setInverted(true);
        mRearLeft.setInverted(true);

        mOffsetEntry = table.getEntry(OFFSET_ENTRY_KEY);
        mDistanceEntry = table.getEntry("distance_vision");
        mRotationTunner = new NetworkPIDTunner();
        mGyro = new ADXRS450_Gyro();
        mGyro.calibrate();

        mFrontLeft.configFactoryDefault();
        mFrontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        pidsHandler();

        table.addEntryListener("xoffset", new TableEntryListener() {

            @Override
            public void valueChanged(NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value,
                    int flags) {
                motherFuckingProperty.set(FlashUtil.millisInt());
            }
        }, EntryListenerFlags.kUpdate);
    }

    @Override
    public void arcadeDrive(double moveValue, double rotateValue) {
        double[] values = FlashDrive.calculate_arcadeDrive(moveValue, rotateValue);
        tankDrive(values[0], values[1]);
    }

    public double getMod() {
        return mRotationTunner.getMod();
    }

    @Override
    public void tankDrive(double right, double left) {
        mRearRight.set(right * 1.25);
        mFrontRight.set(right* 1.25);
        mFrontLeft.set(ControlMode.PercentOutput, left);
        mRearLeft.set(left);
    }

    @Override
    public void stop() {
        tankDrive(0, 0);
    }

    private void pidsHandler() {
        distanceSource = new PIDSource() {
            @Override
            public double pidGet() {
                return getDistance();
            }
        };

        rotationSource = new PIDSource() {
            @Override
                public double pidGet() {
                    return getAngle();
            }
        };

        distancePID = new PIDController(0.21, 0.0, 0.285, 0.0, distanceSetPoint, distanceSource);
        distancePID.setOutputLimit(-DRIVE_LIMIT, DRIVE_LIMIT);
        rotatePID = new PIDController(0.04,0.0, 0.0, 0.0, rotationSetPoint, rotationSource);
        rotatePID.setOutputLimit(-ROTATE_LIMIT, ROTATE_LIMIT);

        //rotatePID.setPID(0.09, 0.1865, 0.0109);
    }

    public double getDistance() {
        return -(mFrontLeft.getSelectedSensorPosition() / PPR) * WHEEL_DIAMETER * Math.PI;
    }

    public double getVisionDistance() {
        return mDistanceEntry.getDouble(-1);
    }
    
    public double getAngle() {
        return mGyro.getAngle() % DEGREES_IN_A_CIRCLE;
    }
    
    public double getVisionAngleDeg() {
        double angle = -mOffsetEntry.getDouble(0)* (CAMERA_FOV/CAMERA_WIDTH);
        return angle < 0 ? angle  +10 : angle;
    }
    
    public double getVisionAnglePixel() {
        return (-mOffsetEntry.getDouble(0));
    }

    public void resetDistance() {
        mFrontLeft.setSelectedSensorPosition(0);
    }

    public void resetGyro() {
        mGyro.reset();
    }

    public static int findClosest(int[] arr, int target) {
        // Corner cases
        if (target <= arr[0])
            return arr[0];
        if (target >= arr[arr.length - 1])
            return arr[arr.length - 1];

        // Doing binary search
        int i = 0, j = arr.length, mid = 0;
        while (i < j) {
            mid = (i + j) / 2;

            if (arr[mid] == target)
                return arr[mid];

            /*
             * If target is less than array element, then search in left
             */
            if (target < arr[mid]) {

                // If target is greater than previous
                // to mid, return closest of two
                if (mid > 0 && target > arr[mid - 1])
                    return getClosest(arr[mid - 1], arr[mid], target);

                /* Repeat for left half */
                j = mid;
            }

            // If target is greater than mid
            else {
                if (mid < arr.length - 1 && target < arr[mid + 1])
                    return getClosest(arr[mid], arr[mid + 1], target);
                // update i
                i = mid + 1;
            }
        }

        // Only single element left after search
        return arr[mid];
    }

    // Method to compare which one is the more close.
    // We find the closest by taking the difference
    // between the target and both values. It assumes
    // that val2 is greater than val1 and target lies
    // between these two.
    private static int getClosest(int val1, int val2, int target) {
        if (target - val1 >= val2 - target)
            return val2;
        else
            return val1;
    }
}
