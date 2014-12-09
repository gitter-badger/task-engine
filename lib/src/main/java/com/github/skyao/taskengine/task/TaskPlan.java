package com.github.skyao.taskengine.task;

import static com.google.common.base.Preconditions.checkState;

/**
 * task plan about when to execute this task..
 */
public class TaskPlan {
    private int priority;

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
     * @throws IllegalStateException if priority is invalid
     * @see com.github.skyao.taskengine.task.TaskPriority#isValidPriority(int)
     */
    public void setPriority(int priority) {
        checkState(TaskPriority.isValidPriority(priority), "invalid task priority=" + priority);
        this.priority = priority;
    }

    /**
     * get a default task plan.
     *
     * @return default task plan.
     */
    public static TaskPlan defaultPlan() {
        TaskPlan plan = new TaskPlan();
        plan.setPriority(TaskPriority.NORMAL);
        return plan;
    }
}
