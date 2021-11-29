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

    private JobNode parentJobGraphNode;

    private Map<String, JobNode> parentJobGraphNodeMap;

    private JobNode parentJobGraphLine;

    private Map<String, JobNode> parentJobGraphLineMap;

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

    public JobNode getParentJobGraphNode() {
        return parentJobGraphNode;
    }

    public void setParentJobGraphNode(JobNode parentJobGraphNode) {
        this.parentJobGraphNode = parentJobGraphNode;
    }

    public Map<String, JobNode> getParentJobGraphNodeMap() {
        return parentJobGraphNodeMap;
    }

    public void setParentJobGraphNodeMap(Map<String, JobNode> parentJobGraphNodeMap) {
        this.parentJobGraphNodeMap = parentJobGraphNodeMap;
    }

    public Map<String, JobNode> getParentJobGraphLineMap() {
        return parentJobGraphLineMap;
    }

    public void setParentJobGraphLineMap(Map<String, JobNode> parentJobGraphLineMap) {
        this.parentJobGraphLineMap = parentJobGraphLineMap;
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

    public JobNode getParentJobGraphLine() {
        return parentJobGraphLine;
    }

    public void setParentJobGraphLine(JobNode parentJobGraphLine) {
        this.parentJobGraphLine = parentJobGraphLine;
    }

    public void setId(String id) {
        this.id = id;
    }
}