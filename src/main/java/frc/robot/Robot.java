package frc.robot;

import edu.flash3388.flashlib.robot.frc.IterativeFRCRobot;
import edu.wpi.first.wpilibj.Joystick;
import frc.Subsystem.LiftSystem;

public class Robot extends IterativeFRCRobot {
	public static LiftSystem liftSystem;
	public static Joystick rightStick;
	public static Joystick leftStick;

	@Override
	protected void initRobot() {
		liftSystem = new LiftSystem(5, 6, 2,4,5);
		rightStick = new Joystick(0);
		leftStick = new Joystick(1);
	}

	@Override
	protected void disabledInit() {

	}

	@Override
	protected void disabledPeriodic() {

	}

	@Override
	protected void teleopInit() {

	}

	@Override
	protected void teleopPeriodic() {

	}

	@Override
	protected void autonomousInit() {

	}

	@Override
	protected void autonomousPeriodic() {

	}
}
