package frc.actions;

import edu.flash3388.flashlib.robot.InstantAction;

import frc.robot.Robot;

public class CloseBack extends InstantAction {

    public CloseBack() {
        requires(Robot.climbSystem);
    }

    @Override
    protected void execute() {
        Robot.climbSystem.closeBack();
    }
}
