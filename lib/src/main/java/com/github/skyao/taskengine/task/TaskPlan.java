package com.github.skyao.taskengine.task;

import static com.google.common.base.Preconditions.checkArgument;

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
     * get start time.
     *
     * @return start time in timestamp
     */
    public long getStart() {
        return start;
    }

    /**
     * set start time in timestamp.
     *
     * @param start start time
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

    public void setNext(long next) {
        this.next = next;
    }

    /**
     * get last execution time..
     *
     * @return last execution time in timestamp
     */
    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    /**
     * get retry plan.
     * @return retry plan
     */
    public RepeatPlan getRetry() {
        return retry;
    }

    /**
     * set retry plan.
     *
     * @param retryPlan retry plan
     */
    public void setRetry(RepeatPlan retryPlan) {
        this.retry = retryPlan;
    }

    /**
     * get a default task plan.
     *
     * @return default task plan.
     */
    public static TaskPlan defaultPlan() {
        TaskPlan plan = new TaskPlan();
        //TBD
        return plan;
    }

    /**
     * task repeat plan about how to execute this task repeatedly.
     *
     */
    public static class RepeatPlan {
        private boolean enabled = true;
        private int max = 1;
        private int executed = 0;
        private long interval = 0;
        private long deadline = 0;

        /**
         * check if repeat plan enabled.
         *
         * @return true if repeat plan enabled.
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * set repeat plan enabled or not.
         *
         * @param enabled enabled or not
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
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
            // consider interval if it is enabled
            long nextExecuteTime = interval > 0 ? (System.currentTimeMillis() + interval) : System
                    .currentTimeMillis();
            return nextExecuteTime <= deadline;
        }
    }
}
