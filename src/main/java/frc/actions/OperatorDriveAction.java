package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;

public class OperatorDriveAction extends Action {
    private final double MIN = -0.12;
    private final double MAX = 0.12;

    public OperatorDriveAction(){
        requires(Robot.driveTrain);
    }

    protected void initiallize() {
        Robot.driveTrain.stop();
    }

    @Override
    protected void execute() {
        double left = Robot.lefJoystick.getY();
        double right = Robot.righJoystick.getY();

        if(right>= MIN && right <= MAX)
            right = 0;
        
        if (left >= MIN && left <= MAX)
            left = 0;
    
        Robot.driveTrain.tankDrive(right, left); //fuck off Tom
    }

    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }
}
