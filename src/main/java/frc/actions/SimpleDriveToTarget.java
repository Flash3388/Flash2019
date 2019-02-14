package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.util.FlashUtil;
import frc.robot.Robot;
import frc.subsystems.DriveSystem;

public class SimpleDriveToTarget extends Action {
    private final double ROTATION_MODIFIER = 0.35;
    private final double DISTANCE_MODIFIER = -0.3;

    private final double mDistanceSetpoint;
    private final double mRotationSetpoint;

    private double mThresholdStartTime = 0.0;

    private double mRotateMargin;
    private double mDistMargin;

    private int mRotateTimeInThreshold;

    private double mInitDistance;

    public SimpleDriveToTarget(double distanceSetpoint, double rotateSetpoint, double rotateMargin, double distMargin,
            int rotateTimeInThreshold, double initDistance) {
        requires(Robot.driveTrain);

        mDistMargin = distMargin;
        mRotateMargin = rotateMargin;
        mRotateTimeInThreshold = rotateTimeInThreshold;

        Robot.driveTrain.rotatePID.setSetPoint(() -> rotateSetpoint);
        Robot.driveTrain.distancePID.setSetPoint(() -> distanceSetpoint);

        mDistanceSetpoint = distanceSetpoint;
        mRotationSetpoint = rotateSetpoint;
        mInitDistance = initDistance;
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.rotatePID.setEnabled(true);
        Robot.driveTrain.rotatePID.reset();
    }

    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }

    @Override
    protected void execute() {
        double rotationResult = Robot.driveTrain.rotatePID.calculate();
        double distance = Robot.driveTrain.getDistance();
        double ratio = 0;

        if(distance > mDistanceSetpoint)
            ratio = distance / mInitDistance;

        if (!Robot.driveTrain.rotatePID.isEnabled() || (inRotationThreshold() && inDistThreshold())) {
            if (mThresholdStartTime < 1)
                mThresholdStartTime = FlashUtil.millisInt();
        } else {
            if (mThresholdStartTime >= 1)
                mThresholdStartTime = 0;
        }
        if(distance == -1)
            Robot.driveTrain.stop();
        else
            Robot.driveTrain.arcadeDrive(DISTANCE_MODIFIER* ratio, rotationResult * ROTATION_MODIFIER);
    }
    
    @Override
    protected boolean isFinished() {
        return inRotationThreshold() && inDistThreshold() && mThresholdStartTime > 0
         && FlashUtil.millisInt() - mThresholdStartTime >= mRotateTimeInThreshold;
    }
    
    private boolean inRotationThreshold() {
        double margin = mRotateMargin;
        double current = Robot.driveTrain.rotatePID.getPIDSource().pidGet();
        return Mathf.constrained(mRotationSetpoint - current, -margin, margin);
    }
    
    private boolean inDistThreshold() {
        double margin = mDistMargin;
        double current = Robot.driveTrain.getDistance();
        return Mathf.constrained(mDistanceSetpoint - current, -margin, margin);
    }
}
