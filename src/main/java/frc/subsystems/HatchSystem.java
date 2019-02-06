package frc.subsystems;

import edu.flash3388.flashlib.robot.Subsystem;

public class HatchSystem extends Subsystem {
    private final Piston mPiston;

    public HatchSystem(int forward, int backward) {
        mPiston = new Piston(forward, backward);
    }

    public void grab() {
        mPiston.open();
    }

    public void discharge() {
        mPiston.close();
    }
}