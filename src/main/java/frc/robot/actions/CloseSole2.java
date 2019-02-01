package frc.robot.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;

public class CloseSole2 extends Action {

    public CloseSole2() {
        requires(Robot.climb);
    }

    @Override
    protected void execute() {
        Robot.climb.closePiston2();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }



}
