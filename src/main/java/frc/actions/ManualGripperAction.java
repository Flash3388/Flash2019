package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;

public class ManualGripperAction extends Action {

    public ManualGripperAction() {
        requires(Robot.rollerGripperSystem);
    }

    @Override
    protected void execute() {
        double speed = Robot.xbox.RightStick.AxisY.get();

        if (Math.abs(speed) < 0.2) {
            if (Math.abs(Robot.rollerGripperSystem.getCurrentSpeed()) > 0.05) {
                cancel();
            }
            return;
        }

        if (speed > 0.0) {
            Robot.rollerGripperSystem.capture();
        } else {
            Robot.rollerGripperSystem.release();
        }
    }

    @Override
    protected void end() {
        Robot.rollerGripperSystem.startSlowStop();
    }
}