package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;

public class ManualClimbDriveAction extends Action{

    public ManualClimbDriveAction() {
        requires(Robot.climbSystem);
    }

    @Override
    protected void execute() {
        double speed = Robot.xbox.LeftStick.getY() * 0.5;

        if (speed < -0.1)
            Robot.climbSystem.drive(-speed);
        else
            Robot.climbSystem.stop();
    }

    @Override
    protected void end() {
        Robot.climbSystem.stop();
    }
}