package frc.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.flash3388.flashlib.robot.Subsystem;

public class ClimbingSystem extends Subsystem {
    private final Piston mFrontRighPiston;
    private final Piston mFrontLeftPiston;
    private final Piston mBackPiston;

    private final TalonSRX mBackMotor;

    public ClimbingSystem(int frontRightForward, int frontRightBackward, int frontLeftForward, int frontLeftBackward, int backForward, int backBackward, int backMotor) {
        mFrontLeftPiston = new Piston(frontLeftForward, frontLeftBackward);
        mFrontRighPiston = new Piston(frontRightForward, frontRightBackward);
        mBackPiston = new Piston(backForward, backBackward);

        mBackMotor = new TalonSRX(backMotor);
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
}