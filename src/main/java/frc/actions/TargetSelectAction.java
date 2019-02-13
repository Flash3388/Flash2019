package frc.actions;

import frc.tables.TargetSelectTable;
import edu.flash3388.flashlib.robot.InstantAction;

public class TargetSelectAction extends InstantAction {
	private final TargetSelectTable mTargetSelectTable;
	private int mTargetNumber;

	public TargetSelectAction(TargetSelectTable targetSelectTable, int targetNumber) {
		mTargetSelectTable = targetSelectTable;
		mTargetNumber = targetNumber;
	}

	@Override
	protected void execute() {
		mTargetSelectTable.selectTarget(mTargetNumber);
	}

	@Override
	protected void end() {
	}
}
