/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.flash3388.flashlib.robot.FlashRobotUtil;
import edu.flash3388.flashlib.robot.HIDInterface;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;

public class FRCHIDInterface implements HIDInterface {

	private final DriverStation mDs;
	
	public FRCHIDInterface(DriverStation ds) {
		mDs = ds;
	}
	
	@Override
	public double getHIDAxis(int stick, int axis) {
		return mDs.getStickAxis(stick, axis);
	}

	@Override
	public boolean getHIDButton(int stick, int button) {
		return mDs.getStickButton(stick, button);
	}

	@Override
	public int getHIDPOV(int stick, int pov) {		
		return mDs.getStickPOV(stick, pov);
	}

	@Override
	public boolean isAxisConnected(int stick, int axis) {
		return mDs.getStickAxisCount(stick) > axis;
	}

	@Override
	public boolean isButtonConnected(int stick, int button) {
		return mDs.getStickButtonCount(stick) >= button;
	}

	@Override
	public boolean isHIDConnected(int stick) {
		return mDs.getStickAxisCount(stick) > 0;
	}

	@Override
	public boolean isPOVConnected(int stick, int pov) {
		return mDs.getStickPOVCount(stick) > pov;
	}

}
