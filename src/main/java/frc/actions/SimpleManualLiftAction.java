package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class SimpleManualLiftAction extends Action{

    public SimpleManualLiftAction() {
        requires(Robot.liftSystem);
    }

    @Override
    protected void end() {
        Robot.liftSystem.stop();
    }

    @Override
    protected void execute() {
        double rt = Robot.xbox.RT.get();
        double lt = Robot.xbox.LT.get();

        if(rt > 0.1)
            Robot.liftSystem.rotate(RobotMap.LIFT_SPEED);
        else if(lt > 0.1)
            Robot.liftSystem.rotate(RobotMap.FALL_SPEED);
        else
            Robot.liftSystem.rotate(RobotMap.STALL_SPEED);
    }
}
