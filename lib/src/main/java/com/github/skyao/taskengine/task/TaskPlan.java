package com.github.skyao.taskengine.task;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * task plan about when to execute this task.
 *
 * @author Sky Ao
 */
public class TaskPlan {
    private int priority = TaskPriority.NORMAL;
    private long startTime = System.currentTimeMillis();

    private TaskRetryPlan retryPlan;

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
     * get task start time in timestamp.
     *
     * @return task start time
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * set task start time in timestamp.
     *
     * @param startTime task start time
     * @throws IllegalStateException if start time is invalid
     */
    public void setStartTime(long startTime) {
        checkArgument(startTime > 0, "invalid start time: start=" + startTime);
        this.startTime = startTime;
    }

    /**
     * get retry plan.
     * @return retry plan
     */
    public TaskRetryPlan getRetryPlan() {
        return retryPlan;
    }

    /**
     * set retry plan.
     *
     * @param retryPlan retry plan
     */
    public void setRetryPlan(TaskRetryPlan retryPlan) {
        this.retryPlan = retryPlan;
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
}
