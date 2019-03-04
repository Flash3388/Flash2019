package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.flash3388.flashlib.robot.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;

public class ClimbSystem extends Subsystem {
    private final Piston mFrontRighPiston;
    private final Piston mFrontLeftPiston;
    private final Piston mBackPiston;

    private final TalonSRX mBackMotor;

    private final DigitalInput closeRight;
    private final DigitalInput closeLeft;
    private final DigitalInput closeBack;

    private DigitalInput frontSensor;
	private DigitalInput rearSensor;

    public ClimbSystem(int frontRightForward, int frontRightBackward, int frontLeftForward, int frontLeftBackward,
            int backForward, int backBackward, int backMotor, int closeRightSensor, int closeLeftSensor,
            int closeBackSensor) {
        mFrontLeftPiston = new Piston(0, frontLeftForward, frontLeftBackward);
        mFrontRighPiston = new Piston(0, frontRightForward, frontRightBackward);
        mBackPiston = new Piston(0, backForward, backBackward);

        mBackMotor = new TalonSRX(backMotor);
        mBackMotor.setInverted(true);

        closeRight = new DigitalInput(closeRightSensor);
        closeLeft = new DigitalInput(closeLeftSensor);
        closeBack = new DigitalInput(closeBackSensor);

        frontSensor = new DigitalInput(7);
		rearSensor = new DigitalInput(8);
    }
    
    public boolean isFrontClosed() {
        return closeRight.get() && closeLeft.get();
    }

    public boolean isBackClosed() {
        return !closeBack.get();
    }

    public boolean isFrontSensorOverStep() {
        return frontSensor.get();
    }

    public boolean isRearSensorOverStep() {
        return rearSensor.get();
    }

    public void switchBack() {
        mBackPiston.toggle();
    }

    public void switchLeftFront() {
        mFrontLeftPiston.toggle();
    }

    public void switchRightFront() {
        mFrontRighPiston.toggle();
    }

    public void switchAll() {
        mFrontLeftPiston.toggle();
        mFrontRighPiston.toggle();
        mBackPiston.toggle();
    }

    public void closeFront() {
        mFrontRighPiston.close();
        mFrontLeftPiston.close();
    }

    public void openFront() {
        mFrontLeftPiston.open();
        mFrontRighPiston.open();
    }

    public void closeBack() {
        mBackPiston.close();
    }

    public void openBack() {
        mBackPiston.open();
    }

    public void drive(double speed) {
        mBackMotor.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        drive(0);
    }
}