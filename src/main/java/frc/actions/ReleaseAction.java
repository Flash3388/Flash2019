package frc.actions;

import frc.robot.Robot;

import edu.flash3388.flashlib.robot.Action;

public class ReleaseAction extends Action {

    public ReleaseAction() {
        requires(Robot.rollerGripper);
    }

    @Override
    protected void execute() {
        Robot.rollerGripper.release();
    }

    @Override
    protected void end() {
        Robot.rollerGripper.stop();
    }
}
