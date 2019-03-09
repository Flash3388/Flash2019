package frc.actions;

import frc.robot.Robot;

public class StallClimbDriveTimedAction extends TimedAction {

    public StallClimbDriveTimedAction(double timeout) {
        super(timeout);
        requires(Robot.climbSystem);
    }

    @Override
    protected void execute() {
        Robot.climbSystem.drive(0.1);
    }
    
    @Override
    protected void end() {
        super.end();
        Robot.climbSystem.stop();
    }
}