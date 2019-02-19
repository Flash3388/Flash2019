package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ClimbDriveAction extends Action {

    public ClimbDriveAction() {
        requires(Robot.driveSystem);
    }

    @Override
    protected void initialize() {
        Robot.driveSystem.stop();
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
    }

    @Override
    protected void execute() {
        double val = Robot.righJoystick.getY();

        if(Mathf.constrained(val, -RobotMap.MIN_JOYSTICK_VALUE, RobotMap.MIN_JOYSTICK_VALUE))
            val = 0;
        
        Robot.climbSystem.drive(val);
    }
}