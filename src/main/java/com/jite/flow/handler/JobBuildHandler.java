package com.jite.flow.handler;

import com.jite.flow.constant.JobIdEnum;
import com.jite.flow.job.DefaultJob;
import com.jite.flow.job.Job;
import com.jite.flow.util.FlowUtil;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * 原型模式 + 享元模式
 * @author Lux Sun
 * @date 2021/10/13
 */
public class JobBuildHandler {

    private static final Logger LOG = Logger.getLogger(JobBuildHandler.class.getName());

    private static final Map<String, Job> JOB_MAP = new ConcurrentHashMap<>();

    private JobBuildHandler() {}

    static {
        register(JobIdEnum.DEFAULT_JOB, new DefaultJob());
    }

    public static void init() {
        LOG.info("Load the Jobs successfully");
    }

    public static Job getJob(String jobId) {
        if (!JOB_MAP.containsKey(jobId)) {
            return null;
        }
        Job job = JOB_MAP.get(jobId);
        return FlowUtil.KryoUtil.clone(job);
    }

    public static Job getJob(Enum jobId) {
        return getJob(jobId.name());
    }

    public static void register(String jobId, Job job) {
        JOB_MAP.put(jobId, job);
    }

    public static void register(Enum jobId, Job job) {
        register(jobId.name(), job);
    }
}