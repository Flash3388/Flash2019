package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.flash3388.flashlib.flashboard.Flashboard;
import edu.flash3388.flashlib.math.Mathf;
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
    public static final int OFFSET_MARGIN = 5;

    public static final double DRIVE_LIMIT = 1.0;
    public static final double ROTATE_LIMIT = 1.0;

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
        rotatePID = new PIDController(0.1,0.0, 0.0, 0.0, rotationSetPoint, rotationSource);
        rotatePID.setOutputLimit(-ROTATE_LIMIT, ROTATE_LIMIT);

        //rotatePID.setPID(0.09, 0.1865, 0.0109);
    }

    public double getDistance() {
        return -(mFrontLeft.getSelectedSensorPosition() / PPR) * WHEEL_DIAMETER * Math.PI;
    }

    public double getVisionDistance() {
        double angle = mDistanceEntry.getDouble(-1);
        return angle;
    }
    
    public double getAngle() {
        return mGyro.getAngle() % 360;
    }
    
    public double getVisionAngleDeg() {
        return (-mOffsetEntry.getDouble(0) * (24.75/Math.sqrt(Math.pow(320, 2)+Math.pow(480,2))));
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

    public static boolean inBounds(double val, double min, double max) {
        return val > min && val < max;
    }

    public void setupDrivePIDTunner() {
        Flashboard.putPIDTuner("distance", distancePID.kpProperty(), distancePID.kiProperty(), distancePID.kdProperty(),
                distancePID.kfProperty(), distanceSetPoint, distanceSource, 20, 1000);
    }

    public void setupRotationPIDTunner() {
        Flashboard.putPIDTuner("rotation", rotatePID.kpProperty(), rotatePID.kiProperty(), rotatePID.kdProperty(),
                rotatePID.kfProperty(), rotationSetPoint, rotationSource, 20, 1000);
    }
}
