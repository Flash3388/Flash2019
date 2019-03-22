package frc.robot;

import edu.flash3388.flashlib.FRCHIDInterface;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.InstantAction;
import edu.flash3388.flashlib.robot.RobotFactory;
import edu.flash3388.flashlib.robot.frc.IterativeFRCRobot;
import edu.flash3388.flashlib.robot.hid.Joystick;
import edu.flash3388.flashlib.robot.hid.XboxController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import frc.actions.CancelAllCurrentRunningActionsAction;
import frc.actions.ClimbDriveAction;
import frc.actions.ComplexActions;
import frc.subsystems.DriveSystem;
import frc.subsystems.HatchSystem;
import frc.subsystems.LiftSystem;
import frc.subsystems.RollerGripperSystem;
import frc.subsystems.ClimbSystem;

import frc.actions.EdwardAction;
import frc.actions.ManualGripperAction;
import frc.actions.ManualLiftAction;
import frc.actions.OperatorDriveAction;
import frc.time.Clock;
import frc.time.FpgaClock;
import frc.time.ntp.NtpServer;

public class Robot extends IterativeFRCRobot {
	public static DriveSystem driveSystem;
	public static ClimbSystem climbSystem;
	public static HatchSystem hatchSystem;
	public static LiftSystem liftSystem;
	public static RollerGripperSystem rollerGripperSystem;

	public static XboxController xbox;

	public static Joystick righJoystick;
	public static Joystick lefJoystick;

	public static SuffleboardHandler pidHandler;

	public static Clock clock;

	private NtpServer ntpServer;

    @Override
	protected void preInit(RobotInitializer initializer) {
		initializer.initFlashboard = false;
	}

	@Override
	protected void initRobot() {
		RobotFactory.setHIDInterface(new FRCHIDInterface(DriverStation.getInstance()));

        clock = new FpgaClock();

        NetworkTable ntpTable = NetworkTableInstance.getDefault().getTable("ntp");
        ntpServer = new NtpServer(
                ntpTable.getEntry("client"),
                ntpTable.getEntry("serverRec"),
                ntpTable.getEntry("serverSend"),
                clock);

		xbox = new XboxController(0);
		righJoystick = new Joystick(1, 5);
		lefJoystick = new Joystick(2, 5);
		
		setupSystems();
		setupButtons();

		Compressor c = new Compressor();
		c.stop();
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
	
	private void setupSystems() {
		driveSystem = new DriveSystem(RobotMap.FRONT_RIGHT_MOTOR, RobotMap.REAR_RIGHT_MOTOR, RobotMap.FRONT_LEFT_MOTOR,
				RobotMap.REAR_LEFT_MOTOR);
		driveSystem.setDefaultAction(new OperatorDriveAction());
		driveSystem.resetDistance();

		climbSystem = new ClimbSystem(RobotMap.FRONT_RIGHT_CHANNEL_FORWARD, RobotMap.FRONT_RIGHT_CHANNEL_BACKWARD,
				RobotMap.FRONT_LEFT_CHANNEL_FORWARD, RobotMap.FRONT_LEFT_CHANNEL_BACKWARD,
				RobotMap.BACK_CHANNEL_FORWARD, RobotMap.BACK_CHANNEL_BACKWARD, RobotMap.BACK_MOTOR,
				RobotMap.FRONT_RIGHT_UP_SWITCH, RobotMap.FRONT_LEFT_UP_SWITCH, RobotMap.BACK_UP_SWITCH,
				RobotMap.CLIMB_SWITCH, RobotMap.DRIVE_SWITCH);

		hatchSystem = new HatchSystem(RobotMap.HATCH_GRIPPER_CHANNEL_FORWARD, RobotMap.HATCH_GRIPPER_CHANNEL_BACKWARD);

		rollerGripperSystem = new RollerGripperSystem(RobotMap.ROLLER_GRIPPER_MOTOR);
		rollerGripperSystem.setDefaultAction(new ManualGripperAction());

		liftSystem = new LiftSystem(RobotMap.LEFT_LIFT_MOTOR, RobotMap.RIGHT_LIFT_MOTOR, RobotMap.DOWN_SWITCH, RobotMap.UP_SWITCH);
		liftSystem.setDefaultAction(new ManualLiftAction());
	}
	
	private void setupButtons() {
		righJoystick.getButton(1).whenPressed(new EdwardAction());

		Action climbAction = ComplexActions.climbDriveAction();
		xbox.Start.whenPressed(climbAction);

		xbox.DPad.getUp().whenPressed(new ClimbDriveAction());

		Action autonomousClimb = ComplexActions.autonomousClimbAction();

		lefJoystick.getButton(2).whenPressed(new InstantAction(){
		
			@Override
			protected void execute() {
				Robot.climbSystem.closeBack();
				Robot.climbSystem.closeFront();
			}
		});

		righJoystick.getButton(2).whenPressed(new CancelAllCurrentRunningActionsAction(climbAction, autonomousClimb));
		lefJoystick.getButton(1).whenPressed(ComplexActions.hatchDrive());
		
	}
}
