package com.jite.flow.example;

import com.jite.flow.engine.GraphLine;

public class Line extends GraphLine {

    public Line(String id, String fromId, String toId) {
        super(id, fromId, toId);
    }

    public Line(String id, String fromId, String toId, Boolean async) {
        super(id, fromId, toId, async);
    }
}