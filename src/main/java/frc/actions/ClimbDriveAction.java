package frc.actions;

import edu.flash3388.flashlib.robot.Action;

import frc.robot.Robot;

public class ClimbDriveAction extends Action {
    public ClimbDriveAction() {
        requires(Robot.climbSystem);
    }
    
    @Override
    protected void initialize() {
        Robot.climbSystem.closeFront();
    }

    @Override
    protected void execute() {
        Robot.climbSystem.drive(Math.abs(Robot.xbox.LeftStick.getY()) * 0.8);
    }

    @Override
    protected void end() {
        Robot.climbSystem.stop();
    }
}