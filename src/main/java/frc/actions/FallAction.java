// package frc.actions;

// import edu.flash3388.flashlib.robot.Action;
// import frc.subsystems.LiftSystem;
// import frc.robot.Robot;

// public class FallAction extends Action {
//     public FallAction() {
//         requires(Robot.liftSystem);
//     }

//     @Override
//     protected void end() {
//     }

//     @Override
//     protected void execute() {
//         Robot.liftSystem.rotate(LiftSystem.FALL_SPEED);
//     }

//     @Override
//     protected boolean isFinished() {
//         return Robot.liftSystem.isBottom();
//     }
// }