package frc.actions;

import java.util.ArrayList;
import java.util.List;

public class TimeStampRecorder {
    private List<AngularFrame> frames;    

    public TimeStampRecorder() {
        frames = new ArrayList<AngularFrame>();
    }

    public void append(long timestamp, double angle) {
        frames.add(new AngularFrame(timestamp, angle));
    }

    public void clear() {
        frames.clear();
    }

    public double getCorrespondingAngle(long timestamp) {
        if (timestamp <= frames.get(0).timestamp)
            return frames.get(0).timestamp;
        if (timestamp >= frames.get(frames.size() - 1).timestamp)
            return frames.get(frames.size()).timestamp;

        int i = 0, j = frames.size(), mid = 0;
        while (i < j) {
            mid = (i + j) / 2;

            if (frames.get(mid).timestamp == timestamp)
                return frames.get(mid).timestamp;
            if (timestamp < frames.get(mid).timestamp) {
                if (mid > 0 && timestamp > frames.get(mid-1).timestamp)
                    return getClosest(frames.get(mid-1), frames.get(mid), timestamp);
                j = mid;
            } else {
                if (mid < frames.size() - 1 && timestamp < frames.get(mid+1).timestamp)
                    return getClosest(frames.get(mid), frames.get(mid+1), timestamp);
                i = mid + 1;
            }
        }

        return frames.get(mid).timestamp;
    }

    private static double getClosest(AngularFrame frame1, AngularFrame frame2, long timestamp) {
        if (timestamp- frame1.timestamp >= frame2.timestamp - timestamp)
            return frame2.angle;
        else
            return frame1.angle;
    }

    private class AngularFrame {
        public long timestamp;
        public double angle;

        public AngularFrame(long timestamp, double angle) {
            this.timestamp = timestamp;
            this.angle = angle;
        }
    }
}
