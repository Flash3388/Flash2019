package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.flash3388.flashlib.robot.PIDController;
import edu.flash3388.flashlib.robot.PIDSource;
import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.FlashDrive;
import edu.flash3388.flashlib.robot.systems.TankDriveSystem;
import edu.flash3388.flashlib.util.beans.DoubleProperty;
import edu.flash3388.flashlib.util.beans.PropertyHandler;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import frc.robot.NetworkPIDTunner;
import frc.robot.RobotMap;

public class DriveSystem extends Subsystem implements TankDriveSystem {
    private final static double DISTANCE_MULTIPLY_VALUE = RobotMap.REVERSE_PPR*RobotMap.WHEEL_DIAMETER*Math.PI;

    private static final String DISTANCE_NAME = "distanceSetPoint";
    private static final String ROTATION_NAME = "rotationSetPoint";

    public PIDController distancePID;
    public PIDController rotatePID;
    
    public DoubleProperty distanceSetPoint = PropertyHandler.putNumber(DISTANCE_NAME, 0.0);
    public DoubleProperty rotationSetPoint = PropertyHandler.putNumber(ROTATION_NAME, 0.0);

    private final ADXRS450_Gyro mGyro;

    private final TalonSRX mRearRight;
    private final TalonSRX mFrontRight;
    private final TalonSRX mRearLeft;
    private final TalonSRX mFrontLeft;

    public DriveSystem(int frontRight, int rearRight, int frontLeft, int rearLeft) {
        mFrontRight = new TalonSRX(frontRight);
        mRearRight = new TalonSRX(rearRight);
        mFrontLeft = new TalonSRX(frontLeft);
        mRearLeft = new TalonSRX(rearLeft);

        mFrontLeft.setInverted(true);
        mRearLeft.setInverted(true);
        
        mGyro = new ADXRS450_Gyro();
        mGyro.calibrate();

        mRearRight.configFactoryDefault();
        mRearRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        mFrontLeft.configFactoryDefault();
        mFrontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        pidsHandler();
    }

    @Override
    public void arcadeDrive(double moveValue, double rotateValue) {
        double[] values = FlashDrive.calculate_arcadeDrive(moveValue, rotateValue);
        tankDrive(values[0], values[1]);
    }

    @Override
    public void tankDrive(double right, double left) {
        mRearRight.set(ControlMode.PercentOutput,right);
        mFrontRight.set(ControlMode.PercentOutput,right);
        mFrontLeft.set(ControlMode.PercentOutput, left);
        mRearLeft.set(ControlMode.PercentOutput,left);
    }

    @Override
    public void stop() {
        tankDrive(0, 0);
    }

    private void pidsHandler() {
        PIDSource distancSource = new PIDSource() {

            @Override
            public double pidGet() {
                return getRightDistance();
            }
        };
        PIDSource rotationSource = new PIDSource() {

            @Override
            public double pidGet() {
                return getAngle();
            }
        };

        distancePID = new PIDController(0.04, 0.0, 0.0, 0.0, distanceSetPoint, distancSource);
        distancePID.setOutputLimit(-RobotMap.DRIVE_LIMIT, RobotMap.DRIVE_LIMIT);
        rotatePID = new PIDController(0.04, 0.0, 0.0, 0.0, rotationSetPoint, rotationSource);
        rotatePID.setOutputLimit(-RobotMap.ROTATE_LIMIT, RobotMap.ROTATE_LIMIT);
    }

    public void setDistancePIDSource(double angle) {
        distancePID.setPIDSource(angle>0 ? new PIDSource(){
        
            @Override
            public double pidGet() {
                return getRightDistance();
            }
        } : new PIDSource(){
        
            @Override
            public double pidGet() {
                return getLeftDistance();
            }
        });
    } 
    
    public double getLeftDistance() {
        return mFrontLeft.getSelectedSensorPosition() * DISTANCE_MULTIPLY_VALUE;
    }

    public double getRightDistance() {
        return mRearRight.getSelectedSensorPosition() * DISTANCE_MULTIPLY_VALUE;
    }
    
    public double getAngle() {
        return mGyro.getAngle() % RobotMap.DEGREES_IN_A_CIRCLE;
    }

    public void resetDistance() {
        mRearRight.setSelectedSensorPosition(0);
        mFrontLeft.setSelectedSensorPosition(0);
    }

    public void resetGyro() {
       mGyro.reset();
    }

    public static int findClosest(int[] arr, int target) {
        if (target <= arr[0])
            return arr[0];
        if (target >= arr[arr.length - 1])
            return arr[arr.length - 1];

        int i = 0, j = arr.length, mid = 0;
        while (i < j) {
            mid = (i + j) / 2;

            if (arr[mid] == target)
                return arr[mid];
            if (target < arr[mid]) {
                if (mid > 0 && target > arr[mid - 1])
                    return getClosest(arr[mid - 1], arr[mid], target);
                j = mid;
            } else {
                if (mid < arr.length - 1 && target < arr[mid + 1])
                    return getClosest(arr[mid], arr[mid + 1], target);
                i = mid + 1;
            }
        }

        return arr[mid];
    }

    private static int getClosest(int val1, int val2, int target) {
        if (target - val1 >= val2 - target)
            return val2;
        else
            return val1;
    }
}
