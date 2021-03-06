package com.jite.flow.engine;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public abstract class AbstractGraphNode extends AbstractGraph {

    protected String moduleId;

    protected String moduleParam;

    protected String getJobNodeName() {
        return getName();
    }

    protected String getJobNodeId() {
        return getId();
    }

    public String getModuleId() {
        return moduleId;
    }

    public String getModuleParam() {
        return moduleParam;
    }

    public void setModuleParam(String moduleParam) {
        this.moduleParam = moduleParam;
    }
}