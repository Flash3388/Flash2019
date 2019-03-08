package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.ActionGroup;
import frc.robot.Robot;

public class ComplexActions {

    public static Action autonomousClimbAction() {
        return new ActionGroup().addSequential(climbAction())
                .addSequential(new AutonomousClimbDriveAction())
                .addSequential(new OnlyCloseFrontAction())
                .addWaitAction(5)
                .addSequential(new AutonomousSecondClimbDriveAction())
                .addSequential(new OnlyCloseBackAction()); 
    }
    
    public static Action climbAction() {
        return new ActionGroup().addSequential(new OpenBackPistonAction())
                .addSequential(new OpenFrontAction())
                .addWaitAction(2)
                .addSequential(new Action() {
                    @Override
                    protected void execute() {
                        Robot.climbSystem.drive(0.1);
                    }

                    @Override
                    protected void end() {
                        Robot.climbSystem.stop();
                    }
                },2);
    }

    public static Action climbDriveAction() {
        return new ActionGroup().addSequential(climbAction())
                .addSequential(new ManualClimbDriveAction());
    }
}