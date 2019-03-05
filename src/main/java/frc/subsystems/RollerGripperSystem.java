package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.Rotatable;

import frc.robot.RobotMap;

public class RollerGripperSystem extends Subsystem implements Rotatable {

    private TalonSRX mGripperMotor;

    public RollerGripperSystem(int gripperMotor) {
        mGripperMotor = new TalonSRX(gripperMotor);
    }

    public void capture() {
        rotate(RobotMap.CAPTURE_SPEED);
    }

    public void release() {
        rotate(RobotMap.RELEASE_SPEED);
    }

    public void stop() {
        rotate(0);
    }

    @Override
    public void rotate(double speed) {
        mGripperMotor.set(ControlMode.PercentOutput, speed);
    }
}
