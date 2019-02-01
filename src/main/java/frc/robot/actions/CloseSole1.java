package frc.robot.actions;

import edu.flash3388.flashlib.robot.Action;
import frc.robot.Robot;

public class CloseSole1 extends Action {

        public CloseSole1() {
            requires(Robot.climb);
        }

        @Override
        protected void execute() {
            Robot.climb.closePiston1();
        }

        @Override
        protected boolean isFinished() {
            return true;
        }

        @Override
        protected void end() {

        }



    }
