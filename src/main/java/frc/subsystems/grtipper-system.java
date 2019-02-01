package org.usfirst.frc.team3388.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.flash3388.flashlib.robot.Subsystem;

public class RollerGripper extends Subsystem {
	private WPI_TalonSRX talon;

	public RollerGripper() {
		this.talon = new WPI_TalonSRX(3);
	}

	public void capture(double speed) {
    this.talon.set(speed);
	}

  public void release(double speed) {
    this.talon.set(-speed);
  }

	public void stop() {
		this.talon.set(0);
	}

}
