package frc.actions;

import edu.flash3388.flashlib.robot.InstantAction;
import frc.robot.Robot;

public class OnlyCloseBackAction extends InstantAction {

    public OnlyCloseBackAction() {
        requires(Robot.climbSystem);
    }

    @Override
    protected void execute() {
        Robot.climbSystem.closeBack();
    }
}