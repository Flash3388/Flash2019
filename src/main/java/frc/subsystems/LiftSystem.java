package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.Rotatable;
import edu.wpi.first.wpilibj.DigitalInput;

public class LiftSystem extends Subsystem implements Rotatable {
    public final static double LIFT_SPEED = 0.4;
    public final static double STALL_SPEED = 0.1;
    public final static double FALL_SPEED = 0.05;

    // private final DigitalInput mBottomSwitch;
    // private final DigitalInput mLowSwitch;
    // private final DigitalInput mHighSwitch;

    private final VictorSPX mLeftLiftMotor;
    private final VictorSPX mRightLiftMotor;

    public LiftSystem(int leftLiftMotor, int rightLiftMotor) {
        mLeftLiftMotor = new VictorSPX(leftLiftMotor);
        mRightLiftMotor = new VictorSPX(rightLiftMotor);
        mLeftLiftMotor.setInverted(true);
    }

    @Override
    public void rotate(double speed) {
        mRightLiftMotor.set(ControlMode.PercentOutput, speed);
        mLeftLiftMotor.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public void stop() {
        mRightLiftMotor.set(ControlMode.PercentOutput, 0);
        mLeftLiftMotor.set(ControlMode.PercentOutput, 0);
    }

    // public boolean isLow() {
    //     return mLowSwitch.get();
    // }

    // public boolean isHigh() {
    //     return mHighSwitch.get();
    // }

    // public boolean isBottom() {
    //     return mBottomSwitch.get();
    // }
}