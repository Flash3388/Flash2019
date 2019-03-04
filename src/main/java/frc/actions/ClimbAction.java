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

    private boolean isAllowedToDrive()
    {
        boolean frontOnClear = !Robot.climbSystem.isFrontSensorOverStep() && !Robot.climbSystem.isFrontClosed();
        boolean frontPistonIsUp = Robot.climbSystem.isFrontSensorOverStep() && Robot.climbSystem.isFrontClosed();
        boolean rearOnClear = !Robot.climbSystem.isRearSensorOverStep() || 
                              (Robot.climbSystem.isFrontSensorOverStep() && Robot.climbSystem.isBackClosed());

        System.out.println("frontOnClear:" + frontOnClear + " frontPistonIsUp:" + frontPistonIsUp + " rearOnClear:" + rearOnClear);
        return frontOnClear && frontPistonIsUp && rearOnClear;
    }

    @Override
    protected void execute() {
        double speed = Robot.xbox.LeftStick.getY() * 0.8;

        if(speed>0.16 && isAllowedToDrive())
            Robot.climbSystem.drive(speed);
        else
            Robot.climbSystem.stop();
    }
    @Override
    protected void end() {
        Robot.climbSystem.stop();
    }
}
