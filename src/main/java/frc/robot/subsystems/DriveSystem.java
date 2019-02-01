package frc.robot.subsystems;


import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.devices.TalonSRX;
import edu.flash3388.flashlib.robot.systems.TankDriveSystem;


public class DriveSystem extends Subsystem implements TankDriveSystem {
    private TalonSRX rearRight;
    private TalonSRX frontRight;
    private TalonSRX rearLeft;
    private TalonSRX frontLeft;

    protected DriveSystem(){
        rearRight = new TalonSRX(1);
        frontRight = new TalonSRX(2);
        frontLeft = new TalonSRX(3);
        rearLeft = new TalonSRX(4);
    }



    @Override
    public void tankDrive(double right, double left) {
        rearRight.set(right);
        frontRight.set(right);
        frontLeft.set(left);
        rearLeft.set(left);
    }

    @Override
    public void stop() {
        rearRight.set(0);
        rearLeft.set(0);
        frontRight.set(0);
        frontLeft.set(0);
    }
}
