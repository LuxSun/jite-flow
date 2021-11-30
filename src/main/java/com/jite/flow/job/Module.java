package com.jite.flow.job;

/**
 * @author Lux Sun
 * @date 2021/11/1
 */
public interface Module {

    default <T> T get(Class<T> clazz) {
        return (T)this;
    }
}