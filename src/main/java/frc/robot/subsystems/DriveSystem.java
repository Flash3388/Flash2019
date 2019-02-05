package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.TankDriveSystem;
import frc.robot.actions.OperatorDriveAction;

public class DriveSystem extends Subsystem implements TankDriveSystem {
    private final double WHEEL_RADIUS = 10.16;

    private final TalonSRX mRearRight;
    private final TalonSRX mFrontRight;
    private final TalonSRX mRearLeft;
    private final TalonSRX mFrontLeft;

    public DriveSystem(int frontRight, int frontLeft, int rearRight, int rearLeft) {
        mFrontRight = new TalonSRX(frontRight);
        mRearRight = new TalonSRX(rearRight);
        mFrontLeft = new TalonSRX(frontLeft);
        mRearLeft = new TalonSRX(rearLeft);

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
        mRearRight.set(ControlMode.PercentOutput, 0);
        mFrontRight.set(ControlMode.PercentOutput, 0);
        mFrontLeft.set(ControlMode.PercentOutput, 0);
        mRearLeft.set(ControlMode.PercentOutput, 0);
    }

    public double getDistance() {
        return mRearLeft.getSelectedSensorPosition() * WHEEL_RADIUS;
    }
}
