package com.jite.flow.handler;

import com.alibaba.fastjson.JSON;
import com.jite.flow.constant.JobModuleIdEnum;
import com.jite.flow.job.DefaultJob;
import com.jite.flow.job.DefaultJobModule;
import com.jite.flow.job.JobModule;
import com.jite.flow.util.FlowUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author Lux Sun
 * @date 2021/11/2
 */
public class JobModuleBuildHandler {

    private static final Logger LOG = Logger.getLogger(JobModuleBuildHandler.class.getName());

    private static final Map<String, JobModule> JOB_MODULE_MAP = new ConcurrentHashMap<>();

    static {
        register(JobModuleIdEnum.DEFAULT_JOB, new DefaultJobModule());
        register(JobModuleIdEnum.LINE_JOB, new DefaultJobModule());
    }

    public static void init() {
        LOG.info("Load the JobModules successfully");
    }

    public static JobModule getJobModule(String jobModuleId) {
        return JOB_MODULE_MAP.get(jobModuleId);
    }

    public static JobModule getJobModule(String jobModuleId, String jobModuleBody) {
        if (FlowUtil.StringUtil.isEmpty(jobModuleId)) {
            return null;
        }

        // 获取 jobModuleId 对应的 <? implements JobModule>.class
        JobModule jobModule = JOB_MODULE_MAP.get(jobModuleId);
        return JSON.parseObject(jobModuleBody, jobModule.getClass());
    }

    public static JobModule getJobModule(Enum jobModuleId, String jobModuleBody) {
        return getJobModule(jobModuleId.name(), jobModuleBody);
    }

    public static void register(String jobModuleId, JobModule jobModule) {
        JOB_MODULE_MAP.put(jobModuleId, jobModule);
    }

    public static void register(Enum jobModuleId, JobModule jobModule) {
        register(jobModuleId.name(), jobModule);
    }
}