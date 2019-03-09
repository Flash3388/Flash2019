package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import edu.wpi.first.wpilibj.Timer;

public abstract class TimedAction extends Action {
    private Timer mTimer;
    private double mTimeout;

    public TimedAction(double timeout) {
        mTimer = new Timer();

        mTimeout = timeout;
    }

    @Override
    protected void initialize() {
        mTimer.reset();
        mTimer.start();
    }

    protected double getTimePassed() {
        return mTimer.get();
    }

    @Override
    protected boolean isFinished() {
        return mTimer.get() >= mTimeout;
    }

    @Override
    protected void end() {
        mTimer.stop();
    }
}