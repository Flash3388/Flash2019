package frc.subsystems;

import edu.flash3388.flashlib.robot.Subsystem;

public class HatchSystem extends Subsystem {
    private final Piston mPiston;

    public HatchSystem(int forward, int backward) {
        mPiston = new Piston(0, forward, backward);
    }

    public void toggle(){
        mPiston.toggle();
        System.out.println("Toggling bitch");
    }

    public void grab() {
        mPiston.open();
    }

    public void discharge() {
        mPiston.close();
    }
}
