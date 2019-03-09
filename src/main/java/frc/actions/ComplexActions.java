package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.ActionGroup;

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
        return new ActionGroup().addSequential(new SmartDriveToTarget(3, distance, angle))
                .addSequential(new TimedDriveAction(0.2, 0.5))
                .addSequential(new EdwardAction());
    }
}
