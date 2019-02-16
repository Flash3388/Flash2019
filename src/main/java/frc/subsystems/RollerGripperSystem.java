package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.Rotatable;

public class RollerGripperSystem extends Subsystem implements Rotatable {
	public static final double CAPTURE_SPEED = 1.0;
	public static final double RELEASE_SPEED = -CAPTURE_SPEED;

	private TalonSRX mGripperMotor;

	public RollerGripperSystem(int gripperMotor) {
		mGripperMotor = new TalonSRX(gripperMotor);
	}

	public void capture() {
		rotate(CAPTURE_SPEED);
	}

  public void release() {
  	rotate(RELEASE_SPEED);
  }

	public void stop() {
		rotate(0);
	}

	@Override
	public void rotate(double speed) {
		mGripperMotor.set(ControlMode.PercentOutput, speed);
	}
}
