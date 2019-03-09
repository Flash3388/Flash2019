package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;

public class LiftBallMaxAction extends Action {

    public LiftBallMaxAction() {
        requires(Robot.liftSystem);
    }

    @Override
    protected void execute() {
        Robot.liftSystem.lift();
    }

    @Override
    protected void end() {
        Robot.liftSystem.stop();
    }

    @Override
    protected boolean isFinished() {
        return Robot.liftSystem.isUp();
    }
}