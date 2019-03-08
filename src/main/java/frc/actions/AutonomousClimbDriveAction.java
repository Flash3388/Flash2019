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
        double manualValue = Math.abs(Robot.xbox.LeftStick.getY());

        Robot.climbSystem.drive(
                manualValue > RobotMap.CLIMB_DRIVE_SPEED ?
                manualValue :
                RobotMap.CLIMB_DRIVE_SPEED);
    }

    @Override
    protected boolean isFinished() {
        return Robot.climbSystem.isClimbed();
    }

    @Override
    protected void end() {
        Robot.climbSystem.stop();
    }
}