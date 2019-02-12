package frc.actions;

import edu.flash3388.flashlib.robot.InstantAction;
import frc.robot.Robot;

public class ClimbAction extends InstantAction {

    public ClimbAction() {
        requires(Robot.climbingSystem);
    }

    @Override
    protected void execute() {
        Robot.climbingSystem.openFront();
        Robot.climbingSystem.openBack();
    }
}
