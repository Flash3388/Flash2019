package frc.actions;

import edu.flash3388.flashlib.robot.InstantAction;

import frc.robot.Robot;

public class ClimbAction extends InstantAction {

    public ClimbAction() {
        requires(Robot.climbSystem);
    }

    @Override
    protected void execute() {
        Robot.climbSystem.openFront();
        Robot.climbSystem.openBack();
    }
}
