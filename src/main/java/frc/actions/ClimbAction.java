package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;

public class ClimbAction extends Action {
    Timer t = new Timer();

    public ClimbAction() {
        requires(Robot.climbSystem);
    }

    @Override
    protected void initialize() {
        t.reset();
        t.start();
        Robot.climbSystem.openBack();
        while (t.get() < 0.5) {
        }
        Robot.climbSystem.openFront();
        t.stop();
    }

    @Override
    protected void execute() {

    }

    private void doMove() {
        double speed = Robot.xbox.LeftStick.getY() * 0.8;

        if (speed < -0.16)
            Robot.climbSystem.drive(speed);
        else
            Robot.climbSystem.stop();
    }

    @Override
    protected void end() {
        Robot.climbSystem.stop();
    }
}
