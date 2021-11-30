package com.jite.flow.engine;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public abstract class AbstractGraphLine extends AbstractGraph {

    protected String fromId;

    protected String toId;

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }
}