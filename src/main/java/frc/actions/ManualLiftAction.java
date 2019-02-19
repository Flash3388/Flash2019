package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ManualLiftAction extends Action {

    public ManualLiftAction() {
        requires(Robot.liftSystem);
    }

    @Override
    protected void end() {
        Robot.liftSystem.stop();
    }

    @Override
    protected void execute() {
        double leftT = Robot.xbox.LT.get();
        double rightT = Robot.xbox.RT.get();

        if(rightT > 0.1)
            Robot.liftSystem.lift();
        else if(leftT > 0.1)
            Robot.liftSystem.fall();
        else
            Robot.liftSystem.stall();
    }
}