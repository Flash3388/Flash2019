package frc.subsystems;

import edu.flash3388.flashlib.robot.Subsystem;

public class ClimbingSystem extends Subsystem {
    private final Piston mFrontPiston;
    private final Piston mBackPiston;

    public ClimbingSystem(int frontForward, int frontBackward, int backForward, int backBackward) {
        mFrontPiston = new Piston(frontForward, frontBackward);
        mBackPiston = new Piston(backForward, backBackward);
    }

    public void closeFront() {
        mFrontPiston.close();
    }

    public void openFront() {
        mFrontPiston.open();
    }

    public void closeBack() {
        mFrontPiston.close();
    }

    public void openBack() {
        mFrontPiston.open();
    }
}