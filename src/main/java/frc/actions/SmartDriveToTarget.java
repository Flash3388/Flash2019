package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.util.FlashUtil;
import frc.robot.Robot;
import frc.subsystems.DriveSystem;

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
        Robot.driveTrain.rotationSetPoint.set(
                (double)DriveSystem.findClosest(new int[]{-90,-45,0,45,90},(int)Robot.driveTrain.getAngle()-(int)Robot.driveTrain.getVisionAngleDeg()));

        System.out.println(Robot.driveTrain.rotationSetPoint.get());
        if (Robot.driveTrain.distanceSetPoint.get() == -1 || Robot.driveTrain.distanceSetPoint.get() > 300.0) {
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
        
        if (ratio < 0.56)
            ratio -= 0.56;

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
        return inRotationThreshold() && mThresholdStartTime > 0
                && FlashUtil.millisInt() - mThresholdStartTime >= mRotateTimeInThreshold;
    }

    private boolean inRotationThreshold() {
        double margin = mRotateMargin;
        double current = Robot.driveTrain.rotatePID.getPIDSource().pidGet();
        return Mathf.constrained(Robot.driveTrain.rotationSetPoint.get() - current, -margin, margin);
    }
}
