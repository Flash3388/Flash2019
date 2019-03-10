package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;

import frc.robot.Robot;
import frc.robot.RobotMap;

import frc.subsystems.DriveSystem;

public class SmartDriveToTarget extends Action {
    private static final double MAX_DISTANCE = 400.0;

    private double mRotateMargin;
    private boolean isRight = true;

    private double mRatio;

    public SmartDriveToTarget(double rotateMargin, double distanceToTarget,
            double targetAngle) {
        requires(Robot.driveSystem);

        mRotateMargin = rotateMargin;

        Robot.driveSystem.distanceSetPoint.set(distanceToTarget);
        
        System.out.println(targetAngle);
        Robot.driveSystem.setDistancePIDSource(targetAngle);
        if (targetAngle < 0) {
            isRight = false;
            System.out.println("LEFT");
        }
    }

    @Override
    protected void initialize() {
        Robot.driveSystem.resetDistance();

        Robot.driveSystem.distancePID.setEnabled(true);
        Robot.driveSystem.distancePID.reset();

        Robot.driveSystem.rotatePID.setEnabled(true);
        Robot.driveSystem.rotatePID.reset();

        System.out.println(Robot.driveSystem.rotatePID.getSetPoint());
        System.out.println(Robot.driveSystem.rotatePID.getPIDSource().pidGet());
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
    }

    @Override
    protected void execute() {
        double distanceResult = -Robot.driveSystem.distancePID.calculate();
        double rotationResult = Robot.driveSystem.rotatePID.calculate();
        double distance = Robot.driveSystem.distancePID.getPIDSource().pidGet();

        if(distance>Robot.driveSystem.distancePID.getSetPoint().get())
            mRatio = 1;
        else
            mRatio = distance / Robot.driveSystem.distancePID.getSetPoint().get();
        
        if(isRight)
            if (mRatio < -RobotMap.TURNING_RATIO_RIGHT) {
                mRatio = -RobotMap.TURNING_RATIO_RIGHT;
            }
        else
            if (mRatio < RobotMap.TURNING_RATIO_LEFT)
                mRatio -= RobotMap.TURNING_RATIO_LEFT;

        Robot.driveSystem.arcadeDrive(distanceResult * (1.0 - mRatio)* RobotMap.DRIVING_MODIFIER, rotationResult * mRatio * RobotMap.TURNING_MODIFIER);
    }

    @Override
    protected boolean isFinished() {
        System.out.println(mRatio);
        return mRatio >=0.61;
    }

    private boolean inRotationThreshold() {
        double margin = mRotateMargin;
        double current = Robot.driveSystem.rotatePID.getPIDSource().pidGet();
        return Mathf.constrained(Robot.driveSystem.rotationSetPoint.get() - current, -margin, margin);
    }
}
