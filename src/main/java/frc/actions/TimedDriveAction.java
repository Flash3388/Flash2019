package frc.actions;

import frc.robot.Robot;

public class TimedDriveAction extends TimedAction {
    private double mSpeed;

    public TimedDriveAction(double speed, double timeout) {
        super(timeout);
        requires(Robot.driveSystem);
        mSpeed = speed;
    }

    @Override
    protected void execute() {
        System.out.println("AAAA");
        Robot.driveSystem.tankDrive(-mSpeed,-mSpeed);
    }

    @Override
    protected void end() {
        super.end();
        Robot.driveSystem.stop();
    }
}
