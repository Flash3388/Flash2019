package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.util.FlashUtil;
import frc.robot.Robot;
import frc.subsystems.DriveSystem;

public class RotationPIDAction extends Action {
    private final double mMargin;
    private final int mTimeInThreshold;

    private double mThresholdStartTime = 0;
    private double modifier = 0.6;

    public RotationPIDAction(double margin, int timeInThreshold, double setpoint) {
        requires(Robot.driveTrain);

        mMargin = margin;
        mTimeInThreshold = timeInThreshold;
        Robot.driveTrain.distancePID.setSetPoint(() -> setpoint);
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.resetGyro();

        Robot.driveTrain.rotatePID.setEnabled(true);
        Robot.driveTrain.rotatePID.setOutputLimit(-DriveSystem.ROTATE_LIMIT, DriveSystem.ROTATE_LIMIT);
        Robot.driveTrain.rotatePID.reset();
    }

    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }

    @Override
    protected void execute() {
        double pidResult = -Robot.driveTrain.rotatePID.calculate();

        if (!Robot.driveTrain.rotatePID.isEnabled() || inDistanceThreshold()) {
            if (mThresholdStartTime < 1)
                mThresholdStartTime = FlashUtil.millisInt();
        } else {
            if (mThresholdStartTime >= 1)
                mThresholdStartTime = 0;
        }
        Robot.driveTrain.tankDrive(pidResult * modifier, -pidResult * modifier);

    }

    @Override
    protected boolean isFinished() {
        return inDistanceThreshold() && mThresholdStartTime > 0
         && FlashUtil.millisInt() - mThresholdStartTime >= mTimeInThreshold;
    }

    private boolean inDistanceThreshold() {
        double current = Robot.driveTrain.distancePID.getPIDSource().pidGet();
        return Mathf.constrained(Robot.driveTrain.rotationSetPoint.get() - current, -mMargin, mMargin);
    }
}