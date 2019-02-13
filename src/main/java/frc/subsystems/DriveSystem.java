package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.flash3388.flashlib.flashboard.Flashboard;
import edu.flash3388.flashlib.robot.PIDController;
import edu.flash3388.flashlib.robot.PIDSource;
import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.FlashDrive;
import edu.flash3388.flashlib.robot.systems.TankDriveSystem;
import edu.flash3388.flashlib.util.beans.DoubleProperty;
import edu.flash3388.flashlib.util.beans.PropertyHandler;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import frc.actions.OperatorDriveAction;

public class DriveSystem extends Subsystem implements TankDriveSystem {
    public static final double DRIVE_LIMIT = 1.0;
    public static final double ROTATE_LIMIT = 1.0;
    
    private final double WHEEL_RADIUS = 10.16;

    public static final String DISTANCE_NAME = "distanceSetPoint";
    public static final String ROTATION_NAME = "rotationSetPoint";

    public PIDController distancePID;
    public PIDController rotatePID;
    public PIDSource distanceSource;
    public PIDSource rotationSource;

    public DoubleProperty distanceSetPoint = PropertyHandler.putNumber(DISTANCE_NAME, 0.0);
    public DoubleProperty rotationSetPoint = PropertyHandler.putNumber(ROTATION_NAME, 0.0);

    private final ADXRS450_Gyro mGyro;

    private final TalonSRX mRearRight;
    private final TalonSRX mFrontRight;
    private final TalonSRX mRearLeft;
    private final TalonSRX mFrontLeft;

    private final TalonSRX mBackPistonMotor;

    public DriveSystem(int frontRight, int rearRight, int frontLeft, int rearLeft, int backPiston) {
        mFrontRight = new TalonSRX(frontRight);
        mRearRight = new TalonSRX(rearRight);
        mFrontLeft = new TalonSRX(frontLeft);
        mRearLeft = new TalonSRX(rearLeft);
        mBackPistonMotor = new TalonSRX(backPiston);

        mFrontLeft.setInverted(true);
        mRearLeft.setInverted(true);
        
        mGyro = new ADXRS450_Gyro();
        mGyro.calibrate();

        mFrontRight.configFactoryDefault();
        mFrontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        pidsHandler();
    }

    @Override
    public void tankDrive(double right, double left) {
        mRearRight.set(ControlMode.PercentOutput, right);
        mFrontRight.set(ControlMode.PercentOutput, right);
        mFrontLeft.set(ControlMode.PercentOutput, left);
        mRearLeft.set(ControlMode.PercentOutput, left);
    }

    @Override
    public void arcadeDrive(double moveValue, double rotateValue) {
        double[] values = FlashDrive.calculate_arcadeDrive(moveValue, rotateValue);
        tankDrive(values[0], values[1]);
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
        rotatePID = new PIDController(0.308, 0.0, 0.628, 0.0, rotationSetPoint, rotationSource);
        rotatePID.setOutputLimit(-ROTATE_LIMIT, ROTATE_LIMIT);
    }

    public void drive(double speed)
	{	
		final double KP = 0.1;
		final double MARGIN = 1.0;
		//driveTrain.tankDrive(speed, speed);
		double angle = rotationSource.pidGet();
	///	if(DrivePIDAction.inThreshold)
		//	angle = 0;
		tankDrive(speed, -angle*KP);
	}


    public void climbDrive(double speed) {
        mBackPistonMotor.set(ControlMode.PercentOutput, speed);
    }

    public void stopClimb() {
        climbDrive(0);
    }

    public double getDistance() {
        return mFrontRight.getSelectedSensorPosition() * WHEEL_RADIUS;
    }
    
    public double getAngle() {
        return mGyro.getAngle();
    }

    public static boolean inBounds(double val, double min, double max) {
        return val <= min || val >= max;
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
