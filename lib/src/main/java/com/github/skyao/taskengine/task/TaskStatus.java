package com.github.skyao.taskengine.task;

public enum TaskStatus {

    /**
     * task is initiated and not scheduled yet.
     */
    INITIAL(0),

    /**
     * task is scheduled and waiting to be executed. 
     */
    WAITING(1),

    /**
     * task is running.
     */
    RUNNING(2),

    /**
     * task is finished.
     */
    FINISHED(3);

    private int statusValue;

    private TaskStatus(int statusValue) {
        this.statusValue = statusValue;
    }

    public int getStatusValue() {
        return statusValue;
    }
}
