package com.jite.flow.async;

import com.jite.flow.engine.AbstractGraphLine;
import com.jite.flow.engine.AbstractGraphNode;
import java.util.List;
import java.util.Map;

/**
 * @author Lux Sun
 * @date 2021/10/26
 */
public class AsyncGraph {

    /**
     * Async 原始图节点
     */
    private List<AbstractGraphNode> asyncAbstractGraphNodeList;

    /**
     * Async 原始图关系线
     */
    private List<AbstractGraphLine> asyncAbstractGraphLineList;

    /**
     * 原始图 节点/线 参数模板
     */
    private List<Map<String, Object>> paramList;

    public AsyncGraph(List<AbstractGraphNode> asyncAbstractGraphNodeList, List<AbstractGraphLine> asyncAbstractGraphLineList, List<Map<String, Object>> paramList) {
        this.asyncAbstractGraphNodeList = asyncAbstractGraphNodeList;
        this.asyncAbstractGraphLineList = asyncAbstractGraphLineList;
        this.paramList = paramList;
    }

    public List<Map<String, Object>> getParamList() {
        return paramList;
    }

    public void setParamList(List<Map<String, Object>> paramList) {
        this.paramList = paramList;
    }

    public List<AbstractGraphNode> getAsyncAbstractGraphNodeList() {
        return asyncAbstractGraphNodeList;
    }

    public void setAsyncAbstractGraphNodeList(List<AbstractGraphNode> asyncAbstractGraphNodeList) {
        this.asyncAbstractGraphNodeList = asyncAbstractGraphNodeList;
    }

    public List<AbstractGraphLine> getAsyncAbstractGraphLineList() {
        return asyncAbstractGraphLineList;
    }

    public void setAsyncAbstractGraphLineList(List<AbstractGraphLine> asyncAbstractGraphLineList) {
        this.asyncAbstractGraphLineList = asyncAbstractGraphLineList;
    }
}
