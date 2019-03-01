package frc.actions;

import edu.flash3388.flashlib.robot.InstantAction;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

public class ClimbAction extends InstantAction {
    Timer t = new Timer();
    boolean finished = false;

    public ClimbAction() {
        requires(Robot.climbSystem);
    }

    @Override
    protected void initialize() {
        finished = false;
        t.reset();
        t.start();
    }

    @Override
    protected void execute() {
        Robot.climbSystem.openBack();
        if (t.get() > 0.5) {
            Robot.climbSystem.openFront();
            System.out.println("HERE");
            finished = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }

    @Override
    protected void end() {
        t.stop();
    }
}
