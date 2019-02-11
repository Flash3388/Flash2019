package frc.actions;

import frc.tables.TargetSelect;
import edu.flash3388.flashlib.robot.InstantAction;

public class TargetSelectAction extends InstantAction {
	private final TargetSelect mTargetSelect;
	private int mTargetNumber;

	public TargetSelectAction(TargetSelect targetSelect, int targetNumber) {
		mTargetSelect = targetSelect;
		mTargetNumber = targetNumber;
	}

	@Override
	protected void execute() {
		mTargetSelect.selectTarget(mTargetNumber);
	}

	@Override
	protected void end() {
	}
}
