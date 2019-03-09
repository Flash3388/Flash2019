package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
@Deprecated
public class LiftAction extends Action {

    private  double mTimeout;
    private double mStartTime;

    public LiftAction(double timeout) {
        requires(Robot.liftSystem);
        mTimeout = timeout;
    }

    @Override
    protected void initialize() {
        mStartTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
        Robot.liftSystem.lift();
    }

    @Override
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - mStartTime >= mTimeout;
    }

    @Override
    protected void end() {
        Robot.liftSystem.stop();
    }
}
