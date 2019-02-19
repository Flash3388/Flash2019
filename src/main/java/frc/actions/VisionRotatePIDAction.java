package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.util.FlashUtil;
import edu.flash3388.flashlib.util.beans.DoubleProperty;
import frc.robot.Robot;

public class VisionRotatePIDAction extends Action {
    private final DoubleProperty mMargin;
    private final int mTimeInThreshold;

    private double mThresholdStartTime = 0;
    private double modifier;
    public VisionRotatePIDAction(DoubleProperty margin, int timeInThreshold, double setpoint) {
        requires(Robot.driveSystem);

        mMargin = margin;
        mTimeInThreshold = timeInThreshold;
        Robot.driveSystem.rotatePID.setSetPoint(()-> setpoint);
    }

    @Override
    protected void initialize() {
        Robot.driveSystem.resetGyro();

        Robot.driveSystem.rotatePID.setEnabled(true);
        Robot.driveSystem.rotatePID.reset();
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
    }

    @Override
    protected void execute() {
        double pidResult = -Robot.driveSystem.rotatePID.calculate();

        if (!Robot.driveSystem.rotatePID.isEnabled() || inDistanceThreshold()) {
            if (mThresholdStartTime < 1)
                mThresholdStartTime = FlashUtil.millisInt();
        }
        else {
            if (mThresholdStartTime >= 1)
                mThresholdStartTime = 0;
        }
        
        Robot.driveSystem.tankDrive(pidResult*modifier, -pidResult*modifier);

    }
    
    @Override
    protected boolean isFinished() {
        return false ;//inDistanceThreshold() && mThresholdStartTime > 0
                //&& FlashUtil.millisInt() - mThresholdStartTime >= mTimeInThreshold;
    }

    private boolean inDistanceThreshold() {
        double margin = mMargin.get();
        double current = Robot.driveSystem.rotatePID.getPIDSource().pidGet();
        return Mathf.constrained(Robot.driveSystem.rotationSetPoint.get() - current, -margin, margin);
    }
}
