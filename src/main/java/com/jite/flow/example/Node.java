package com.jite.flow.example;

import com.jite.flow.engine.GraphNode;

public class Node extends GraphNode {

    public Node(String id, String moduleId) {
        super(id, moduleId);
    }

    public Node(String id, String name, String moduleId) {
        super(id, name, moduleId);
    }
}