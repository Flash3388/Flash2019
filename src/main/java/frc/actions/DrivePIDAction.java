package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.util.FlashUtil;
import frc.robot.Robot;
import frc.subsystems.DriveSystem;

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
    protected void initialize() {
        Robot.driveTrain.distanceSetPoint.set(mSetpoint + Robot.driveTrain.distanceSource.pidGet());
        Robot.driveTrain.distancePID.setEnabled(true);
        Robot.driveTrain.distancePID.setOutputLimit(-DriveSystem.DRIVE_LIMIT, DriveSystem.DRIVE_LIMIT);
        Robot.driveTrain.distancePID.reset();

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
            Robot.driveTrain.drive(-Robot.driveTrain.distancePID.calculate());
        }

        else {
            if (mThresholdStartTime >= 1)
                mThresholdStartTime = 0;
            Robot.driveTrain.drive(-Robot.driveTrain.distancePID.calculate());
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
