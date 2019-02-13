package frc.robot;

import edu.flash3388.flashlib.FRCHIDInterface;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.RobotFactory;
import edu.flash3388.flashlib.robot.frc.IterativeFRCRobot;
import edu.flash3388.flashlib.robot.hid.Joystick;
import edu.flash3388.flashlib.robot.hid.XboxController;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import frc.subsystems.DriveSystem;
import frc.subsystems.LiftSystem;
import frc.actions.OperatorDriveAction;
import frc.subsystems.ClimbingSystem;
import frc.subsystems.RollerGripper;
import frc.subsystems.HatchSystem;

public class Robot extends IterativeFRCRobot {
	private Compressor mCompressor;

	public static DriveSystem driveTrain;
	public static LiftSystem liftSystem;
	public static ClimbingSystem climbingSystem;
	public static RollerGripper rollerGripper;
	public static HatchSystem hatchSystem;

	public static Joystick controller;
	// public static Joystick rightStick;
	// public static Joystick leftStick;

	@Override
	protected void preInit(RobotInitializer initializer) {
		initializer.initFlashboard = false;
	}

	@Override
	protected void initRobot() {
		RobotFactory.setHIDInterface(new FRCHIDInterface(DriverStation.getInstance()));
		mCompressor = new Compressor();
		mCompressor.stop();

		driveTrain = new DriveSystem(2,6, 3,4, 5);
		driveTrain.setDefaultAction(new OperatorDriveAction());
		// liftSystem = new LiftSystem(5, 6, 2,4,5);
		climbingSystem = new ClimbingSystem(2,7 ,6,0 , 5, 1);
		controller = new Joystick(0, 2);
		controller.getButton(1).whileHeld(new Action(){
		
			@Override
			protected void execute() {
				driveTrain.tankDrive(0.4, -0.4);
			}
		
			@Override
			protected void end() {
				driveTrain.stop();
			}
		});
		controller.getButton(2).whileHeld(new Action(){
		
			@Override
			protected void execute() {
				driveTrain.tankDrive(0.6, -0.6	);
			}
		
			@Override
			protected void end() {
				driveTrain.stop();
			}
		});

		// rollerGripper = new RollerGripper(7);
		// hatchSystem = new HatchSystem(0, 1);
		//rightStick = new Joystick(2, 5);
		//leftStick = new Joystick(1, 5);
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
