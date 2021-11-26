package com.jite.flow.job;

import com.jite.flow.engine.JobStruct;
import com.jite.flow.report.ReportContext;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public class JobContext {

    private JobStruct jobStruct;

    private ReportContext reportContext = new ReportContext(this);

    private ConcurrentHashMap<String, Object> asyncContext = new ConcurrentHashMap<>();

    private Map<String, Object> context = new ConcurrentHashMap<>();

    private final ThreadLocal<JobLocal> JOB_LOCAL_THREAD_LOCAL = new ThreadLocal<>();

    public JobContext(JobStruct jobStruct) {
        this.jobStruct = jobStruct;
    }

    public JobContext(JobStruct jobStruct, ConcurrentHashMap<String, Object> asyncContext) {
        this(jobStruct);
        this.asyncContext = asyncContext;
    }

    public void put(String key, Object value) {
        context.put(key, value);
    }

    public <T> void put(Class<T> clazz, Object value) {
        context.put(clazz.getName(), value);
    }

    public <T> void put(Class<T> clazz) {
        try {
            context.put(clazz.getName(), clazz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object get(String key) {
        return context.get(key);
    }

    public <T> T get(Class<T> clazz) {
        return (T)context.get(clazz.getName());
    }

    public <T> T get(String key, Class<T> clazz) {
        return (T)context.get(key);
    }

    public Set<Map.Entry<String, Object>> getEntrySet() {
        return context.entrySet();
    }

    public JobStruct getJobStruct() {
        return jobStruct;
    }

    public ConcurrentHashMap<String, Object> getAsyncContext() {
        return asyncContext;
    }

    public ReportContext getReportContext() {
        return reportContext;
    }

    public ThreadLocal<JobLocal> getJobLocalThreadLocal() {
        return JOB_LOCAL_THREAD_LOCAL;
    }

    public JobLocal getJobLocal() {
        return JOB_LOCAL_THREAD_LOCAL.get();
    }

    public static JobContextBuilder builder() {
        return new JobContextBuilder();
    }

    public static class JobContextBuilder {

        private JobStruct jobStruct;

        private ConcurrentHashMap<String, Object> asyncContext;

        public JobContextBuilder jobStruct(JobStruct jobStruct) {
            this.jobStruct = jobStruct;
            return this;
        }

        public JobContextBuilder asyncContext(ConcurrentHashMap<String, Object> asyncContext) {
            this.asyncContext = asyncContext;
            return this;
        }

        public JobContext build() {
            return new JobContext(this.jobStruct, this.asyncContext);
        }
    }

    @Override
    public String toString() {
        return "JobContext: " + context;
    }
}