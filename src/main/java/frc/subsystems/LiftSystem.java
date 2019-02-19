package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.Rotatable;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.RobotMap;

public class LiftSystem extends Subsystem implements Rotatable {
    public final static double LIFT_SPEED = 0.65;
    public final static double STALL_SPEED = 0.2;
    public final static double FALL_SPEED = 0.05;

    private final Counter mUpCounter;
    private final Counter mDownCounter;

    private final VictorSPX mLeftLiftMotor;
    private final VictorSPX mRightLiftMotor;

    public LiftSystem(int leftLiftMotor, int rightLiftMotor, int downSwitch, int upSwitch) {
        mLeftLiftMotor = new VictorSPX(leftLiftMotor);
        mRightLiftMotor = new VictorSPX(rightLiftMotor);
        mLeftLiftMotor.setInverted(true);

        
        mUpCounter = new Counter(new DigitalInput(downSwitch));
        mDownCounter = new Counter(new DigitalInput(upSwitch));
    }

    public boolean isDown() {
        return mDownCounter.get() > 0;
    }

    public void initDownCounter() {
        mDownCounter.reset();
    }

    public boolean isUp() {
        return mUpCounter.get() > 0;
    }

    public void initUpCounter() {
        mUpCounter.reset();
    }

    public void fall() {
        if(!isDown())
            rotate(RobotMap.FALL_SPEED);
        else
            stop();
    }

    public void stall() {
        if(!isDown())
            rotate(RobotMap.STALL_SPEED);
        else
            stop();
    }

    public void lift() {
        if(!isUp())
            rotate(RobotMap.LIFT_SPEED);
        else
            stall();
    }

    @Override
    public void rotate(double speed) {
        mRightLiftMotor.set(ControlMode.PercentOutput, speed);
        mLeftLiftMotor.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public void stop() {
        rotate(0);
    }
}