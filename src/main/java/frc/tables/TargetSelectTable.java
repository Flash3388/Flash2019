package frc.tables;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.TableEntryListener;

public class TargetSelectTable implements TableEntryListener {
    private final static String TARGET_SELECTION_TABLE = "target_selection_table";
    private final static String SELECT_NEXT_TARGET_KEY = "select_next_target_key";
    private final static String SELECT_TARGET_NUMBER_KEY = "select_target_number_key";
    public final static int NUM_OF_POSSIBLE_TARGETS = 3;

    private NetworkTable mTargetSelectTable;
    private int mSelectNextTargetEntryListenerHandle;
    private int mSelectTargetNumberListenerHandle;

    private TargetSelectListener mTargetSelectListener; // support single listener

    public TargetSelectTable() {
        mTargetSelectTable = NetworkTableInstance.getDefault().getTable(TARGET_SELECTION_TABLE);
        mTargetSelectTable.getEntry(SELECT_TARGET_NUMBER_KEY).setDouble(-1);
        mTargetSelectTable.getEntry(SELECT_NEXT_TARGET_KEY).setBoolean(false);
    }
    
    public void selectNextTarget() {
        System.out.println("next bitch");
        mTargetSelectTable.getEntry(SELECT_NEXT_TARGET_KEY).setBoolean(true);
    }

    public void selectTarget(int targetNumber) {
        if (targetNumber >= 0 && targetNumber < NUM_OF_POSSIBLE_TARGETS) {
            if (mTargetSelectTable.getEntry(SELECT_TARGET_NUMBER_KEY).getDouble(targetNumber) != targetNumber) 
                mTargetSelectTable.getEntry(SELECT_TARGET_NUMBER_KEY).setDouble(targetNumber);
            else
                selectNextTarget();
        }
    }

    public void registerSelectTargetListener(TargetSelectListener targetSelectListener) {
        mTargetSelectListener = targetSelectListener;

        if (targetSelectListener != null) {
            mSelectNextTargetEntryListenerHandle = mTargetSelectTable.addEntryListener(SELECT_NEXT_TARGET_KEY, this, EntryListenerFlags.kUpdate);
            mSelectTargetNumberListenerHandle = mTargetSelectTable.addEntryListener(SELECT_TARGET_NUMBER_KEY, this, EntryListenerFlags.kUpdate);
        }
    }

    public void unregisterSelectTargetListener(TargetSelectListener targetSelectListener) {
        if (targetSelectListener != null) {
            mTargetSelectTable.removeEntryListener(mSelectNextTargetEntryListenerHandle);
            mTargetSelectTable.removeEntryListener(mSelectTargetNumberListenerHandle);
        }
    }

    public void valueChanged(NetworkTable table,
                      java.lang.String key,
                      NetworkTableEntry entry,
                      NetworkTableValue value,
                      int flags) {
        if (key.equals(SELECT_NEXT_TARGET_KEY)) {
            mTargetSelectListener.OnNextTargetSelectPressed();
        } else if (key.equals(SELECT_TARGET_NUMBER_KEY)) {
            mTargetSelectListener.onTargetSelectPressed((int)value.getDouble());
        }
    }
}
