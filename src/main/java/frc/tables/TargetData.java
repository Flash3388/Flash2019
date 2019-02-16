package frc.tables;

public class TargetData {
    private double mXOffset;
    private double mDistance;

    public TargetData(Double xOffset, Double distance) {
        mXOffset = xOffset;
        mDistance = distance;
    }

    public double getXOffset() {
        return mXOffset;
    }

    public double getDistance() {
        return mDistance;
    }
}