package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;

public class ClimbAloneAction extends Action {

    public ClimbAloneAction(){
        requires(Robot.climbSystem);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {

        Robot.climbSystem.openBack();
        Robot.climbSystem.openFront();

        while (Robot.climbSystem.isBackClosed() && Robot.climbSystem.isFrontClosed()){}

        while (Robot.climbSystem.isFrontSensorOverStep()){ // moving until front sensor gets false value (gets to the platform)
            Robot.climbSystem.drive(0.25);
        }

        Robot.climbSystem.closeFront(); // closing front pistons

        while (!Robot.climbSystem.isFrontClosed()){}

        while (Robot.climbSystem.isRearSensorOverStep()){ // moving the robot to the stage
            Robot.climbSystem.drive(0.2);
        }

        Robot.climbSystem.closeBack(); // closing back pistons and finishes climb

        while (!Robot.climbSystem.isBackClosed()){}
    }

    @Override
    protected void end() {
        Robot.climbSystem.stop();
    }
}
