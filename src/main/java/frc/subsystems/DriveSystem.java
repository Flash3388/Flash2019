package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.TankDriveSystem;
import frc.actions.OperatorDriveAction;

public class DriveSystem extends Subsystem implements TankDriveSystem {
    private final double WHEEL_RADIUS = 10.16;

    private final TalonSRX mRearRight;
    private final TalonSRX mFrontRight;
    private final TalonSRX mRearLeft;
    private final TalonSRX mFrontLeft;

    private final TalonSRX mBackPistonMotor;

    public DriveSystem(int frontRight, int frontLeft, int rearRight, int rearLeft, int backPiston) {
        mFrontRight = new TalonSRX(frontRight);
        mRearRight = new TalonSRX(rearRight);
        mFrontLeft = new TalonSRX(frontLeft);
        mRearLeft = new TalonSRX(rearLeft);
        mBackPistonMotor = new TalonSRX(backPiston);

        this.setDefaultAction(new OperatorDriveAction());
    }

    @Override
    public void tankDrive(double right, double left) {
        mRearRight.set(ControlMode.PercentOutput, right);
        mFrontRight.set(ControlMode.PercentOutput, right);
        mFrontLeft.set(ControlMode.PercentOutput, left);
        mRearLeft.set(ControlMode.PercentOutput, left);
    }

    @Override
    public void stop() {
        tankDrive(0, 0);
    }

    public void climbDrive(double speed) {
        mBackPistonMotor.set(ControlMode.PercentOutput, speed);
    }

    public void stopClimb() {
        climbDrive(0);
    }

    public double getDistance() {
        return mRearLeft.getSelectedSensorPosition() * WHEEL_RADIUS;
    }

    public static boolean inBounds(double val, double min, double max) {
        return val <= min || val >= max;
    }
}
