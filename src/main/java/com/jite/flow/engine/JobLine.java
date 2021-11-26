package com.jite.flow.engine;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public class JobLine {

    private String fromId;

    private String toId;

    public JobLine(String fromId, String toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

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