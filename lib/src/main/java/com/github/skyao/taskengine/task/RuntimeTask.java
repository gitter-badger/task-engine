package com.github.skyao.taskengine.task;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * runtime task.
 */
public class RuntimeTask {
    private final Task task;
    private final TaskContext context;

    /**
     * create RuntimeTask for a Task.
     *
     * @param task task to wrap
     * @throws NullPointerException if task or task context is null
     */
    public RuntimeTask(Task task, TaskContext context) {
        checkNotNull(task, "task should not be null");
        checkNotNull(context, "task context should not be null");

        this.task = task;
        this.context = context;
    }

    /**
     * get task.
     *
     * @return wrapped task
     */
    public Task getTask() {
        return task;
    }

    /**
     * get task context.
     *
     * @return task context.
     */
    public TaskContext getContext() {
        return context;
    }
}
