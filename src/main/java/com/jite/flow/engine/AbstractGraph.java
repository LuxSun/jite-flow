package com.jite.flow.engine;

/**
 * @author Lux Sun
 * @date 2021/10/18
 */
public abstract class AbstractGraph {

    protected String id;

    protected String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
