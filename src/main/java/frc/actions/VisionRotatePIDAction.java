package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.util.FlashUtil;
import edu.flash3388.flashlib.util.beans.DoubleProperty;
import frc.robot.Robot;
import frc.subsystems.DriveSystem;

public class VisionRotatePIDAction extends Action {
    private final DoubleProperty mMargin;
    private final int mTimeInThreshold;

    private double mThresholdStartTime = 0;
    private double modifier;
    public VisionRotatePIDAction(DoubleProperty margin, int timeInThreshold, double setpoint) {
        requires(Robot.driveTrain);

        mMargin = margin;
        mTimeInThreshold = timeInThreshold;
        Robot.driveTrain.rotatePID.setSetPoint(()-> setpoint);
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.resetGyro();

        Robot.driveTrain.rotatePID.setEnabled(true);
        Robot.driveTrain.rotatePID.setOutputLimit(-DriveSystem.ROTATE_LIMIT, DriveSystem.ROTATE_LIMIT);
        Robot.driveTrain.rotatePID.reset();
        modifier = Robot.driveTrain.getMod();
    }

    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }

    @Override
    protected void execute() {
        // if (FlashUtil.millisInt() - Robot.driveTrain.motherFuckingProperty.get() > 40) {
        //     return;
        // }

        double pidResult = -Robot.driveTrain.rotatePID.calculate();

        if (!Robot.driveTrain.rotatePID.isEnabled() || inDistanceThreshold()) {
            if (mThresholdStartTime < 1)
                mThresholdStartTime = FlashUtil.millisInt();
        }
        else {
            if (mThresholdStartTime >= 1)
                mThresholdStartTime = 0;
        }
        //Robot.driveTrain.tankDrive(pidResult * modifier, -pidResult * modifier);
        Robot.driveTrain.tankDrive(pidResult*modifier, -pidResult*modifier);

    }
    
    @Override
    protected boolean isFinished() {
        return false ;//inDistanceThreshold() && mThresholdStartTime > 0
                //&& FlashUtil.millisInt() - mThresholdStartTime >= mTimeInThreshold;
    }

    private boolean inDistanceThreshold() {
        double margin = mMargin.get();
        double current = Robot.driveTrain.rotatePID.getPIDSource().pidGet();
        return Mathf.constrained(Robot.driveTrain.rotationSetPoint.get() - current, -margin, margin);
    }
}
