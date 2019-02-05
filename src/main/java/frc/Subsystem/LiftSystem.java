package frc.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.Rotatable;
import edu.wpi.first.wpilibj.DigitalInput;

public class LiftSystem extends Subsystem implements Rotatable {
    public final static double LIFT_SPEED = 0.4;
    public final static double STALL_SPEED = 0.1;
    public final static double FALL_SPEED = 0.05;

    private final DigitalInput mBottomSwitch;
    private final DigitalInput mLowSwitch;
    private final DigitalInput mHighSwitch;

    private final TalonSRX mLeftLiftMotor;
    private final TalonSRX mRightLiftMotor;

    public LiftSystem(int leftLiftMotor, int rightLiftMotor, int lowSwitch, int highSwitch, int bottomSwitch) {
        mBottomSwitch = new DigitalInput(bottomSwitch);
        mLowSwitch = new DigitalInput(lowSwitch);
        mHighSwitch = new DigitalInput(highSwitch);

        mLeftLiftMotor = new TalonSRX(leftLiftMotor);
        mRightLiftMotor = new TalonSRX(rightLiftMotor);
    }

    @Override
    public void rotate(double speed) {
        mRightLiftMotor.set(ControlMode.PercentOutput, speed);
        mLeftLiftMotor.set(ControlMode.PercentOutput, -speed);
    }

    @Override
    public void stop() {
        mRightLiftMotor.set(ControlMode.PercentOutput, 0);
        mLeftLiftMotor.set(ControlMode.PercentOutput, 0);
    }

    public boolean isLow() {
        return mLowSwitch.get();
    }

    public boolean isHigh() {
        return mHighSwitch.get();
    }

    public boolean isBottom() {
        return mBottomSwitch.get();
    }
}