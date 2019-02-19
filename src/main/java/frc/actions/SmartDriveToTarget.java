package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.util.FlashUtil;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.subsystems.DriveSystem;

public class SmartDriveToTarget extends Action {
    private static final double MAX_DISTANCE = 300.0;

    private double mThresholdStartTime = 0.0;
    private double mRotateMargin;

    private int mRotateTimeInThreshold;

    public SmartDriveToTarget(double rotateMargin, int rotateTimeInThreshold, double distanceToTarget,
            double targetAngle) {
        requires(Robot.driveSystem);

        mRotateMargin = rotateMargin;
        mRotateTimeInThreshold = rotateTimeInThreshold;

        Robot.driveSystem.distanceSetPoint.set(distanceToTarget);
        Robot.driveSystem.rotationSetPoint.set(
                (double)DriveSystem.findClosest(RobotMap.ANGLE_SET,(int)Robot.driveSystem.getAngle()-(int)targetAngle));

    }

    @Override
    protected void initialize() {
        System.out.println(Robot.driveSystem.rotationSetPoint.get());
        if (Robot.driveSystem.distanceSetPoint.get() == -1 || Robot.driveSystem.distanceSetPoint.get() > MAX_DISTANCE) {
            System.out.println("Fucked");
            cancel();
        }

        Robot.driveSystem.resetDistance();

        Robot.driveSystem.distancePID.setEnabled(true);
        Robot.driveSystem.distancePID.reset();

        Robot.driveSystem.rotatePID.setEnabled(true);
        Robot.driveSystem.rotatePID.reset();
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
    }

    @Override
    protected void execute() {
        double distanceResult = -Robot.driveSystem.distancePID.calculate();
        double rotationResult = Robot.driveSystem.rotatePID.calculate();
        double distance = Robot.driveSystem.getDistance();
        double ratio;

        if(distance>Robot.driveSystem.distancePID.getSetPoint().get())
            ratio = 1;
        else
            ratio = distance / Robot.driveSystem.distancePID.getSetPoint().get();
        
        if (ratio < RobotMap.TURNING_RATIO)
            ratio -= RobotMap.TURNING_RATIO;

        if (!Robot.driveSystem.rotatePID.isEnabled() || (inRotationThreshold())) {
            if (mThresholdStartTime < 1)
                mThresholdStartTime = FlashUtil.millisInt();
        } else {
            if (mThresholdStartTime >= 1)
                mThresholdStartTime = 0;
        }

        Robot.driveSystem.arcadeDrive(distanceResult * (1.0 - ratio), rotationResult * ratio * 0.6);
    }

    @Override
    protected boolean isFinished() {
        return inRotationThreshold() && mThresholdStartTime > 0
                && FlashUtil.millisInt() - mThresholdStartTime >= mRotateTimeInThreshold;
    }

    private boolean inRotationThreshold() {
        double margin = mRotateMargin;
        double current = Robot.driveSystem.rotatePID.getPIDSource().pidGet();
        return Mathf.constrained(Robot.driveSystem.rotationSetPoint.get() - current, -margin, margin);
    }
}
