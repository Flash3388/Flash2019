package frc.actions;

import frc.robot.Robot;

public class TimedLiftAction extends TimedAction {

    public TimedLiftAction(double timeout) {
        super(timeout);
        requires(Robot.liftSystem);
    }

    @Override
    protected void execute() {
        Robot.liftSystem.lift();
    }

    @Override
    protected void end() {
        super.end();
        Robot.liftSystem.stop();
    }  
}