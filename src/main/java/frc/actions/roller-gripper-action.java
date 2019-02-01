package org.usfirst.frc.team3388.robot.actions;

import org.usfirst.frc.team3388.robot.Robot;

import edu.flash3388.flashlib.robot.Action;

public class RollerGripperAction extends Action {
	private double speed;

	public RollerGripperAction(double speed) {
		this.speed = speed;
		requires(Robot.rollerGripper);
	}

	@Override
	protected void execute() {
		Robot.rollerGripper.capture(speed);
	}

//hello
	@Override
	protected void end() {
		Robot.rollerGripper.stop();
	}
}
