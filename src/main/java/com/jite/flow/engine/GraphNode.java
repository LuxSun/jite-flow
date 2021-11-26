package com.jite.flow.engine;

public class GraphNode extends AbstractGraphNode {

    public GraphNode(String id, String moduleId) {
        this.id = id;
        this.moduleId = moduleId;
    }

    public GraphNode(String id, String name, String moduleId) {
        this(id, moduleId);
        this.name = name;
    }
}