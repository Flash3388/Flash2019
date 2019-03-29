package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.Rotatable;

import frc.actions.GripperStop;
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

	public void startSlowStop() {
	    new GripperStop(RobotMap.GRIPPER_STOP_SPEED_DECLINE, RobotMap.GRIPPER_STOP_SPEED_MARGIN).start();
    }

	@Override
	public void rotate(double speed) {
		mGripperMotor.set(ControlMode.PercentOutput, speed);
	}

    public double getCurrentSpeed() {
        return mGripperMotor.getMotorOutputPercent();
    }
}
