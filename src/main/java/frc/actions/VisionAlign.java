package frc.actions;

import edu.flash3388.flashlib.math.Mathf;
import edu.flash3388.flashlib.robot.Action;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class VisionAlign extends Action {
    private final double mMargin;

    private TimeStampRecorder jhonson;

    private double mInitialDistanceToTarget;
    //private double mStartTimeSeconds;
    
    public VisionAlign(double margin) {
        requires(Robot.driveSystem);

        mMargin = margin;

        jhonson = new TimeStampRecorder();
    }

    @Override
    protected void initialize() {
        //mStartTimeSeconds = Timer.getFPGATimestamp();
        //Robot.cameraExposure.set(20);

        Robot.driveSystem.resetGyro();
        Robot.driveSystem.rotatePID.reset();
        Robot.driveSystem.rotatePID.setEnabled(true);
        Robot.driveSystem.rotationSetPoint.set(0);

        //mInitialDistanceToTarget = Robot.driveSystem.getVisionDistanceCm() - RobotMap.CAMERA_DISTANCE_FROM_FRONT_CM; //Robot.driveSystem.getVisionDistanceCm()
        mInitialDistanceToTarget = Robot.driveSystem.alignDistance.get() - RobotMap.CAMERA_DISTANCE_FROM_FRONT_CM;
        NetworkTableInstance.getDefault().getEntry("testinitial").setDouble(mInitialDistanceToTarget);
        //Robot.driveSystem.resetDistance();
    }

    @Override
    protected void end() {
        Robot.driveSystem.stop();
        Robot.cameraExposure.set(46);
        Robot.hatchSystem.toggle();
        System.out.println("Done");
    }

    @Override
    protected void execute() {
        /*if (Timer.getFPGATimestamp() - mStartTimeSeconds <= 0.1) {
            mInitialDistanceToTarget = Robot.driveSystem.getVisionDistanceCm() - RobotMap.CAMERA_DISTANCE_FROM_FRONT_CM;
        }*/

        jhonson.append(Robot.clock.currentTimeMillis(), Robot.driveSystem.getAngle());

        double jhonsonAngle = jhonson.getAngleAt(Robot.driveSystem.getVisionTime());
        double distance = distance(Robot.driveSystem.getAngle(), jhonsonAngle);

        double setpoint = Robot.driveSystem.getVisionAngle() - distance;
                
        Robot.driveSystem.currectRotationSource.set(setpoint);
        double rotVal = Robot.driveSystem.rotatePID.calculate();
        System.out.println();
        if (inRotationThreshold())
            rotVal = 0;

        Robot.driveSystem.arcadeDrive((Robot.righJoystick.getY()+Robot.lefJoystick.getY())/2,-rotVal);
    }

    @Override
    protected boolean isFinished() {
        double distancePassed = (Robot.driveSystem.getLeftDistance() + Robot.driveSystem.getRightDistance()) * 0.5;
        //System.out.println(mInitialDistanceToTarget + "|" + distancePassed);
        NetworkTableInstance.getDefault().getEntry("testdistance").setDouble(distancePassed);
        return distancePassed >= mInitialDistanceToTarget;
    }

    private boolean inRotationThreshold() {
        double current = Robot.driveSystem.rotatePID.getPIDSource().pidGet();
        return Mathf.constrained(current, -mMargin, mMargin);
    }

    private static double distance(double alpha, double beta) {
        double phi = Math.abs(beta - alpha) % 360; 
        double distance = phi > 180 ? 360 - phi : phi;
        return distance;
    }
}