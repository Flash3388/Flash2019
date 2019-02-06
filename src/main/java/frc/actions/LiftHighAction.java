package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.subsystems.LiftSystem;
import frc.robot.Robot;

public class LiftHighAction extends Action {
    public LiftHighAction() {
        requires(Robot.liftSystem);
    }

    @Override
    protected void end() {
    }

    @Override
    protected void execute() {
        Robot.liftSystem.rotate(LiftSystem.LIFT_SPEED);
    }

    @Override
    protected boolean isFinished() {
        return Robot.liftSystem.isHigh();
    }
}