package frc.actions;

import frc.robot.Robot;

import edu.flash3388.flashlib.robot.Action;

public class ReleaseAction extends Action {

    public ReleaseAction() {
        requires(Robot.rollerGripperSystem);
    }

    @Override
    protected void execute() {
        Robot.rollerGripperSystem.release();
    }

    @Override
    protected void end() {
        Robot.rollerGripperSystem.stop();
    }
}
