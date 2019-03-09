package frc.actions;

import edu.flash3388.flashlib.robot.Action;
import edu.flash3388.flashlib.robot.InstantAction;
import frc.robot.Robot;

public class CancelAllCurrentRunningActionsAction extends InstantAction {
    private Action[] mActionsToStop;
    
    public CancelAllCurrentRunningActionsAction(Action... actionsToStop) {
        mActionsToStop = actionsToStop;
    } 
    
    @Override
    protected void execute() {
        Robot.driveSystem.cancelCurrentAction();
        Robot.climbSystem.cancelCurrentAction();
        Robot.rollerGripperSystem.cancelCurrentAction();
        Robot.hatchSystem.cancelCurrentAction();
        Robot.liftSystem.cancelCurrentAction();

        for (Action action : mActionsToStop) {
            if (action.isRunning() && !action.isCanceled()) {
                action.cancel();
            }
        }
    }
}