package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.util.FlashUtil;
import frc.robot.Robot;

public class DrivePIDAction extends Action {
    private final double mMargin;
    private final int mTimeInThreshold;

    private boolean mInThreshold = false;
    private double mThresholdStartTime = 0;
    private double mSetpoint;

    public DrivePIDAction(double margin, int timeInThreshold, double setpoint) {
        mMargin = margin;
        mTimeInThreshold = timeInThreshold;
        mSetpoint = setpoint;
    }

    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }

    @Override
    protected void execute() {
        if (!Robot.driveTrain.distancePID.isEnabled() || inDistanceThreshold()) {
            if (mThresholdStartTime < 1) {
                mThresholdStartTime = FlashUtil.millisInt();
                mInThreshold = true;
            }
            Robot.driveTrain.tankDrive(-Robot.driveTrain.distancePID.calculate(),
                    -Robot.driveTrain.distancePID.calculate());
        }
    }

    @Override
    protected boolean isFinished() {
        return inDistanceThreshold() && mThresholdStartTime > 0
                && FlashUtil.millisInt() - mThresholdStartTime >= mTimeInThreshold;
    }

    private boolean inDistanceThreshold() {
        double current = Robot.driveTrain.distancePID.getPIDSource().pidGet();
        return Mathf.constrained(Robot.driveTrain.distanceSetPoint.get() - current, -mMargin, mMargin);
    }
}
