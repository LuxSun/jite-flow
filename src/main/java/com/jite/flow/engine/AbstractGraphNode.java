package com.jite.flow.engine;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public abstract class AbstractGraphNode extends AbstractGraph {

    protected String moduleId;

    protected String moduleParam;

    @Override
    protected String getJobNodeName() {
        return getName();
    }

    protected String getJobNodeId() {
        return getId();
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleParam() {
        return moduleParam;
    }

    public void setModuleParam(String moduleParam) {
        this.moduleParam = moduleParam;
    }
}