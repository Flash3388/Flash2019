package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;

import frc.robot.Robot;

public class RotationPIDAction extends Action {
    private final double mMargin;

    private double setpoint;
    private double mRotateSpeed;

    public RotationPIDAction(double margin, double rotateSpeed) {
        requires(Robot.driveSystem);

        mMargin = margin;
        mRotateSpeed = rotateSpeed;
    }

    public RotationPIDAction(double margin) {
        requires(Robot.driveSystem);

        mMargin = margin;
        mRotateSpeed = 0.2;
    }

    @Override
    protected void initialize() {
        Robot.driveSystem.resetGyro();
        setpoint = Robot.driveSystem.getVisionAngle();
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
    }

    @Override
    protected void execute() {
        Robot.driveSystem.tankDrive(mRotateSpeed*Math.signum(setpoint) , -mRotateSpeed *Math.signum(setpoint));
    }

    @Override
    protected boolean isFinished() {
        return inRotationThreshold();
    }

    private boolean inRotationThreshold() {
        double current = Robot.driveSystem.rotatePID.getPIDSource().pidGet();
        return Mathf.constrained(setpoint - current, -mMargin, mMargin);
    }
}
