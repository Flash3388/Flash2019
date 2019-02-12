package frc.subsystems;

import edu.flash3388.flashlib.robot.Subsystem;

public class ClimbingSystem extends Subsystem {
    public final Piston mFrontRighPiston;
    public final Piston mFrontLeftPiston;
    public final Piston mBackPiston;

    public ClimbingSystem(int frontRightForward, int frontRightBackward, int frontLeftForward, int frontLeftBackward, int backForward, int backBackward) {
        mFrontLeftPiston = new Piston(frontLeftForward, frontLeftBackward);
        mFrontRighPiston = new Piston(frontRightForward, frontRightBackward);
        mBackPiston = new Piston(backForward, backBackward);
    }

    public void closeFrontRight() {
        mFrontRighPiston.close();
    }

    public void closeFrontLeft() {
        mFrontLeftPiston.close();
    }
    
    public void openFrontRight() {
        mFrontRighPiston.open();
    }

    public void openFrontLeft() {
        mFrontLeftPiston.open();
    }

    public void closeFront() {
        closeFrontRight();
        closeFrontLeft();
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