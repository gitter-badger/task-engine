package com.github.skyao.taskengine.task;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * task plan about when to execute this task.
 *
 * @author Sky Ao
 */
public class TaskPlan {
    private int priority;
    private long start;
    private RepeatPlan schedule;
    private RepeatPlan retry;

    /**
     * create a default plan.
     *
     * @return new instance of default plan
     */
    public static TaskPlan newDefaultPlan() {
        return Builder.newDefaultPlan();
    }

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
     * get start time when this task should begin to execute .
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

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('{');
        buffer.append("priority=").append(priority);
        buffer.append(",start=").append(start);
        if (schedule != null) {
            buffer.append(",schedule=").append(schedule);
        }
        if (retry != null) {
            buffer.append(",retry=").append(retry);
        }
        buffer.append('}');
        return buffer.toString();
    }

    /**
     * use builder to help building new plan.
     *
     * @return builder instance
     */
    public static Builder newBuilder() {
        return new Builder();
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
         * @throws java.lang.IllegalArgumentException if intervalInMilliseconds is not greater than or quails to 0
         */
        public void setExecuted(int executed) {
            checkArgument(executed >= 0, "executed times should greater than or equal to zero: executedTimes=" + executed);

            this.executed = executed;
        }

        /**
         * get intervalInMilliseconds.
         *
         * @return intervalInMilliseconds in milliseconds
         */
        public long getInterval() {
            return interval;
        }

        /**
         * set intervalInMilliseconds in milliseconds
         *
         * @param interval intervalInMilliseconds in milliseconds, 0 means no intervalInMilliseconds
         * @throws java.lang.IllegalArgumentException if intervalInMilliseconds is not greater than or quails to 0
         */
        public void setInterval(long interval) {
            checkArgument(interval >= 0, "intervalInMilliseconds should greater than or equal to zero: intervalInMilliseconds=" + interval);

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
            // consider intervalInMilliseconds if it is enable
            long nextExecuteTime = interval > 0 ? (System.currentTimeMillis() + interval) : System
                    .currentTimeMillis();
            return nextExecuteTime <= deadline;
        }

        @Override
        public String toString() {
            StringBuilder buffer = new StringBuilder();
            buffer.append('{');
            if (enable) {
                buffer.append("max=").append(max);
                buffer.append(",executed=").append(executed);
                if (interval > 0) {
                    buffer.append(",interval=").append(interval);
                }
                if (deadline > 0) {
                    buffer.append(",deadline=").append(deadline);
                }
            } else {
                buffer.append("enable=false");
            }
            buffer.append('}');
            return buffer.toString();
        }

        /**
         * Builder to help to build a RepeatPlan.
         */
        public static class RepeatPlanBuilder {
            private final RepeatPlan repeatPlan;

            /**
             * create a builder for specified repeat plan.
             *
             * @param repeatPlan repeat plan to build
             */
            public RepeatPlanBuilder(RepeatPlan repeatPlan) {
                this.repeatPlan = repeatPlan;
            }

            /**
             * set max repeat times.
             *
             * @param max max repeat times
             * @return this builder itself to chain
             */
            public RepeatPlanBuilder max(int max) {
                this.repeatPlan.setMax(max);
                return this;
            }

            /**
             * set interval in milliseconds.
             *
             * @param milliseconds interval in milliseconds
             * @return this builder itself to chain
             */
            public RepeatPlanBuilder intervalInMilliseconds(long milliseconds) {
                this.repeatPlan.setInterval(milliseconds);
                return this;
            }

            /**
             * set interval in seconds.
             *
             * @param seconds interval in seconds
             * @return this builder itself to chain
             */
            public RepeatPlanBuilder intervalInSeconds(int seconds) {
                this.repeatPlan.setInterval(seconds * 1000);
                return this;
            }

            /**
             * set interval in minutes.
             *
             * @param minutes interval in minutes
             * @return this builder itself to chain
             */
            public RepeatPlanBuilder intervalInMinutes(int minutes) {
                this.repeatPlan.setInterval(minutes * 60 * 1000);
                return this;
            }

            /**
             * set interval in hours.
             *
             * @param hours interval in hours
             * @return this builder itself to chain
             */
            public RepeatPlanBuilder intervalInHours(int hours) {
                this.repeatPlan.setInterval(hours * 60 * 60 * 1000);
                return this;
            }

            /**
             * set exact deadline timestamp.
             *
             * @param deadline deadline
             * @return this builder itself to chain
             */
            public RepeatPlanBuilder deadline(long deadline) {
                this.repeatPlan.setDeadline(deadline);
                return this;
            }

            /**
             * set deadline to specified seconds later.
             *
             * @param seconds specified seconds
             * @return this builder itself to chain
             */
            public RepeatPlanBuilder deadlineAfterSeconds(int seconds) {
                this.repeatPlan.setDeadline(System.currentTimeMillis() + seconds * 1000);
                return this;
            }

            /**
             * set deadline to specified minutes later.
             *
             * @param minutes specified minutes
             * @return this builder itself to chain
             */
            public RepeatPlanBuilder deadlineAfterMinutes(int minutes) {
                this.repeatPlan.setDeadline(System.currentTimeMillis() + minutes * 60 * 1000);
                return this;
            }

            /**
             * set deadline to specified hours later.
             *
             * @param hours specified hours
             * @return this builder itself to chain
             */
            public RepeatPlanBuilder deadlineAfterHours(int hours) {
                this.repeatPlan.setDeadline(System.currentTimeMillis() + hours * 60 * 60 * 1000);
                return this;
            }
        }
    }

    public static class Builder {
        private final TaskPlan plan;

        /**
         * create new builder.
         */
        public Builder() {
            this.plan = Builder.newDefaultPlan();
        }

        /**
         * build a new default plan.
         *
         * @return new instance of default plan
         */
        public static TaskPlan newDefaultPlan() {
            TaskPlan plan = new TaskPlan();
            plan.setPriority(TaskPriority.NORMAL);
            plan.setStart(System.currentTimeMillis());
            return plan;
        }

        /**
         * build task plan.
         *
         * @return task plan
         */
        public TaskPlan build() {
            return plan;
        }

        /**
         * set priority by specified value.
         *
         * @param priority task priority
         * @return this builder itself to chain
         */
        public Builder priority(int priority) {
            this.plan.setPriority(priority);
            return this;
        }

        /**
         * set priority to HIGH.
         *
         * @return this builder itself to chain
         * @see TaskPriority#HIGH
         */
        public Builder priorityHigh() {
            this.plan.setPriority(TaskPriority.HIGH);
            return this;
        }

        /**
         * set priority to LOW.
         *
         * @return this builder itself to chain
         * @see TaskPriority#HIGH
         */
        public Builder priorityLow() {
            this.plan.setPriority(TaskPriority.LOW);
            return this;
        }

        /**
         * start to execute task immediately.
         *
         * @return this builder itself to chain
         */
        public Builder startNow() {
            this.plan.setStart(System.currentTimeMillis());
            return this;
        }

        /**
         * start to execute task at specified timestamp.
         *
         * @param start task start time in timestamp
         * @return this builder itself to chain
         */
        public Builder startAt(long start) {
            this.plan.setStart(start);
            return this;
        }

        /**
         * start to execute task after specified seconds.
         *
         * @param seconds specified seconds
         * @return this builder itself to chain
         */
        public Builder startAfterSeconds(int seconds) {
            this.plan.setStart(System.currentTimeMillis() + seconds * 1000);
            return this;
        }

        public RepeatPlan.RepeatPlanBuilder enableSchedule() {
            RepeatPlan repeatPlan = new RepeatPlan();
            this.plan.setSchedule(repeatPlan);
            return new RepeatPlan.RepeatPlanBuilder(repeatPlan);
        }

        public RepeatPlan.RepeatPlanBuilder enableRetry() {
            RepeatPlan repeatPlan = new RepeatPlan();
            this.plan.setRetry(repeatPlan);
            return new RepeatPlan.RepeatPlanBuilder(repeatPlan);
        }
    }
}
