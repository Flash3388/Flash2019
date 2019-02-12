package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.util.FlashUtil;
import frc.robot.Robot;

public class SmartDriveToTarget extends Action {
    private double mThresholdStartTime = 0.0;
    private double mRotateMargin;

    private int mRotateTimeInThreshold;

    public SmartDriveToTarget(double rotateMargin, int rotateTimeInThreshold) {
        requires(Robot.driveTrain);

        mRotateMargin = rotateMargin;
        mRotateTimeInThreshold = rotateTimeInThreshold;
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.distanceSetPoint.set(Robot.driveTrain.getVisionDistance());
        Robot.driveTrain.rotationSetPoint.set(-90);

        if (Robot.driveTrain.distanceSetPoint.get() == -1 || Robot.driveTrain.distanceSetPoint.get() > 300.0
                || Robot.driveTrain.rotationSetPoint.get() == 0.0) {
            System.out.println("Fucked");
            cancel();
        }

        Robot.driveTrain.resetDistance();
        // Robot.driveTrain.resetGyro();

        Robot.driveTrain.distancePID.setEnabled(true);
        Robot.driveTrain.distancePID.reset();

        Robot.driveTrain.rotatePID.setEnabled(true);
        Robot.driveTrain.rotatePID.reset();
    }

    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }

    @Override
    protected void execute() {
        double distanceResult = -Robot.driveTrain.distancePID.calculate();
        double rotationResult = Robot.driveTrain.rotatePID.calculate();
        double distance = Robot.driveTrain.getDistance();
        double ratio;

        if(distance>Robot.driveTrain.distancePID.getSetPoint().get())
            ratio = 1;
        else
            ratio = distance / Robot.driveTrain.distancePID.getSetPoint().get();
        
        if (ratio < 0.575)
            ratio -= 0.575;

        if (!Robot.driveTrain.rotatePID.isEnabled() || (inRotationThreshold())) {
            if (mThresholdStartTime < 1)
                mThresholdStartTime = FlashUtil.millisInt();
        } else {
            if (mThresholdStartTime >= 1)
                mThresholdStartTime = 0;
        }

        Robot.driveTrain.arcadeDrive(distanceResult * (1.0 - ratio), rotationResult * ratio * 0.6);
    }

    @Override
    protected boolean isFinished() {
        return inRotationThreshold() && inDistanceThreshold() && mThresholdStartTime > 0
                && FlashUtil.millisInt() - mThresholdStartTime >= mRotateTimeInThreshold;
    }

    private boolean inRotationThreshold() {
        double margin = mRotateMargin;
        double current = Robot.driveTrain.rotatePID.getPIDSource().pidGet();
        return Mathf.constrained(Robot.driveTrain.rotationSetPoint.get() - current, -margin, margin);
    }

    private boolean inDistanceThreshold() {
        double margin = 20;
        double current = Robot.driveTrain.distancePID.getPIDSource().pidGet();

        return Mathf.constrained(Robot.driveTrain.distanceSetPoint.get() - current, -margin, margin);
    }
}
