package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class AutonomousSecondClimbDriveAction extends Action {

    public AutonomousSecondClimbDriveAction() {
        requires(Robot.climbSystem,Robot.driveSystem);
    }

    @Override
    protected void initialize() {
        System.out.println("AutonomousSecondClimbDriveAction");
    }

    @Override
    protected void end() {
        System.out.println("Done AutonomousSecondClimbDriveAction");
        Robot.driveSystem.stop();
        Robot.climbSystem.stop();
    }

    @Override
    protected void execute() {
        Robot.climbSystem.drive(RobotMap.CLIMB_DRIVE_SPEED);
        Robot.driveSystem.tankDrive(-0.1, -0.1);
    }

    @Override
    protected boolean isFinished() {
        return Robot.climbSystem.isDrove();
    }
}