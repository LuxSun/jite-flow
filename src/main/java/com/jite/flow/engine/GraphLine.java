package com.jite.flow.engine;

public class GraphLine extends AbstractGraphLine {

    public GraphLine(String id, String fromId, String toId) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
    }

    public GraphLine(String id, String fromId, String toId, Boolean async) {
        this(id, fromId, toId);
        this.async = async;
    }
}