package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class AutonomousClimbDriveAction extends Action {

    public AutonomousClimbDriveAction() {
        requires(Robot.climbSystem);
    }

    @Override
    protected void initialize() {
        System.out.println("AutonomousClimbDriveAction");
    }

    @Override
    protected void execute() {
        Robot.climbSystem.drive(RobotMap.CLIMB_DRIVE_SPEED);
    }

    @Override
    protected boolean isFinished() {
        System.out.println("isClimbed:" + Robot.climbSystem.isClimbed());
        return Robot.climbSystem.isClimbed();
    }

    @Override
    protected void end() {
        System.out.println("Done AutonomousClimbDriveAction");
        Robot.climbSystem.stop();
    }
}