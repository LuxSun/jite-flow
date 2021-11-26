package com.jite.flow.handler;

import com.jite.flow.constant.JobModuleIdEnum;
import com.jite.flow.job.DefaultJob;
import com.jite.flow.job.Job;
import com.jite.flow.job.JobModule;
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
        register(JobModuleIdEnum.DEFAULT_JOB, new DefaultJob());
    }

    public static void init() {
        LOG.info("Load the Jobs successfully");
    }

    public static Job getJob(String jobModuleId) {
        if (!JOB_MAP.containsKey(jobModuleId)) {
            return null;
        }
        Job job = JOB_MAP.get(jobModuleId);
        return FlowUtil.KryoUtil.clone(job);
    }

    public static Job getJob(Enum jobModuleId) {
        return getJob(jobModuleId.name());
    }

    public static void register(String jobModuleId, Job job) {
        JOB_MAP.put(jobModuleId, job);
    }

    public static void register(Enum jobModuleId, Job job) {
        register(jobModuleId.name(), job);
    }
}