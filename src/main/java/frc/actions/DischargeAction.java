package frc.actions;

import edu.flash3388.flashlib.robot.InstantAction;
import frc.robot.Robot;

public class DischargeAction extends InstantAction {

    public DischargeAction() {
        requires(Robot.hatchSystem);
    }

    @Override
    protected void execute() {
        Robot.hatchSystem.discharge();
    }
}