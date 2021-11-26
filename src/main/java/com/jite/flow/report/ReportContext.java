package com.jite.flow.report;

import com.jite.flow.job.JobContext;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public class ReportContext {

    private JobContext jobContext;

    private Map<String, JobReport> reportContext = new ConcurrentHashMap<>();

    public ReportContext(JobContext jobContext) {
        this.jobContext = jobContext;
    }

    public void put(String jobNodeId, JobReport jobReport) {
        reportContext.put(jobNodeId, jobReport);
    }

    public JobContext getJobContext() {
        return jobContext;
    }

    public Object get(String jobNodeId) {
        return reportContext.get(jobNodeId);
    }

    public Set<Map.Entry<String, JobReport>> getEntrySet() {
        return reportContext.entrySet();
    }
}