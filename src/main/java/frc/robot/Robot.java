package frc.robot;

import java.util.ArrayList;
import java.util.List;

import edu.flash3388.flashlib.FRCHIDInterface;
import edu.flash3388.flashlib.robot.InstantAction;
import edu.flash3388.flashlib.robot.RobotFactory;
import edu.flash3388.flashlib.robot.frc.IterativeFRCRobot;
import edu.flash3388.flashlib.robot.hid.Joystick;
import edu.flash3388.flashlib.robot.hid.XboxController;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;

import frc.actions.CloseBack;
import frc.actions.CloseFront;
import frc.subsystems.DriveSystem;
import frc.subsystems.HatchSystem;
import frc.subsystems.LiftSystem;
import frc.subsystems.RollerGripperSystem;
import frc.subsystems.ClimbSystem;

import frc.actions.ClimbAction;
import frc.actions.EdwardAction;
import frc.actions.ManualGripperAction;
import frc.actions.ManualLiftAction;
import frc.actions.OperatorDriveAction;
import frc.actions.SmartDriveToTarget;
import frc.actions.TargetSelectAction;

import frc.tables.TargetData;
import frc.tables.TargetDataListener;
import frc.tables.TargetDataTable;
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
		righJoystick = new Joystick(1, 5);
		lefJoystick = new Joystick(2, 5);
		
		setupSystems();
		setupTables();
		setupButtons();
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
				RobotMap.BACK_CHANNEL_FORWARD, RobotMap.BACK_CHANNEL_BACKWARD, RobotMap.BACK_MOTOR,
				RobotMap.FRONT_RIGHT_UP_SWITCH, RobotMap.FRONT_LEFT_UP_SWITCH, RobotMap.BACK_UP_SWITCH);

		hatchSystem = new HatchSystem(RobotMap.HATCH_GRIPPER_CHANNEL_FORWARD, RobotMap.HATCH_GRIPPER_CHANNEL_BACKWARD);

		rollerGripperSystem = new RollerGripperSystem(RobotMap.ROLLER_GRIPPER_MOTOR);
		rollerGripperSystem.setDefaultAction(new ManualGripperAction());

		liftSystem = new LiftSystem(RobotMap.LEFT_LIFT_MOTOR, RobotMap.RIGHT_LIFT_MOTOR, RobotMap.DOWN_SWITCH, RobotMap.UP_SWITCH);
		liftSystem.setDefaultAction(new ManualLiftAction());
	}
	
	private void setupButtons() {
		xbox.Y.whenPressed(new EdwardAction());
		xbox.Start.whenPressed(new ClimbAction());

		xbox.DPad.getDown().whenPressed(new CloseFront());
		xbox.DPad.getUp().whenPressed(new CloseBack());

		xbox.Back.whenPressed(new InstantAction() {
            @Override
            protected void execute() {
				driveSystem.cancelCurrentAction();
				climbSystem.cancelCurrentAction();
				rollerGripperSystem.cancelCurrentAction();
				hatchSystem.cancelCurrentAction();
				liftSystem.cancelCurrentAction();
            }
        });

		righJoystick.getButton(1).whenPressed(new InstantAction() {
            boolean isOn = false;

		    @Override
            protected void execute() {
                if (isOn) {
                    mCompressor.stop();
                } else {
                    mCompressor.start();
                }

                isOn = !isOn;
            }
        });
	}
}
