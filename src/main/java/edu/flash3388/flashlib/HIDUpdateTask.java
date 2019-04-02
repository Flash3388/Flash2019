package edu.flash3388.flashlib;

import edu.flash3388.flashlib.robot.FlashRobotUtil;
import edu.flash3388.flashlib.robot.RobotFactory;

public class HIDUpdateTask implements Runnable {

    @Override
    public void run() {
        if (!RobotFactory.getImplementation().isDisabled()) {
            FlashRobotUtil.updateHID();
        }
    }
}
