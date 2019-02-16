package frc.robot;

import java.util.ArrayList;
import java.util.List;

import edu.flash3388.flashlib.FRCHIDInterface;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.InstantAction;
import edu.flash3388.flashlib.robot.RobotFactory;
import edu.flash3388.flashlib.robot.SystemAction;
import edu.flash3388.flashlib.robot.frc.IterativeFRCRobot;
import edu.flash3388.flashlib.robot.hid.Joystick;
import edu.flash3388.flashlib.robot.hid.XboxController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;

import frc.subsystems.DriveSystem;
import frc.subsystems.HatchSystem;
import frc.subsystems.LiftSystem;
import frc.subsystems.RollerGripperSystem;
import frc.actions.OperatorDriveAction;
import frc.actions.TargetSelectAction;
import frc.subsystems.ClimbingSystem;
import frc.tables.TargetData;
import frc.tables.TargetDataListener;
import frc.tables.TargetDataTable;
import frc.tables.TargetSelectTable;

public class Robot extends IterativeFRCRobot implements TargetDataListener {
	private Compressor mCompressor;

	public static DriveSystem driveTrain;
	public static ClimbingSystem climbingSystem;
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


	@Override
	protected void preInit(RobotInitializer initializer) {
		initializer.initFlashboard = false;
	}

	@Override
	protected void initRobot() {
		RobotFactory.setHIDInterface(new FRCHIDInterface(DriverStation.getInstance()));

		mCompressor = new Compressor();
		mCompressor.stop();
		
		pidHandler = new SuffleboardHandler(NetworkTableInstance.getDefault().getTable("change me"));
		xbox = new XboxController(0);
		righJoystick = new Joystick(1, 5);
		lefJoystick = new Joystick(2, 5);
		
		setupSystems();
		setupTables();

		xbox.Start.whenPressed(new InstantAction() {

			@Override
			protected void execute() {
				climbingSystem.openFront();
				climbingSystem.openBack();
			}
		});
		xbox.Back.whenPressed(new InstantAction() {

			@Override
			protected void execute() {
				climbingSystem.closeFront();
				climbingSystem.closeBack();
			}
		});
	}

	@Override
	protected void disabledInit() {

	}

	@Override
	protected void disabledPeriodic() {

	}

	@Override
	protected void teleopInit() {
		driveTrain.resetDistance();
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

	@Override
	public void onTargetData(TargetData targetData) {

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
		xbox.Y.whenPressed(mTargetSelectActionList.get(3));

		mTargetDataTable.registerTargetDataListener(this);

	}
	
	private void setupSystems() {
		driveTrain = new DriveSystem(RobotMap.FRONT_RIGHT_MOTOR, RobotMap.REAR_RIGHT_MOTOR, 
				RobotMap.FRONT_LEFT_MOTOR,RobotMap.REAR_LEFT_MOTOR);
		driveTrain.setDefaultAction(new OperatorDriveAction());

		climbingSystem = new ClimbingSystem(
				RobotMap.FRONT_RIGHT_CHANNEL_FORWARD, RobotMap.FRONT_RIGHT_CHANNEL_BACKWARD,
				RobotMap.FRONT_LEFT_CHANNEL_FORWARD, RobotMap.FRONT_LEFT_CHANNEL_BACKWARD,
				RobotMap.BACK_CHANNEL_FORWARD, RobotMap.BACK_CHANNEL_BACKWARD,
				RobotMap.BACK_MOTOR);
		
		hatchSystem = new HatchSystem(RobotMap.HATCH_GRIPPER_CHANNEL_FORWARD, RobotMap.HATCH_GRIPPER_CHANNEL_BACKWARD);
		rollerGripperSystem = new RollerGripperSystem(7);
		liftSystem = new LiftSystem(8, 9);
		righJoystick.getButton(2).whileHeld(new Action(){
		
			@Override
			protected void execute() {
				rollerGripperSystem.rotate(-0.65);
			}
		
			@Override
			protected void end() {
				rollerGripperSystem.stop();
			}
		});
		righJoystick.getButton(1).whileHeld(new Action(){
		
			@Override
			protected void execute() {
				rollerGripperSystem.rotate(0.8);
			}
		
			@Override
			protected void end() {
				rollerGripperSystem.stop();
			}
		});
		// liftSystem.setDefaultAction(new SystemAction(new Action(){
		
		// 	@Override
		// 	protected void execute() {
		// 		double speed = xbox.RT.get() * 0.8;
		// 		liftSystem.rotate(speed);//0.6 up moderate up and 0.4 stall
		// 		System.out.println(speed);
		// 	}
		
		// 	@Override
		// 	protected void end() {
		// 		liftSystem.stop();
		// 	}
		// }, liftSystem));

	}
}
