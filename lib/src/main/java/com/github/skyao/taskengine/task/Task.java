package com.github.skyao.taskengine.task;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * task definition of task engine.
 *
 * @author Sky Ao
 */
public class Task {
    private final TaskContent content;
    private final TaskPlan plan;

    /**
     * create task by task content and default task plan.
     *
     * @param content task content
     * @throws NullPointerException if content is null
     */
    public Task(TaskContent content) {
        checkNotNull(content, "task content should not be null");

        this.content = content;
        this.plan = TaskPlan.defaultPlan();
    }

    /**
     * create task by task content and task plan.
     *
     * @param content task content
     * @param plan    task plan
     * @throws NullPointerException if content or plan is null
     */
    public Task(TaskContent content, TaskPlan plan) {
        checkNotNull(content, "task content should not be null");
        checkNotNull(plan, "task plan should not be null");

        this.content = content;
        this.plan = plan;
    }

    /**
     * get task content.
     *
     * @return task content
     */
    public TaskContent getContent() {
        return this.content;
    }

    /**
     * get task plan.
     *
     * @return task plan
     */
    public TaskPlan getPlan() {
        return this.plan;
    }

}
