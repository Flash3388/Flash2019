package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;

public class GripperStop extends Action {

    private final double mSpeedDecline;
    private final double mSpeedMargin;

    public GripperStop(double speedDecline, double speedMargin) {
        requires(Robot.rollerGripperSystem);
        mSpeedDecline = speedDecline;
        mSpeedMargin = speedMargin;
    }

    @Override
    protected void execute() {
        double currentSpeed = Robot.rollerGripperSystem.getCurrentSpeed();
        double nextSpeedAbs = Math.abs(currentSpeed) - mSpeedDecline;

        if (nextSpeedAbs < mSpeedMargin) {
            nextSpeedAbs = 0.0;
        }

        Robot.rollerGripperSystem.rotate(nextSpeedAbs * Math.signum(currentSpeed));
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.rollerGripperSystem.getCurrentSpeed()) < mSpeedMargin;
    }

    @Override
    protected void end() {
        Robot.rollerGripperSystem.rotate(0.0);
    }
}
