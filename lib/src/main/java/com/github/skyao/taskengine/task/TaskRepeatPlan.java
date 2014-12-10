package com.github.skyao.taskengine.task;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * task repeat plan about how to execute this task repeatedly.
 *
 * @author Sky Ao
 */
public class TaskRepeatPlan {
    private int maxExecuteTimes;
    private int executedTimes;
    private long interval;
    private long deadline;

    /**
     * get max execute times.
     *
     * @return max execute times
     */
    public int getMaxExecuteTimes() {
        return maxExecuteTimes;
    }

    /**
     * set max execute times.
     *
     * @param maxExecuteTimes max execute times, 1 means no repeat
     * @throws java.lang.IllegalArgumentException if max repeat times is not greater than 1
     */
    public void setMaxExecuteTimes(int maxExecuteTimes) {
        checkArgument(maxExecuteTimes > 1, "max repeat times should greater than 1: maxExecuteTimes=" + maxExecuteTimes);

        this.maxExecuteTimes = maxExecuteTimes;
    }

    /**
     * get interval.
     *
     * @return interval in milliseconds
     */
    public long getInterval() {
        return interval;
    }

    /**
     * set interval in milliseconds
     *
     * @param interval interval in milliseconds, should greater than zero
     * @throws java.lang.IllegalArgumentException if interval is not greater than 0
     */
    public void setInterval(long interval) {
        checkArgument(interval >= 0, "interval should greater than or equal to zero: interval=" + interval);

        this.interval = interval;
    }

    /**
     * get deadline.
     *
     * @return deadline in timestamp
     */
    public long getDeadline() {
        return deadline;
    }

    /**
     * set deadline in timestamp.
     *
     * @param deadline deadline in timestamp, should greater than zero
     * @throws java.lang.IllegalArgumentException if deadline is not greater than 0
     */
    public void setDeadline(long deadline) {
        checkArgument(deadline > 0, "deadline should greater than 0: deadline=" + deadline);

        this.deadline = deadline;
    }

    /**
     * check if this task need repeat to execute any more.
     *
     * @return true if this task need repeat to execute
     */
    public boolean needRepeat() {
        // check maxTimes and triedTimes first
        if (maxExecuteTimes <= 1 || this.executedTimes >= maxExecuteTimes) {
            return false;
        }

        // maxTimes and triedTimes are OK for retry
        // then check deadline
        if (deadline <= 0) {
            // no deadline
            return true;
        }

        // check deadline
        // consider interval if it is enabled
        long nextRetryTimestamp = interval > 0 ? (System.currentTimeMillis() + interval) : System
                .currentTimeMillis();
        return nextRetryTimestamp <= deadline;
    }
}
