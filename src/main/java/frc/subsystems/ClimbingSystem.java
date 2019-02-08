package frc.subsystems;

import edu.flash3388.flashlib.robot.Subsystem;

public class ClimbingSystem extends Subsystem {
    private final Piston mFrontRighPiston;
    private final Piston mFrontLeftPiston;
    private final Piston mBackPiston;

    public ClimbingSystem(int frontRightForward, int frontRightBackward, int frontLeftForward, int frontLeftBackward, int backForward, int backBackward) {
        mFrontLeftPiston = new Piston(frontLeftForward, frontLeftBackward);
        mFrontRighPiston = new Piston(frontRightForward, frontRightBackward);
        mBackPiston = new Piston(backForward, backBackward);
    }

    public void closeFront() {
        mFrontLeftPiston.close();
        mFrontRighPiston.close();
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