package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.ActionGroup;
import edu.flash3388.flashlib.robot.SystemAction;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Robot;
import frc.subsystems.DriveSystem;

public class ComplexActions {

    public static Action autonomousClimbAction() {
        return new ActionGroup().addSequential(climbAction())
                .addSequential(new AutonomousClimbDriveAction())
                .addSequential(new OnlyCloseFrontAction())
                .addSequential(new WaitAction(5))
                .addSequential(new AutonomousSecondClimbDriveAction())
                .addSequential(new OnlyCloseBackAction()); 
    }
    
    public static Action climbAction() {
        return new ActionGroup().addSequential(new OpenBackPistonAction())
                .addSequential(new OpenFrontAction())
                .addSequential(new WaitAction(2))
                .addSequential(new StallClimbDriveTimedAction(2));
    }

    public static Action climbDriveAction() {
        return new ActionGroup().addSequential(climbAction())
                .addSequential(new ManualClimbDriveAction());
    }

    public static Action visionAlign() {
        return new ActionGroup()
                .addSequential(new Action() {
                    NetworkTableEntry mRunEntry = NetworkTableInstance.getDefault().getEntry("vision_run");
                    NetworkTableEntry mWaitEntry = NetworkTableInstance.getDefault().getEntry("vision_wait");

                    @Override
                    protected void initialize() {
                        System.out.println("Start");
                        Robot.driveSystem.resetDistance();
                        Robot.cameraExposure.set(20);
                        mWaitEntry.setDouble(0);
                        mRunEntry.setBoolean(true);
                    }

                    @Override
                    protected void execute() {
                    }

                    @Override
                    protected boolean isFinished() {
                        return mWaitEntry.getDouble(0) > 5;
                    }

                    @Override
                    protected void interrupted() {
                        end();
                        mRunEntry.setBoolean(false);
                        Robot.cameraExposure.set(46);
                    }

                    @Override
                    protected void end() {
                        System.out.println("Done");
                        mRunEntry.setBoolean(false);
                    }
                })
                .addSequential(new Action() {
                    double[] distances;
                    double prev;
                    int count;

                    @Override
                    protected void initialize() {
                        distances = new double[5];
                        count = 0;
                        prev = 0;
                    }

                    @Override
                    protected void execute() {
                        double curr = Robot.driveSystem.getVisionDistanceCm();

                        if(prev == 0 ||  curr != prev) {
                            distances[count] = curr;
                            prev = curr;
                            count++;
                        }
                    }

                    @Override
                    protected void end() {
                        double avg = Mathf.avg(distances);
                        Robot.driveSystem.alignDistance.set(DriveSystem.findClosest(distances,avg));
                    }

                    @Override
                    protected boolean isFinished() {
                        return count >= 5;
                    }
                })
                .addSequential(new VisionAlign(1.0));
    }

    public static Action autonomousDriveToTarget(double distance, double angle) {
        return new ActionGroup().addSequential(new SmartDriveToTarget(5, distance, angle))
                .addSequential(new TimedDriveAction(0.2, 0.5)).addSequential(new EdwardAction());
    }
    
    public static Action hatchDrive() {
        return new ActionGroup().addSequential(new RotateAction(1))
                .addSequential(new SystemAction(new Action(){
                
                    @Override
                    protected void execute() {
                        double avg = (Robot.righJoystick.getY()+Robot.lefJoystick.getY()) /2;
                        Robot.driveSystem.tankDrive(avg, avg);
                    }
                
                    @Override
                    protected void end() {
                        
                    }
                }, Robot.driveSystem));
    }
}
