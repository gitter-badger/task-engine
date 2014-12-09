package com.github.skyao.taskengine.task;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * task content.
 * <p>
 * Task content stands for the actual content of a task: what will this task do?
 * </p>
 * <pre>
 * It always contains two part:
 * (1) task type: identifies what type this task is, for example 10001 stands for "remove an user by id".
 * (2) task parameters: some additional parameters for the specified task, in above example, to remove an user by its id
 * , the parameter of user id is required. So here we need a task parameter "uid=245001".
 * </pre>
 *
 * @author Sky Ao
 */
public class TaskContent {

    protected final int type;
    protected final TaskParameter[] parameters;

    /**
     * create by task type without task parameter.
     *
     * @param type task type
     * @throws IllegalStateException if task type is invalid
     */
    public TaskContent(int type) {
        checkTaskType(type);

        this.type = type;
        parameters = new TaskParameter[0];
    }

    /**
     * create by task type with task parameters.
     *
     * @param type       task type
     * @param parameters task parameters
     * @throws IllegalStateException if task type is invalid
     * @throws java.lang.NullPointerException if parameters is null
     */
    public TaskContent(int type, TaskParameter... parameters) {
        checkTaskType(type);
        checkNotNull(parameters, "taskParameters should not be null");

        this.type = type;
        this.parameters = parameters;
    }

    private void checkTaskType(int type) {
        checkState(type > 0, "invalid type=" + type + ",it should greater than zero.");
    }

    /**
     * get task type.
     *
     * @return task type
     */
    public int getType() {
        return this.type;
    }

    /**
     * get task parameters.
     *
     * @return task parameters
     */
    public TaskParameter[] getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("TaskContent[");
        buffer.append("type=").append(type);

        if (parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                TaskParameter param = parameters[i];
                buffer.append(',').append(param.getName()).append('=').append(param.getValue());
            }
        }
        buffer.append(']');

        return buffer.toString();
    }

    /**
     * create a new Builder by specify task type.
     *
     * @param type task type
     * @return new instance of Builder
     */
    public static Builder newBuilder(int type) {
        return new Builder(type);
    }

    /**
     * Builder helps to build task content easily.
     */
    public static class Builder {
        private final int type;
        private final List<TaskParameter> parameterList = new ArrayList<>();

        public Builder(int type) {
            this.type = type;
        }

        /**
         * build target task content.
         *
         * @return task content
         */
        public TaskContent build() {
            return new TaskContent(type, parameterList.toArray(new TaskParameter[parameterList.size()]));
        }

        /**
         * add a parameter with value in string format.
         *
         * @param name  parameter name
         * @param value parameter value in string format
         * @return this builder itself
         */
        public Builder add(String name, String value) {
            parameterList.add(new TaskParameter(name, value));
            return this;
        }

        /**
         * add a parameter with value in int format.
         *
         * @param name  parameter name
         * @param value parameter value in int format
         * @return this builder itself
         */
        public Builder add(String name, int value) {
            parameterList.add(new TaskParameter(name, value));
            return this;
        }

        /**
         * add a parameter with value in long format.
         *
         * @param name  parameter name
         * @param value parameter value in long format
         * @return this builder itself
         */
        public Builder add(String name, long value) {
            parameterList.add(new TaskParameter(name, value));
            return this;
        }

        /**
         * add a parameter with value in boolean format.
         *
         * @param name  parameter name
         * @param value parameter value in boolean format
         * @return this builder itself
         */
        public Builder add(String name, boolean value) {
            parameterList.add(new TaskParameter(name, value));
            return this;
        }

        /**
         * add a parameter with value in double format.
         *
         * @param name  parameter name
         * @param value parameter value in double format
         * @return this builder itself
         */
        public Builder add(String name, double value) {
            parameterList.add(new TaskParameter(name, value));
            return this;
        }
    }
}
