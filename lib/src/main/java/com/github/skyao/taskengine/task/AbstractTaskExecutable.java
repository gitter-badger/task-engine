package com.github.skyao.taskengine.task;

/**
 * Created by exiaoao on 12/11/2014.
 */
public class AbstractTaskExecutable implements TaskExecutable {
    @Override
    public TaskResult apply(RuntimeTask runtimeTask) {
        Task task = runtimeTask.getTask();
        TaskPlan taskPlan = task.getPlan();
        TaskContent taskContent = task.getContent();
        TaskContext taskContext = runtimeTask.getContext();

        return execute(taskContent, taskPlan, taskContext);
    }

    protected TaskResult execute(TaskContent taskContent, TaskPlan taskPlan, TaskContext taskContext) {
        return null;
    }
}
