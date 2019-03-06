package frc.actions;

import edu.flash3388.flashlib.robot.Action;

import frc.robot.Robot;

public class CloseFront extends Action {

    public CloseFront() {
        requires(Robot.climbSystem);
    }

    @Override
    protected void initialize() {
        Robot.climbSystem.closeFront();
    }

    @Override
    protected void execute() {
        if (Robot.climbSystem.isFrontClosed()) {
            double speed = Robot.xbox.LeftStick.getY() * 0.8;
            System.out.println("here");
            if (speed > 0.16)
                Robot.climbSystem.drive(speed);
            else
                Robot.climbSystem.stop();
        }
        else
            Robot.climbSystem.stop();
    }

    @Override
    protected void end() {
        Robot.climbSystem.stop();
    }
}
