package frc.actions;

import edu.flash3388.flashlib.robot.InstantAction;

import frc.robot.Robot;

public class CloseFront extends InstantAction {

    public CloseFront() {
        requires(Robot.climbSystem);
    }

    @Override
    protected void execute() {
        Robot.climbSystem.closeFront();
    }
}
