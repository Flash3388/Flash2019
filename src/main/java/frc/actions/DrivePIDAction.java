package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.util.FlashUtil;

import frc.robot.Robot;

public class DrivePIDAction extends Action {
    private final double mMargin;
    private final int mTimeInThreshold;

    private double mThresholdStartTime = 0;
    private double modifier;

    public DrivePIDAction(double margin, int timeInThreshold, double setpoint) {
        requires(Robot.driveSystem);

        mMargin = margin;
        mTimeInThreshold = timeInThreshold;
        Robot.driveSystem.distancePID.setSetPoint(() -> setpoint);
    }

    @Override
    protected void initialize() {
        Robot.driveSystem.resetDistance();

        Robot.driveSystem.distancePID.setEnabled(true);
        Robot.driveSystem.distancePID.reset();
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
    }

    @Override
    protected void execute() {
        double pidResult = -Robot.driveSystem.distancePID.calculate();

        if (!Robot.driveSystem.distancePID.isEnabled() || inDistanceThreshold()) {
            if (mThresholdStartTime < 1)
                mThresholdStartTime = FlashUtil.millisInt();
        } else {
            if (mThresholdStartTime >= 1)
                mThresholdStartTime = 0;
        }
        Robot.driveSystem.tankDrive(pidResult * modifier, pidResult * modifier);
    }

    @Override
    protected boolean isFinished() {
        return false;// inDistanceThreshold() && mThresholdStartTime > 0
        // && FlashUtil.millisInt() - mThresholdStartTime >= mTimeInThreshold;
    }

    private boolean inDistanceThreshold() {
        double current = Robot.driveSystem.distancePID.getPIDSource().pidGet();
        return Mathf.constrained(Robot.driveSystem.rotationSetPoint.get() - current, -mMargin, mMargin);
    }
}
