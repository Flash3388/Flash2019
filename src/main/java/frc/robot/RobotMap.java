package frc.robot;

public class RobotMap {
    public static final int FRONT_RIGHT_CHANNEL_FORWARD = 7;
    public static final int FRONT_RIGHT_CHANNEL_BACKWARD = 2;
    public static final int FRONT_LEFT_CHANNEL_FORWARD = 6;
    public static final int FRONT_LEFT_CHANNEL_BACKWARD = 0;
    public static final int BACK_CHANNEL_FORWARD = 5;
    public static final int BACK_CHANNEL_BACKWARD = 1;
    public static final int BACK_MOTOR = 5;

    public static final int FRONT_RIGHT_MOTOR = 2;
    public static final int FRONT_LEFT_MOTOR = 3;
    public static final int REAR_RIGHT_MOTOR = 6;
    public static final int REAR_LEFT_MOTOR = 4;

    public static final int HATCH_GRIPPER_CHANNEL_FORWARD = 3;
    public static final int HATCH_GRIPPER_CHANNEL_BACKWARD = 4;
    public static final double CAPTURE_SPEED = -0.4;
    public static final double RELEASE_SPEED = 0.8;

    public static final int ROLLER_GRIPPER_MOTOR = 7;

    public static final int RIGHT_LIFT_MOTOR = 9;
    public static final int LEFT_LIFT_MOTOR = 8;
    public static final int DOWN_SWITCH = 3;
    public static final int UP_SWITCH = 2;
    public static final double LIFT_SPEED = 0.65;
    public static final double FALL_SPEED = 0.05;
    public static final double STALL_SPEED = 0.2;

    public static final double DRIVE_LIMIT = 1.0;
    public static final double ROTATE_LIMIT = 1.0;

    public static final int[] ANGLE_SET = { -125, -90, -45, 0, 45, 90, 125 };
    public static final double TURNING_RATIO = 0.56;
    public static final double MIN_JOYSTICK_VALUE = 0.12;

    public static final double CAMERA_FOV = 65.0;
    public static final double CAMERA_WIDTH = 320.0;
    public static final double DEGREES_IN_A_CIRCLE = 360.0;

    public static final double WHEEL_DIAMETER = 15.24;
    public static final double PPR = 4096.0;
    public static final double REVERSE_PPR = 1.0/PPR;

}