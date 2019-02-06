package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.subsystems.LiftSystem;
import frc.robot.Robot;

public class StallAction extends Action {
    public StallAction() {
        requires(Robot.liftSystem);
    }

    @Override
    protected void end() {
    }

    @Override
    protected void execute() {
        Robot.liftSystem.rotate(LiftSystem.LIFT_SPEED);
    }
}