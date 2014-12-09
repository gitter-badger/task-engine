package com.github.skyao.taskengine.task;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * task parameter.
 * <p>
 * The parameter contains two part: parameter name and parameter value.
 * </p>
 * <pre>
 *     The parameter name is always string, and the parameter value should be one of follow types:
 *     1. string
 *     2. int
 *     3. long
 *     4. boolean
 *     5. double
 * </pre>
 */
public class TaskParameter {
    private final String name;
    private final Object value;

    /**
     * create by name and value in string format.
     *
     * @param name  parameter name
     * @param value parameter value in string format
     * @throws java.lang.NullPointerException if name or value is null
     */
    public TaskParameter(String name, String value) {
        checkNotNull(name, "key should not be null");
        checkNotNull(value, "value should not be null");

        this.name = name;
        this.value = value;
    }

    /**
     * create by name and value in int format.
     *
     * @param name  parameter name
     * @param value parameter value in int format
     * @throws java.lang.NullPointerException if name is null
     */
    public TaskParameter(String name, int value) {
        checkNotNull(name, "key should not be null");

        this.name = name;
        this.value = value;
    }

    /**
     * create by name and value in long format.
     *
     * @param name  parameter name
     * @param value parameter value in long format
     * @throws java.lang.NullPointerException if name is null
     */
    public TaskParameter(String name, long value) {
        checkNotNull(name, "key should not be null");

        this.name = name;
        this.value = value;
    }

    /**
     * create by name and value in boolean format.
     *
     * @param name  parameter name
     * @param value parameter value in boolean format
     * @throws java.lang.NullPointerException if name is null
     */
    public TaskParameter(String name, boolean value) {
        checkNotNull(name, "key should not be null");

        this.name = name;
        this.value = value;
    }

    /**
     * create by name and value in double format.
     *
     * @param name  parameter name
     * @param value parameter value in double format
     * @throws java.lang.NullPointerException if name is null
     */
    public TaskParameter(String name, double value) {
        checkNotNull(name, "key should not be null");

        this.name = name;
        this.value = value;
    }

    /**
     * get name of task parameter.
     *
     * @return name of task parameter
     */
    public String getName() {
        return name;
    }

    /**
     * get value of task parameter.
     *
     * @return value of task parameter
     */
    public Object getValue() {
        return value;
    }

}
