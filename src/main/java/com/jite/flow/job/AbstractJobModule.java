package com.jite.flow.job;

/**
 * @author Lux Sun
 * @date 2021/11/4
 */
public abstract class AbstractJobModule implements JobModule {

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}