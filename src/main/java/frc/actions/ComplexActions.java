package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.ActionGroup;

public class ComplexActions {

    public static Action AutonomousClimbAction() {
        return new ActionGroup().addSequential(ClimbAction())
                .addWaitAction(4)
                .addSequential(new AutonomousClimbDriveAction())
                .addSequential(new OnlyCloseFrontAction())
                .addWaitAction(5)
                .addSequential(new AutonomousSecondClimbDriveAction())
                .addSequential(new OnlyCloseBackAction()); 
    }
    
    public static Action ClimbAction() {
        return new ActionGroup().addSequential(new OpenBackPistonAction())
                .addWaitAction(0.5)
                .addSequential(new OpenFrontAction());
    }

    public static Action ClimbDriveAction() {
        return new ActionGroup().addSequential(ClimbAction())
                .addWaitAction(4)
                .addSequential(new ManualClimbDriveAction());
    }
}