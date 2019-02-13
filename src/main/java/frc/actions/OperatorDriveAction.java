package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;
import frc.subsystems.DriveSystem;

public class OperatorDriveAction extends Action {
    private final double MIN = -0.15;
    private final double MAX = 0.15;

    public OperatorDriveAction(){
        requires(Robot.driveTrain);
    }

    protected void initiallize() {
        Robot.driveTrain.stop();
    }

    @Override
    protected void execute() {
        double left = Robot.controller.getY();
        double right = Robot.controller.getX();

        if(!DriveSystem.inBounds(right, MIN, MAX))
            right = 0;
        
        if (!DriveSystem.inBounds(left, MIN, MAX))
            left = 0;
        
        Robot.driveTrain.arcadeDrive(left, right);
    }

    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }
}
