package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.Rotatable;

import edu.wpi.first.wpilibj.DigitalInput;

import frc.robot.RobotMap;

public class LiftSystem extends Subsystem implements Rotatable {
    private final DigitalInput mDownSwitch;
    private final DigitalInput mUpSwitch;

    private final VictorSPX mLeftLiftMotor;
    private final VictorSPX mRightLiftMotor;

    public LiftSystem(int leftLiftMotor, int rightLiftMotor, int downSwitch, int upSwitch) {
        mLeftLiftMotor = new VictorSPX(leftLiftMotor);
        mRightLiftMotor = new VictorSPX(rightLiftMotor);
        mLeftLiftMotor.follow(mRightLiftMotor,FollowerType.PercentOutput);
        
        mDownSwitch = new DigitalInput(downSwitch);
        mUpSwitch = new DigitalInput(upSwitch);
    }

    public boolean isDown() {
        return !mDownSwitch.get();
    }

    public boolean isUp() {
        return !mUpSwitch.get();
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
    }

    public void rotate(ControlMode mode, double speed) {
        mRightLiftMotor.set(mode, speed);
    }

    @Override
    public void stop() {
        rotate(0);
    }
}