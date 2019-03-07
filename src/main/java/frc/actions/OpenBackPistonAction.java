package frc.actions;

import edu.flash3388.flashlib.robot.InstantAction;
import frc.robot.Robot;

public class OpenBackPistonAction extends InstantAction {

    public OpenBackPistonAction() {
        requires(Robot.climbSystem);
    }

    @Override
    protected void execute() {
        Robot.climbSystem.openBack();
    }
}
