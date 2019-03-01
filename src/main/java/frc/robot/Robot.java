package frc.robot;

import java.util.ArrayList;
import java.util.List;

import edu.flash3388.flashlib.FRCHIDInterface;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.ActionGroup;
import edu.flash3388.flashlib.robot.InstantAction;
import edu.flash3388.flashlib.robot.RobotFactory;
import edu.flash3388.flashlib.robot.frc.IterativeFRCRobot;
import edu.flash3388.flashlib.robot.hid.Joystick;
import edu.flash3388.flashlib.robot.hid.XboxController;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.subsystems.DriveSystem;
import frc.subsystems.HatchSystem;
import frc.subsystems.LiftSystem;
import frc.subsystems.RollerGripperSystem;
import frc.subsystems.ClimbSystem;

import frc.actions.CaptureAction;
import frc.actions.ClimbAction;
import frc.actions.ClimbDriveAction;
import frc.actions.DrivePIDAction;
import frc.actions.EdwardAction;
import frc.actions.ManualLiftAction;
import frc.actions.OperatorDriveAction;
import frc.actions.ReleaseAction;
import frc.actions.SimpleManualLiftAction;
import frc.actions.SmartDriveToTarget;
import frc.actions.TargetSelectAction;

import frc.tables.TargetData;
import frc.tables.TargetDataListener;
import frc.tables.TargetDataTable;
import frc.tables.TargetSelectListener;
import frc.tables.TargetSelectTable;

public class Robot extends IterativeFRCRobot implements TargetDataListener {
	private Compressor mCompressor;

	public static DriveSystem driveSystem;
	public static ClimbSystem climbSystem;
	public static HatchSystem hatchSystem;
	public static LiftSystem liftSystem;
	public static RollerGripperSystem rollerGripperSystem;

	public static XboxController xbox;

	public static Joystick righJoystick;
	public static Joystick lefJoystick;

	public static SuffleboardHandler pidHandler;

	private List<TargetSelectAction> mTargetSelectActionList;
	private TargetSelectTable mTargetSelectTable;
	private TargetDataTable mTargetDataTable;

	private DigitalInput dio0;
	private DigitalInput dio6;
	private DigitalInput dio1;
	private DigitalInput dio4;
	private DigitalInput dio5;

	@Override
	protected void preInit(RobotInitializer initializer) {
		initializer.initFlashboard = false;
	}

	@Override
	protected void initRobot() {
		RobotFactory.setHIDInterface(new FRCHIDInterface(DriverStation.getInstance()));

		mCompressor = new Compressor(0);
		mCompressor.stop();
		
		xbox = new XboxController(0);
		// righJoystick = new Joystick(1, 5);
		// lefJoystick = new Joystick(2, 5);
		
		setupSystems();
		setupTables();
		setupButtons();

		dio1 = new DigitalInput(1);
		dio4 = new DigitalInput(4);
		dio5 = new DigitalInput(5);

		dio0 = new DigitalInput(0);
		dio6 = new DigitalInput(6);
	}

	@Override
	protected void disabledInit() {

	}

	@Override
	protected void disabledPeriodic() {

	}

	@Override
	protected void teleopInit() {
		//driveSystem.resetDistance();
	}

	@Override
	protected void teleopPeriodic() {
		System.out.println(("dio1: " +dio1.get()+ " dio4: " + dio4.get() + " dio5: " + dio5.get()+" dio6: "+dio6.get()+" dio0: "+dio0.get())); 
	}

	@Override
	protected void autonomousInit() {

	}

	@Override
	protected void autonomousPeriodic() {

	}

	@Override
	public void onTargetData(TargetData targetData) {
		new SmartDriveToTarget(0.5, 1000, targetData.getDistance(), targetData.getAngle()).start();;
	}

	private void setupTables() {
		mTargetSelectActionList = new ArrayList<>();
		mTargetSelectTable = new TargetSelectTable();
		mTargetDataTable = new TargetDataTable();

		for (int i = 0; i < TargetSelectTable.NUM_OF_POSSIBLE_TARGETS; i++) {
			mTargetSelectActionList.add(new TargetSelectAction(mTargetSelectTable, i));
		}
		xbox.A.whenPressed(mTargetSelectActionList.get(0));
		xbox.B.whenPressed(mTargetSelectActionList.get(1));
		xbox.X.whenPressed(mTargetSelectActionList.get(2));

		mTargetDataTable.registerTargetDataListener(this);

	}
	
	private void setupSystems() {
		driveSystem = new DriveSystem(RobotMap.FRONT_RIGHT_MOTOR, RobotMap.REAR_RIGHT_MOTOR, RobotMap.FRONT_LEFT_MOTOR,
				RobotMap.REAR_LEFT_MOTOR);
		driveSystem.setDefaultAction(new OperatorDriveAction());

		climbSystem = new ClimbSystem(RobotMap.FRONT_RIGHT_CHANNEL_FORWARD, RobotMap.FRONT_RIGHT_CHANNEL_BACKWARD,
				RobotMap.FRONT_LEFT_CHANNEL_FORWARD, RobotMap.FRONT_LEFT_CHANNEL_BACKWARD,
				RobotMap.BACK_CHANNEL_FORWARD, RobotMap.BACK_CHANNEL_BACKWARD, RobotMap.BACK_MOTOR);
		climbSystem.setDefaultAction(new ClimbDriveAction());

		hatchSystem = new HatchSystem(RobotMap.HATCH_GRIPPER_CHANNEL_FORWARD, RobotMap.HATCH_GRIPPER_CHANNEL_BACKWARD);

		rollerGripperSystem = new RollerGripperSystem(RobotMap.ROLLER_GRIPPER_MOTOR);

		liftSystem = new LiftSystem(RobotMap.LEFT_LIFT_MOTOR, RobotMap.RIGHT_LIFT_MOTOR, RobotMap.DOWN_SWITCH, RobotMap.UP_SWITCH);
		liftSystem.setDefaultAction(new ManualLiftAction());
	}
	
	private void setupButtons() {
		xbox.Y.whenPressed(new EdwardAction());
		xbox.RB.whileHeld(new CaptureAction());
		xbox.LB.whileHeld(new ReleaseAction());
		xbox.Start.whenPressed(new ClimbAction());
		xbox.Back.whenPressed(new InstantAction(){
		
			@Override
			protected void execute() {
				climbSystem.closeBack();
				climbSystem.closeFront();
			}
		});
		xbox.DPad.Down.whenPressed(new InstantAction(){
		
			@Override
			protected void execute() {
				climbSystem.switchBack();
			}
		});

		xbox.DPad.Left.whenPressed(new InstantAction() {

			@Override
			protected void execute() {
				climbSystem.switchLeftFront();
			}
		});

		xbox.DPad.Right.whenPressed(new InstantAction() {

			@Override
			protected void execute() {
				climbSystem.switchRightFront();
			}
		});
	}
}
