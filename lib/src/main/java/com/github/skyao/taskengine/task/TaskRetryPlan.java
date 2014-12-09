package com.github.skyao.taskengine.task;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * retry plan for task execution.
 */
public class TaskRetryPlan {

    private int maxTimes;

    private int triedTimes;

    private long deadline;

    private long cooldownTime;

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
     * get retry cool down time.
     *
     * @return retry cool down time (in millisecond)
     */
    public long getCooldownTime() {
        return cooldownTime;
    }

    /**
     * set retry cool down time.
     *
     * @param cooldownTime retry cool down time, in millisecond
     * @throws java.lang.IllegalArgumentException if cool down time is not greater than zero
     */
    public void setCooldownTime(long cooldownTime) {
        checkArgument(cooldownTime >= 0, "cool down time should greater than zero: cooldownTime=" + cooldownTime);

        this.cooldownTime = cooldownTime;
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
        long nextRetryTimestamp = cooldownTime > 0 ? (System.currentTimeMillis() + cooldownTime) : System
                .currentTimeMillis();
        return nextRetryTimestamp <= deadline;
    }

    @Override
    public String toString() {
        return "RetryPlan [maxTimes=" + maxTimes + ", triedTimes=" + triedTimes + ", deadline=" + deadline
                + ", cooldownTime=" + cooldownTime + "]";
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
        plan.cooldownTime = 0;
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
        if (cooldownTime != that.cooldownTime) {
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
        result = 31 * result + (int) (cooldownTime ^ (cooldownTime >>> 32));
        return result;
    }
}
