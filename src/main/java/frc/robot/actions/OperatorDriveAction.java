package frc.robot.actions;


import edu.flash3388.flashlib.robot.Action;

public class OperatorDriveAction extends Action {
    private double rightSpeed;
    private double leftSpeed;

    public OperatorDriveAction(double rightSpeed, double leftSpeed){
        this.rightSpeed = rightSpeed;
        this.leftSpeed = leftSpeed;

       // requires(Robot.driveTrain); misha al tishkah et ze!!!!!!!!!!!!!

    }

    protected void initiallize(){
        this.rightSpeed = 0.35;
        this.leftSpeed = 0.35;
    }

    @Override
    protected void execute() {
        initiallize();
    }

    @Override
    protected void end() {
        this.rightSpeed = 0;
        this.leftSpeed = 0;

    }
}
