package com.github.skyao.taskengine.task;

import com.google.common.base.Function;

/**
 * task executable part.
 * <p>
 * Task executable is the "executable" part of the task and can be put accepted by executor,
 *  so that the executor can execute this task.
 * </p>
 * @author Sky Ao
 *
 */
public interface TaskExecutable extends Function<RuntimeTask, TaskResult> {

    /**
     * execute the task and get the result.
     * 
     * <p>
     * <b>Please remember to set status of task to FINISHED, and set result to CANCELED, FAILURE or SUCCESS.</b>
     * Otherwise the task will be considered as failure and status is set to
     * FAILURE.
     * </p>
     */
    TaskResult apply(RuntimeTask task);
}
