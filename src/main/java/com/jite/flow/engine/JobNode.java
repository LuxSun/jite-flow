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

    private Boolean boolGraphNode;

    private Boolean boolGraphLine;

    private JobNode parentJobGraphNode;

    private Map<String, JobNode> parentJobGraphNodeMap;

    private JobNode parentJobGraphLine;

    private Map<String, JobNode> parentJobGraphLineMap;

    private String name;

    private Boolean async;

    private String jobModuleId;

    private JobModule jobModule;

    private String jobModuleBody;

    private AbstractGraph abstractGraph;

    public JobNode(String id, Boolean boolGraphNode, Boolean boolGraphLine, String name, String jobModuleId, String jobModuleBody, Boolean async, AbstractGraph abstractGraph) {
        this.id = id;
        this.boolGraphNode = boolGraphNode;
        this.boolGraphLine = boolGraphLine;
        this.name = name;
        this.async = async;
        this.jobModuleId = jobModuleId;
        this.jobModuleBody = jobModuleBody;
        this.abstractGraph = abstractGraph;

        if (FlowUtil.StringUtil.isNotEmpty(jobModuleBody)) {
            this.jobModule = JobModuleBuildHandler.getJobModule(jobModuleId, jobModuleBody);
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

    public String getJobModuleBody() {
        return jobModuleBody;
    }

    public Boolean getBoolGraphNode() {
        return boolGraphNode;
    }

    public void setBoolGraphNode(Boolean boolGraphNode) {
        this.boolGraphNode = boolGraphNode;
    }

    public Boolean getBoolGraphLine() {
        return boolGraphLine;
    }

    public void setBoolGraphLine(Boolean boolGraphLine) {
        this.boolGraphLine = boolGraphLine;
    }

    public void setJobModuleBody(String jobModuleBody) {
        this.jobModuleBody = jobModuleBody;
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

    public AbstractGraph getAbstractGraph() {
        return abstractGraph;
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

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
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