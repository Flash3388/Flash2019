package frc.actions;

import edu.flash3388.flashlib.robot.InstantAction;
import frc.robot.Robot;

public class EdwardAction extends InstantAction{

    public EdwardAction() {
        requires(Robot.hatchSystem);
    }

    @Override
    protected void execute() {
        Robot.hatchSystem.toggle();
    }
}