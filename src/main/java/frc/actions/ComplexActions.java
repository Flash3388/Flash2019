package frc.actions;

import edu.flash3388.flashlib.robot.ActionGroup;
import frc.robot.Robot;

public class ComplexActions {
    public static ActionGroup driveToTarget() {
        return new ActionGroup()
            .addSequential(new RotationPIDAction(0.5, 500, Robot.driveTrain.getVisionAngleDeg()))
            .addSequential(new SmartDriveToTarget(0.5, 500));
    } 
}