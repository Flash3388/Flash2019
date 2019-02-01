package frc.robot.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;


public class OpenSole extends Action {

    public OpenSole() {
        requires(Robot.climb);
    }

    @Override
    protected void execute() {
        Robot.climb.openPistons();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }


}
