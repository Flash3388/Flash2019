package frc.robot.subsystems;

import edu.flash3388.flashlib.robot.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class ClimbingSystem extends Subsystem {

    private DoubleSolenoid solenoid1;
    private DoubleSolenoid solenoid2;

    public ClimbingSystem() {
        this.solenoid1 = new DoubleSolenoid(0,1);
        this.solenoid1 = new DoubleSolenoid(2,3);

    }

    public void openPistons() {
        solenoid1.set(DoubleSolenoid.Value.kForward);
        solenoid2.set(DoubleSolenoid.Value.kForward);

    }

    public void closePiston1() {
        solenoid1.set(DoubleSolenoid.Value.kReverse);
    }

    public void closePiston2() {
        solenoid2.set(DoubleSolenoid.Value.kReverse);
    }



}
