package com.jite.flow.engine;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public abstract class AbstractGraphNode extends AbstractGraph {

    protected String moduleId;

    @Override
    protected String getJobNodeName() {
        return getName();
    }

    @Override
    protected String getJobNodeId() {
        return getId();
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}