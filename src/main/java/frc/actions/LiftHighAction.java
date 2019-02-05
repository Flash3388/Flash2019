package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.Subsystem.LiftSystem;
import frc.robot.Robot;

public class LiftHighAction extends Action {
    public LiftHighAction() {
        requires(Robot.liftSystem);
    }

    @Override
    protected void initialize() {
        Robot.liftSystem.rotate(LiftSystem.LIFT_SPEED);
    }

    @Override
    protected void end() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return Robot.liftSystem.isHigh();
    }
}