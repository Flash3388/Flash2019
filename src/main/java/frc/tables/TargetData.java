package frc.tables;

public class TargetData {
    private double mAngle;
    private double mDistance;

    public TargetData(double angle, double distance) {
        mAngle = angle;
        mDistance = distance;
    }

    public double getAngle() {
        return mAngle;
    }

    public double getDistance() {
        return mDistance;
    }
}
