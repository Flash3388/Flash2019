/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.flash3388.flashlib.robot.HIDInterface;
import edu.flash3388.flashlib.robot.frc.*;
import edu.flash3388.flashlib.robot.frc.IterativeFRCRobot;
import edu.flash3388.flashlib.robot.hid.XboxController;
import edu.flash3388.flashlib.robot.InstantAction;
import edu.flash3388.flashlib.robot.RobotFactory;
import edu.wpi.first.wpilibj.DriverStation;




/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends IterativeFRCRobot {
  

  XboxController xbox;

	@Override
	protected void preInit(RobotInitializer initializer) {
		initializer.initFlashboard = false;
		/*
		 * preInit is used by IterativeFRCRobot to allow users to customize FlashLib operations.
		 * The received class contains simple variable, we can edit those to change initialization 
		 * parameters for FlashLib.
		 * The default implementation of this method is empty, so override it only when custom initialization
		 * is wanted.
		 */
		
		/*
		 * Enables the robot logs to be used. This allows FlashLib operations to log data into
		 * the main FlashLib log.
		 * By default, standard logging is disabled. 
		 */
		//initializer.standardLogs = true;
		
		/*
		 * Sets whether or not to enable the power log. This allows the control loop
		 * to track power issues in voltage and power draw and log them.
		 * By default, power logging is disabled.
		 */
		//initializer.logPower = true;
		
		/*
		 * Sets whether or not to enable auto HID updates. FlashLib's HID buttons need
		 * to be refreshed to automatically activate actions attached. When allowed,
		 * an update tasks for HID buttons is added to FlashLib's scheduler. This updates buttons
		 * which are a part of controllers from the HID package.
		 * By default, this is enabled.
		 */
		//initializer.autoUpdateHid = false;
		
		/*
		 * Sets whether or not IterativeFRCRobot's robot loop should run the FlashLib
		 * scheduler. If this is disabled, the scheduling system will not function.
		 * By default, this is enabled.
		 */
		//initializer.runScheduler = false;
		
		/*
		 * Sets whether or not to initialize Flashboard. If disabled, Flashboard control will
		 * not be initialized and cannot be used.
		 * By default, Flashboard control is initialized.
		 */
		//initializer.initFlashboard = false;
		
		/*
		 * If Flashboard control is set to initialize, it is possible to edit the
		 * initialization parameters. This variable holds an object of FlashboardInitData.
		 * If the variable is null, Flashboard control will not be initialized.
		 */
		//initializer.flashboardInitData;
	}
	@Override
	protected void initRobot() {
		/*
		 * initRobot is called as soon as all FlashLib systems are ready for use. So here we should
		 * perform initialization to our robot systems. It is important that initialization to system
		 * does not occur before this method is called because FlashLib might not be ready yet.
		 */
		RobotFactory.setHIDInterface(new FRCHIDInterface(DriverStation.getInstance()));


   		 xbox = new XboxController(0);
   		 InstantAction printAct = new InstantAction(){
   		   @Override
				protected void execute() {
				System.out.println("A: PRESSED");
			}
 		   };
    
    System.out.println("WE MADE IT THIS FAR");
    xbox.A.whenPressed(printAct);
    xbox.B.whenPressed(printAct);
    xbox.X.whenPressed(printAct);
    xbox.Y.whenPressed(printAct);

    System.out.println("ITS FUCKING NOT");


	}

	@Override
	protected void disabledInit() {
    System.out.println("bakakaet");
    
    /*
		 * disabledInit is called when the robot enters disabled mode. Here we should prepare our
		 * robot for idle mode which means disabling and stopping operation of systems.
		 */
	}
	@Override
	protected void disabledPeriodic() {
		/*
		 * This method is called periodically (~10ms) while the robot is in disabled mode.
		 * Generally no code should be here since disabled mode should insure that the robot does nothing
		 * thus making it safe. But if wanted this method could be used for things like data updates, but actuators
		 * should not be operated.
		 */
	}

	@Override
	protected void teleopInit() {
		/*
		 * teleopInit is called when the robot enters teleop mode. Here we should prepare our
		 * robot for operator control.
		 */
	}
	@Override
	protected void teleopPeriodic() {
		/*
		 * This method is called periodically (~10ms) while the robot is in teleop mode. In here
		 * we can perform periodic operation for teleop mode.
		 */
	}

	@Override
	protected void autonomousInit() {
		/*
		 * autonomousInit is called when the robot enters autonomous mode. Here we should prepare our
		 * robot for autonomous control.
		 */
	}
	@Override
	protected void autonomousPeriodic() {
		/*
		 * This method is called periodically (~10ms) while the robot is in autonomous mode. In here
		 * we can perform periodic operation for autonomous mode.
		 */
	}
}
