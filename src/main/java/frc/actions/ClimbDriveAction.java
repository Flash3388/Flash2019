package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;
import frc.subsystems.DriveSystem;

public class ClimbDriveAction extends Action {
    private final double MIN = -0.12;
    private final double MAX = 0.12;

    public ClimbDriveAction() {
        requires(Robot.driveTrain);
    }

    @Override
    protected void initialize() {
        Robot.driveTrain.stop();
    }

    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }

    @Override
    protected void execute() {
        // double val = Robot.rightStick.getY();

        // if(DriveSystem.inBounds(val, MIN, MAX))
        //     val = 0;
        
        // Robot.driveTrain.climbDrive(val);
    }
}