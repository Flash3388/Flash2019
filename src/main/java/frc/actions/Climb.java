package frc.actions;

import edu.flash3388.flashlib.robot.InstantAction;
import frc.robot.Robot;

public class Climb extends InstantAction {

    public Climb() {
        requires(Robot.climbingSystem);
    }

    @Override
    protected void execute() {
        Robot.climbingSystem.openFront();
        Robot.climbingSystem.openBack();
    }
}
