package com.github.skyao.taskengine.task;

public enum TaskResult {

    /**
     * task has been executed and succeed.
     */
    SUCCESS(0),

    /**
     * task has been executed and failed.
     */
    FAILURE(1),

    /**
     * task has been canceled.
     */
    CANCELED(2),

    /**
     * task is merged to other task.
     */
    MERGED(3),
    
    /**
     * task is rejected
     */
    REJECTED(4);

    private int resultValue;

    private TaskResult(int resultValue) {
        this.resultValue = resultValue;
    }

    public int getResultValue() {
        return resultValue;
    }
}
