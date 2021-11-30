package com.jite.flow.engine;

import com.jite.flow.handler.ModuleBuildHandler;
import com.jite.flow.job.Module;
import com.jite.flow.util.FlowUtil;
import java.util.Map;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public class JobNode {

    private String id;

    private JobNode parentJobNode;

    private Map<String, JobNode> parentJobNodeMap;

    private Map<String, JobNode> childJobNodeMap;

    private String name;

    private String moduleId;

    private Module module;

    private String moduleParam;

    private AbstractGraphNode abstractGraphNode;

    public JobNode(String id, String name, String moduleId, String moduleParam, AbstractGraphNode abstractGraphNode) {
        this.id = id;
        this.name = name;
        this.moduleId = moduleId;
        this.moduleParam = moduleParam;
        this.abstractGraphNode = abstractGraphNode;

        if (FlowUtil.StringUtil.isNotEmpty(moduleParam)) {
            this.module = ModuleBuildHandler.getModule(moduleId, moduleParam);
        }
    }

    public <T> T getModule(Class<T> clazz) {
        if (FlowUtil.ObjectUtil.isNull(this.module)) {
            return null;
        }
        return this.module.get(clazz);
    }

    public String getModuleParam() {
        return moduleParam;
    }

    public void setModuleParam(String moduleParam) {
        this.moduleParam = moduleParam;
    }

    public JobNode getParentJobNode() {
        return parentJobNode;
    }

    public void setParentJobNode(JobNode parentJobNode) {
        this.parentJobNode = parentJobNode;
    }

    public Map<String, JobNode> getParentJobNodeMap() {
        return parentJobNodeMap;
    }

    public void setParentJobNodeMap(Map<String, JobNode> parentJobNodeMap) {
        this.parentJobNodeMap = parentJobNodeMap;
    }

    public AbstractGraphNode getAbstractGraphNode() {
        return abstractGraphNode;
    }

    public Map<String, JobNode> getChildJobNodeMap() {
        return childJobNodeMap;
    }

    public void setChildJobNodeMap(Map<String, JobNode> childJobNodeMap) {
        this.childJobNodeMap = childJobNodeMap;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}