package frc.actions;

import frc.robot.Robot;

import edu.flash3388.flashlib.robot.Action;

public class CaptureAction extends Action {

	public CaptureAction() {
		requires(Robot.rollerGripperSystem);
	}

	@Override
	protected void execute() {
		Robot.rollerGripperSystem.capture();
	}

	@Override
	protected void end() {
		Robot.rollerGripperSystem.stop();
	}
}
