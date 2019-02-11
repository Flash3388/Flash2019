package frc.robot;

import edu.flash3388.flashlib.FRCHIDInterface;
import edu.flash3388.flashlib.flashboard.Flashboard;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.InstantAction;
import edu.flash3388.flashlib.robot.RobotFactory;
import edu.flash3388.flashlib.robot.frc.IterativeFRCRobot;
import edu.flash3388.flashlib.robot.hid.XboxController;
import edu.flash3388.flashlib.util.beans.DoubleProperty;
import edu.flash3388.flashlib.util.beans.SimpleDoubleProperty;
import edu.flash3388.flashlib.util.beans.SimpleProperty;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.TableListener;
import edu.wpi.first.wpilibj.DriverStation;
import frc.actions.DrivePIDAction;
import frc.actions.OperatorDriveAction;
import frc.actions.SimpleDriveToTarget;
import frc.actions.SmartDriveToTarget;
import frc.actions.VisionRotatePIDAction;
import frc.subsystems.DriveSystem;

public class Robot extends IterativeFRCRobot {
	public static DriveSystem driveTrain;

	public static XboxController xbox;

	private NetworkTable mTable;
	private NetworkTableEntry mAngleEntry;
	public static NetworkTableEntry mCurveEntry;
	public static SuffleboardHandler pidHandler;

	// @Override
	// protected void preInit(RobotInitializer initializer) {
	// 	initializer.initFlashboard = false;
	// }

	
	DoubleProperty marginProperty = new SimpleDoubleProperty(1.0);

	@Override
	protected void initRobot() {
		Flashboard.start();
		RobotFactory.setHIDInterface(new FRCHIDInterface(DriverStation.getInstance()));
		mTable = NetworkTableInstance.getDefault().getTable("analysis");
		pidHandler = new SuffleboardHandler(NetworkTableInstance.getDefault().getTable("change me"));
		driveTrain = new DriveSystem(10, 1, 3, 0, mTable);
		driveTrain.setDefaultAction(new OperatorDriveAction());

		xbox = new XboxController(1);
		xbox.A.whenPressed(new SmartDriveToTarget(0.5, 500));
		xbox.B.whenPressed(new InstantAction(){
		
			@Override
			protected void execute() {
				driveTrain.resetDistance();
			}
		});
		mAngleEntry = mTable.getEntry("Angle");
		mCurveEntry = mTable.getEntry("Curve");
		mCurveEntry.setDouble(1);
		mAngleEntry.setDouble(0.0);
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
	//	System.out.println("VISION : "+driveTrain.getVisionAngle()+" Actual "+ driveTrain.getAngle());
		//	System.out.println(driveTrain.getDistance());
		System.out.println(driveTrain.getVisionAngleDeg());
	}

	@Override
	protected void autonomousInit() {

	}

	@Override
	protected void autonomousPeriodic() {

	}
}
