package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.util.FlashUtil;
import edu.flash3388.flashlib.util.beans.DoubleProperty;
import frc.robot.Robot;
import frc.subsystems.DriveSystem;

public class DrivePIDAction extends Action {
    private final double mMargin;
    private final int mTimeInThreshold;

    private double mThresholdStartTime = 0;
    private double modifier;

    public DrivePIDAction(double margin, int timeInThreshold, double setpoint) {
        requires(Robot.driveTrain);

        mMargin = margin;
        mTimeInThreshold = timeInThreshold;
        Robot.driveTrain.distancePID.setSetPoint(() -> setpoint);
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.resetDistance();

        Robot.driveTrain.distancePID.setEnabled(true);
        Robot.driveTrain.distancePID.reset();
        modifier = Robot.driveTrain.getMod();
    }

    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }

    @Override
    protected void execute() {
        double pidResult = -Robot.driveTrain.distancePID.calculate();

        if (!Robot.driveTrain.distancePID.isEnabled() || inDistanceThreshold()) {
            if (mThresholdStartTime < 1)
                mThresholdStartTime = FlashUtil.millisInt();
        } else {
            if (mThresholdStartTime >= 1)
                mThresholdStartTime = 0;
        }
        Robot.driveTrain.tankDrive(pidResult * modifier, pidResult * modifier);
    }

    @Override
    protected boolean isFinished() {
        return false;// inDistanceThreshold() && mThresholdStartTime > 0
        // && FlashUtil.millisInt() - mThresholdStartTime >= mTimeInThreshold;
    }

    private boolean inDistanceThreshold() {
        double current = Robot.driveTrain.distancePID.getPIDSource().pidGet();
        return Mathf.constrained(Robot.driveTrain.rotationSetPoint.get() - current, -mMargin, mMargin);
    }
}
