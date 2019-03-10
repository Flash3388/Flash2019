package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.util.FlashUtil;

import frc.robot.Robot;

public class RotationPIDAction extends Action {
    private final double mMargin;
    private final int mTimeInThreshold;

    private double mThresholdStartTime = 0;
    private double modifier = 0.6;

    public RotationPIDAction(double margin, int timeInThreshold, double setpoint) {
        requires(Robot.driveSystem);

        mMargin = margin;
        mTimeInThreshold = timeInThreshold;
        
        Robot.driveSystem.rotationSetPoint.set(setpoint);
    }

    @Override
    protected void initialize() {
        Robot.driveSystem.rotatePID.setEnabled(true);
        Robot.driveSystem.rotatePID.reset();
        Robot.driveSystem.resetGyro();
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
    }

    @Override
    protected void execute() {
        double pidResult = Robot.driveSystem.rotatePID.calculate();

        if (!Robot.driveSystem.rotatePID.isEnabled() || inRotationThreshold()) {
            if (mThresholdStartTime < 1)
                mThresholdStartTime = FlashUtil.millisInt();
        } else {
            if (mThresholdStartTime >= 1)
                mThresholdStartTime = 0;
        }
        Robot.driveSystem.tankDrive(0.2*Math.signum(Robot.driveSystem.rotatePID.getSetPoint().get()) , -0.2 *Math.signum(Robot.driveSystem.rotatePID.getSetPoint().get()));

    }

    @Override
    protected boolean isFinished() {
        return inRotationThreshold() && mThresholdStartTime > 0
         && FlashUtil.millisInt() - mThresholdStartTime >= mTimeInThreshold;
    }

    private boolean inRotationThreshold() {
        double current = Robot.driveSystem.rotatePID.getPIDSource().pidGet();
        System.out.println(Robot.driveSystem.rotatePID.getSetPoint().get() +" "+ current+" "+ mMargin);
        return Mathf.constrained(Robot.driveSystem.rotationSetPoint.get() - current, -mMargin, mMargin);
    }
}
