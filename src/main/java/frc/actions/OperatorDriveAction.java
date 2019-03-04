package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;

import frc.robot.Robot;
import frc.robot.RobotMap;

public class OperatorDriveAction extends Action {

    public OperatorDriveAction(){
        requires(Robot.driveSystem);
    }

    protected void initiallize() {
        Robot.driveSystem.stop();
    }

    @Override
    protected void execute() {
        double left = Robot.lefJoystick.getY();
        double right = Robot.righJoystick.getY();

        if (Robot.lefJoystick.getButton(3).get()) {
            right = left;
        }

        if(Mathf.constrained(right, -RobotMap.MIN_JOYSTICK_VALUE, RobotMap.MIN_JOYSTICK_VALUE))
            right = 0;
        
        if (Mathf.constrained(left, -RobotMap.MIN_JOYSTICK_VALUE, RobotMap.MIN_JOYSTICK_VALUE))
            left = 0;
    
        Robot.driveSystem.tankDrive(right, left); //fuck off Tom
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
    }
}
