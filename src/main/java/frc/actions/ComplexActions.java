package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.ActionGroup;
import edu.flash3388.flashlib.robot.SystemAction;
import frc.robot.Robot;

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

    public static Action autonomousDriveToTarget(double distance, double angle) {
        return new ActionGroup().addSequential(new SmartDriveToTarget(5, distance, angle))
                .addSequential(new TimedDriveAction(0.2, 0.5)).addSequential(new EdwardAction());
    }
    
    public static Action hatchDrive() {
        return new ActionGroup().addSequential(new RotationPIDAction(1))
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
