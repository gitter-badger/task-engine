package com.github.skyao.taskengine.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Task context.
 * <p>
 * This context saves the context information on task level.
 * </p>
 *
 * @author Sky Ao
 */
public class TaskContext {

    /**
     * saved properties.
     */
    private Map<String, Object> properties = new HashMap<String, Object>();

    /**
     * save property.
     * <p>
     * If value is null, the saved property will be removed.
     * </p>
     *
     * @param key   key of property
     * @param value value of property
     * @throws NullPointerException if key is null
     */
    public void saveProperty(String key, Object value) {
        checkNotNull(key, "key should not be null");

        if (value == null) {
            properties.remove(key);
        } else {
            properties.put(key, value);
        }
    }

    /**
     * get property by specified key.
     *
     * @param key key of property
     * @return value of property, null if not found
     * @throws NullPointerException if key is null
     */
    public Object getProperty(String key) {
        checkNotNull(key, "key should not be null");

        return properties.get(key);
    }

}
