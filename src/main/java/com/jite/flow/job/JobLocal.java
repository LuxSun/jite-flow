package com.jite.flow.job;

import com.jite.flow.engine.JobNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Lux Sun
 * @date 2021/10/13
 */
public class JobLocal {

    private JobNode jobNode;

    private List<JobNode> parentJobNodeList;

    private List<JobNode> childJobNodeList;

    private Map<String, Object> localMap = new HashMap<>();

    public JobLocal(JobNode jobNode, List<JobNode> parentJobNodeList, List<JobNode> childJobNodeList) {
        this.jobNode = jobNode;
        this.parentJobNodeList = parentJobNodeList;
        this.childJobNodeList = childJobNodeList;
    }

    public void setLocalMap(Map<String, Object> localMap) {
        this.localMap = localMap;
    }

    public void put(String key, Object value) {
        localMap.put(key, value);
    }

    public Object get(String key) {
        return localMap.get(key);
    }

    public Set<Map.Entry<String, Object>> getEntrySet() {
        return localMap.entrySet();
    }

    public JobNode getJobNode() {
        return jobNode;
    }

    public List<JobNode> getParentJobNodeList() {
        return parentJobNodeList;
    }

    public List<JobNode> getChildJobNodeList() {
        return childJobNodeList;
    }
}