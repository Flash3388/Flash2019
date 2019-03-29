package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;

public class VisionAlign extends Action {
    private final double mMargin;

    private TimeStampRecorder jhonson;
    
    public VisionAlign(double margin) {
        requires(Robot.driveSystem);

        mMargin = margin;

        jhonson = new TimeStampRecorder();
    }

    @Override
    protected void initialize() {
        Robot.driveSystem.resetGyro();
        Robot.driveSystem.rotatePID.reset();
        Robot.driveSystem.rotatePID.setEnabled(true);
        Robot.driveSystem.rotationSetPoint.set(0);
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
    }

    @Override
    protected void execute() {
        jhonson.append(Robot.clock.currentTimeMillis(), Robot.driveSystem.getAngle());

        double jhonsonAngle = jhonson.getAngleAt(Robot.driveSystem.getVisionTime());
        double distance = distance(Robot.driveSystem.getAngle(), jhonsonAngle);

        double setpoint = Robot.driveSystem.getVisionAngle() - distance;
                
        Robot.driveSystem.currectRotationSource.set(setpoint);
        double rotVal = Robot.driveSystem.rotatePID.calculate();
        System.out.println();
        if (inRotationThreshold())
            rotVal = 0;

        Robot.driveSystem.arcadeDrive((Robot.righJoystick.getY()+Robot.lefJoystick.getY())/2,-rotVal);
    }

    private boolean inRotationThreshold() {
        double current = Robot.driveSystem.rotatePID.getPIDSource().pidGet();
        return Mathf.constrained(current, -mMargin, mMargin);
    }

    private static double distance(double alpha, double beta) {
        double phi = Math.abs(beta - alpha) % 360; 
        double distance = phi > 180 ? 360 - phi : phi;
        return distance;
    }
}