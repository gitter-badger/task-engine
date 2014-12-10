package com.github.skyao.taskengine.task;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * task plan about when to execute this task.
 *
 * @author Sky Ao
 */
public class TaskPlan {
    private int priority = TaskPriority.NORMAL;
    private long start = System.currentTimeMillis();
    private long next = start;
    private long last = 0;
    private RepeatPlan schedule;
    private RepeatPlan retry;

    /**
     * set task priority.
     *
     * @return task priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * set task priority.
     *
     * @param priority task priority
     * @throws IllegalArgumentException if priority is invalid
     * @see com.github.skyao.taskengine.task.TaskPriority#isValidPriority(int)
     */
    public void setPriority(int priority) {
        checkArgument(TaskPriority.isValidPriority(priority), "invalid task priority=" + priority);
        this.priority = priority;
    }

    /**
     * get start time when this .
     *
     * @return start time in timestamp
     */
    public long getStart() {
        return start;
    }

    /**
     * set start time.
     *
     * @param start start time in timestamp
     * @throws IllegalArgumentException if start time is not greater than zero
     */
    public void setStart(long start) {
        checkArgument(start > 0, "start time should greater than zero: start=" + start);
        this.start = start;
    }

    /**
     * get next trigger time to execute this task.
     *
     * @return next trigger time in timestamp
     */
    public long getNext() {
        return next;
    }

    /**
     * set next trigger time to execute this task.
     *
     * @param next next trigger time in timestamp
     * @throws IllegalArgumentException if next trigger time is not greater than zero
     */
    public void setNext(long next) {
        checkArgument(next > 0, "next trigger time should greater than zero: next=" + next);

        this.next = next;
    }

    /**
     * get last execute time.
     *
     * @return last execute time in timestamp
     */
    public long getLast() {
        return last;
    }

    /**
     * set last execute time.
     *
     * @param last last execute time in timestamp
     * @throws IllegalArgumentException if last execute time is not greater than zero
     */
    public void setLast(long last) {
        checkArgument(last > 0, "next trigger time should greater than zero: last=" + last);

        this.last = last;
    }

    /**
     * get retry plan.
     *
     * @return retry plan, null if no retry.
     */
    public RepeatPlan getRetry() {
        return retry;
    }

    /**
     * set retry plan.
     *
     * @param retryPlan retry plan
     * @throws java.lang.NullPointerException if retryPlan is null
     */
    public void setRetry(RepeatPlan retryPlan) {
        checkNotNull(retryPlan, "retry plan should not be null");
        this.retry = retryPlan;
    }

    /**
     * get schedule plan.
     *
     * @return schedule plan, null if this task only execute once.
     */
    public RepeatPlan getSchedule() {
        return schedule;
    }

    /**
     * set schedule plan.
     *
     * @param schedulePlan schedule plan
     * @throws java.lang.NullPointerException if schedulePlan is null
     */
    public void setSchedule(RepeatPlan schedulePlan) {
        checkNotNull(schedulePlan, "schedule plan should not be null");

        this.schedule = schedulePlan;
    }

    /**
     * task execute plan about how to execute this task repeatedly.
     */
    public static class RepeatPlan {
        private boolean enable = true;
        private int max = 1;
        private int executed = 0;
        private long interval = 0;
        private long deadline = 0;

        /**
         * check if repeat plan enable.
         *
         * @return true if repeat plan enable.
         */
        public boolean isEnable() {
            return enable;
        }

        /**
         * set repeat plan enable or not.
         *
         * @param enable enable or not
         */
        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        /**
         * get max execute times.
         *
         * @return max execute times
         */
        public int getMax() {
            return max;
        }

        /**
         * set max execute times.
         *
         * @param max max execute times, 1 means no repeat
         * @throws java.lang.IllegalArgumentException if max execute times is not greater than 1
         */
        public void setMax(int max) {
            checkArgument(max > 1, "max execute times should greater than 1: max=" + max);

            this.max = max;
        }

        /**
         * set executed times.
         *
         * @return executed times
         */
        public int getExecuted() {
            return executed;
        }

        /**
         * set executed times.
         *
         * @param executed executed times.
         * @throws java.lang.IllegalArgumentException if interval is not greater than or quails to 0
         */
        public void setExecuted(int executed) {
            checkArgument(executed >= 0, "executed times should greater than or equal to zero: executedTimes=" + executed);

            this.executed = executed;
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
         * @param interval interval in milliseconds, 0 means no interval
         * @throws java.lang.IllegalArgumentException if interval is not greater than or quails to 0
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
         * @param deadline deadline in timestamp, 0 means no deadline
         * @throws java.lang.IllegalArgumentException if deadline is not greater than or equals to 0
         */
        public void setDeadline(long deadline) {
            checkArgument(deadline >= 0, "deadline should greater than or equals to 0: deadline=" + deadline);

            this.deadline = deadline;
        }

        /**
         * check if this task need repeat to execute any more.
         *
         * @return true if this task need repeat to execute
         */
        public boolean needRepeat() {
            // check max execute times and executed times first
            if (max <= 1 || this.executed >= max) {
                return false;
            }

            // then check deadline
            // if no deadline
            if (deadline <= 0) {
                return true;
            }

            // if deadline exists, check nextExecuteTime
            // consider interval if it is enable
            long nextExecuteTime = interval > 0 ? (System.currentTimeMillis() + interval) : System
                    .currentTimeMillis();
            return nextExecuteTime <= deadline;
        }
    }
}
