package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.ActionGroup;

public class ComplexActions {

    public static Action AutonomousClimbAction() {
        return new ActionGroup().addSequential(new OnlyClimbAction())
                .addWaitAction(4)
                .addSequential(new AutonomousClimbDriveAction())
                .addSequential(new OnlyCloseFrontAction())
                .addWaitAction(5)
                .addSequential(new AutonomousSecondClimbDriveAction())
                .addSequential(new OnlyCloseBackAction()); 
    }
}