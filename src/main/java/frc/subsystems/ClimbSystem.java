package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.flash3388.flashlib.robot.Subsystem;

public class ClimbSystem extends Subsystem {
    private final Piston mFrontRighPiston;
    private final Piston mFrontLeftPiston;
    private final Piston mBackPiston;

    private final TalonSRX mBackMotor;

    public ClimbSystem(int frontRightForward, int frontRightBackward, int frontLeftForward, int frontLeftBackward,
            int backForward, int backBackward, int backMotor) {
        mFrontLeftPiston = new Piston(0, frontLeftForward, frontLeftBackward);
        mFrontRighPiston = new Piston(0, frontRightForward, frontRightBackward);
        mBackPiston = new Piston(0, backForward, backBackward);

        mBackMotor = new TalonSRX(backMotor);
        mBackMotor.setInverted(true);
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