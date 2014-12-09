package com.github.skyao.taskengine.task;

/**
 * task priority.
 * <p>
 * In task engine, all the priority of task will be defined in int value with scope limitation of [-128,127].
 * </p>
 */
public final class TaskPriority {

    /**
     * normal priority.
     */
    public static final int NORMAL = 0;

    /**
     * high priority.
     */
    public static final int HIGH = 50;

    /**
     * very high priority.
     */
    public static final int VERY_HIGH = 100;

    /**
     * highest priority.
     */
    public static final int HIGHEST = Byte.MAX_VALUE;

    /**
     * low priority.
     */
    public static final int LOW = -50;

    /**
     * very low priority.
     */
    public static final int VERY_LOW = -100;

    /**
     * lowest priority.
     */
    public static final int LOWEST = Byte.MIN_VALUE;

    /**
     * default task priority (default to normal).
     */
    public static final int DEFAULT = NORMAL;

    /**
     * check if input priority is valid.
     * <p>
     * The valid priority scope is from -128 to 127 [-128,127].
     * *
     * </p>
     *
     * @param priority priority value in int
     * @return true if this priority is valid.
     */
    public static boolean isValidPriority(int priority) {
        return priority >= LOWEST && priority <= HIGHEST;
    }

    /**
     * normalize priority to valid priority.
     * <pre>
     * Normalize Rule:
     * 1. All the priorities bigger than HIGHEST will be changed to HIGHEST
     * 2. All the priorities smaller than LOWEST will be changed to LOWEST
     * 3. All the valid priorities won't be changed.
     * </pre>
     *
     * @param priority priority value to be normalized
     * @return normalized priority value
     */
    public static int normalize(int priority) {
        if (priority < LOWEST) {
            return LOWEST;
        }
        if (priority > HIGHEST) {
            return HIGHEST;
        }

        return priority;
    }
}
