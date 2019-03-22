package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;

public class VisionAlign extends Action {
    private final double mMargin;

    private double mRotateSpeed;
    private double setpoint;

    private TimeStampRecorder jhonson;
    
    public VisionAlign(double margin, double rotateSpeed) {
        requires(Robot.driveSystem);

        mMargin = margin;
        mRotateSpeed = rotateSpeed;

        jhonson = new TimeStampRecorder();
    }

    public VisionAlign(double margin) {
        this(margin, 0.2);
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
        jhonson.append(Robot.clock.currentTimeMillis(), Robot.driveSystem.getAngle());

        double setpoint = Robot.driveSystem.getVisionAngle() - 
                distance(Robot.driveSystem.getVisionAngle(), 
                jhonson.getAngleAt(Robot.driveSystem.getVisionTime()));
        double rotVal = mRotateSpeed * Math.signum(setpoint);

        if (inRotationThreshold())
            rotVal = 0;
        Robot.driveSystem.arcadeDrive(0, rotVal);
    }

    private boolean inRotationThreshold() {
        double current = Robot.driveSystem.rotatePID.getPIDSource().pidGet();
        return Mathf.constrained(setpoint - current, -mMargin, mMargin);
    }

    private static double distance(double alpha, double beta) {
        double phi = Math.abs(beta - alpha) % 360; // This is either the distance or 360 - distance
        double distance = phi > 180 ? 360 - phi : phi;
        return distance;
    }
}