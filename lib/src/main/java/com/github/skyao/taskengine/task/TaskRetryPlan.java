package com.github.skyao.taskengine.task;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * retry plan for task execution.
 */
public class TaskRetryPlan {

    private int maxTimes;

    private int triedTimes;

    private long deadline;

    private long interval;

    /**
     * get max times of execution.
     *
     * @return max times of execution.
     */
    public int getMaxTimes() {
        return maxTimes;
    }

    /**
     * set max times of execution.
     *
     * @param maxTimes max times of execution, at least one and 1 means no retry
     * @throws java.lang.IllegalArgumentException if max retry times is not greater than 1
     */
    public void setMaxTimes(int maxTimes) {
        checkArgument(maxTimes > 1, "max retry times should greater than 1: maxTimes=" + maxTimes);

        this.maxTimes = maxTimes;
    }

    /**
     * get tried times.
     *
     * @return tried times
     */
    public int getTriedTimes() {
        return triedTimes;
    }

    /**
     * set tried times.
     *
     * @param triedTimes tried times, should greater than zero
     * @throws java.lang.IllegalArgumentException if tried times is not greater than zero
     */
    public void setTriedTimes(int triedTimes) {
        checkArgument(triedTimes > 0, "tried times should greater than 0: triedTimes=" + triedTimes);

        this.triedTimes = triedTimes;
    }

    /**
     * get deadline of execution.
     *
     * @return deadline of execution (time stamp)
     */
    public long getDeadline() {
        return deadline;
    }

    /**
     * set deadline of execution.
     *
     * @param deadline deadline of execution (time stamp), 0 means no deadline
     * @throws java.lang.IllegalArgumentException if tried times is not greater than zero
     */
    public void setDeadline(long deadline) {
        checkArgument(deadline >= 0, "deadline should greater than zero: deadline=" + deadline);

        this.deadline = deadline;
    }

    /**
     * get interval.
     *
     * @return interval (in millisecond)
     */
    public long getInterval() {
        return interval;
    }

    /**
     * set interval.
     *
     * @param interval retry interval, in millisecond
     * @throws java.lang.IllegalArgumentException if interval is not greater than zero
     */
    public void setInterval(long interval) {
        checkArgument(interval >= 0, "interval should greater than or equal to zero: interval=" + interval);

        this.interval = interval;
    }

    /**
     * check if this task need retry.
     *
     * @return true if this task need retry
     */
    public boolean needRetry() {
        // check maxTimes and triedTimes first
        if (maxTimes <= 1 || this.triedTimes >= maxTimes) {
            return false;
        }

        // maxTimes and triedTimes are OK for retry
        // then check deadline
        if (deadline <= 0) {
            // no deadline
            return true;
        }

        // check deadline
        // consider cool down time if it is enabled
        long nextRetryTimestamp = interval > 0 ? (System.currentTimeMillis() + interval) : System
                .currentTimeMillis();
        return nextRetryTimestamp <= deadline;
    }

    @Override
    public String toString() {
        return "RetryPlan [maxTimes=" + maxTimes + ", triedTimes=" + triedTimes + ", deadline=" + deadline
                + ", interval=" + interval + "]";
    }

    /**
     * default task retry plan.
     *
     * @return default retry plan, retry 1 times, no limitation for deadline and cool down time.
     */
    public static TaskRetryPlan newDefault() {
        TaskRetryPlan plan = new TaskRetryPlan();
        plan.maxTimes = 2;
        plan.triedTimes = 0;
        // by default no deadline for retry period
        plan.deadline = 0;
        // by default no cool down time
        plan.interval = 0;
        return plan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskRetryPlan)) {
            return false;
        }

        TaskRetryPlan that = (TaskRetryPlan) o;
        if (interval != that.interval) {
            return false;
        }
        if (deadline != that.deadline) {
            return false;
        }
        if (maxTimes != that.maxTimes) {
            return false;
        }
        return triedTimes == that.triedTimes;
    }

    @Override
    public int hashCode() {
        int result = maxTimes;
        result = 31 * result + triedTimes;
        result = 31 * result + (int) (deadline ^ (deadline >>> 32));
        result = 31 * result + (int) (interval ^ (interval >>> 32));
        return result;
    }
}
