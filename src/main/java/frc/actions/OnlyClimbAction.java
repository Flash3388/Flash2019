package frc.actions;

import edu.flash3388.flashlib.robot.Action;

import edu.wpi.first.wpilibj.Timer;

import frc.robot.Robot;

public class OnlyClimbAction extends Action {
    Timer t = new Timer();

    public OnlyClimbAction() {
        requires(Robot.climbSystem);
    }

    @Override
    protected void initialize() {
        t.reset();
        t.start();
        Robot.climbSystem.openBack();
    }

    @Override
    protected void execute() {}

    @Override
    protected void end() {
        Robot.climbSystem.openFront();
        t.stop();
    }

    @Override
    protected boolean isFinished() {
        return t.get() >= 0.5;
    }
}