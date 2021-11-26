package com.jite.flow.example.mock;

import com.alibaba.fastjson.JSON;
import com.jite.flow.example.Line;
import com.jite.flow.example.Node;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.List;
import java.util.Map;

/**
 * @author Lux Sun
 * @date 2021/10/18
 */
public abstract class AbstractDemo {

    protected List<Node> nodeList;

    protected List<Line> lineList;

    protected List<Map<String, Object>> paramList;

    public AbstractDemo(List<Node> nodeList, List<Line> lineList, List<Map<String, Object>> paramList) {
        this.nodeList = nodeList;
        this.lineList = lineList;
        this.paramList = paramList;
    }

    public String getParams() {
        return JSON.toJSONString(paramList);
    }

    public List<Map<String, Object>> getParamList() {
        return paramList;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public List<Line> getLineList() {
        return lineList;
    }
}