package com.jite.flow.engine;

import com.jite.flow.handler.JobModuleBuildHandler;
import com.jite.flow.job.JobModule;
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

    private String name;

    private String jobModuleId;

    private JobModule jobModule;

    private String jobModuleParam;

    private AbstractGraphNode abstractGraphNode;

    public JobNode(String id, String name, String jobModuleId, String jobModuleParam, AbstractGraphNode abstractGraphNode) {
        this.id = id;
        this.name = name;
        this.jobModuleId = jobModuleId;
        this.jobModuleParam = jobModuleParam;
        this.abstractGraphNode = abstractGraphNode;

        if (FlowUtil.StringUtil.isNotEmpty(jobModuleParam)) {
            this.jobModule = JobModuleBuildHandler.getJobModule(jobModuleId, jobModuleParam);
        }
    }

    public <T> T getJobModule(Class<T> clazz) {
        if (FlowUtil.ObjectUtil.isNull(this.jobModule)) {
            return null;
        }
        return this.jobModule.get(clazz);
    }

    public void setJobModule(JobModule jobModule) {
        this.jobModule = jobModule;
    }

    public String getJobModuleParam() {
        return jobModuleParam;
    }

    public void setJobModuleParam(String jobModuleParam) {
        this.jobModuleParam = jobModuleParam;
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

    public String getJobModuleId() {
        return jobModuleId;
    }

    public void setJobModuleId(String jobModuleId) {
        this.jobModuleId = jobModuleId;
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