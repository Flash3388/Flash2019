package frc.robot;

import edu.flash3388.flashlib.FRCHIDInterface;
import edu.flash3388.flashlib.robot.RobotFactory;
import edu.flash3388.flashlib.robot.frc.IterativeFRCRobot;
import edu.flash3388.flashlib.robot.hid.Joystick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import frc.subsystems.DriveSystem;
import frc.subsystems.LiftSystem;
import frc.subsystems.ClimbingSystem;
import frc.subsystems.RollerGripper;
import frc.subsystems.HatchSystem;

public class Robot extends IterativeFRCRobot {
	public static DriveSystem driveTrain;
	public static LiftSystem liftSystem;
	public static ClimbingSystem climbingSystem;
	public static RollerGripper rollerGripper;
	public static HatchSystem hatchSystem;

	public static Joystick rightStick;
	public static Joystick leftStick;

	@Override
	protected void preInit(RobotInitializer initializer) {
		initializer.initFlashboard = false;
	}

	@Override
	protected void initRobot() {
		RobotFactory.setHIDInterface(new FRCHIDInterface(DriverStation.getInstance()));
		driveTrain = new DriveSystem(0, 1, 2, 3,4);
		liftSystem = new LiftSystem(5, 6, 2,4,5);
		climbingSystem = new ClimbingSystem(3, 4, 5, 6);
		rollerGripper = new RollerGripper(7);
		hatchSystem = new HatchSystem(0, 1);
		rightStick = new Joystick(2, 5);
		leftStick = new Joystick(1, 5);
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
