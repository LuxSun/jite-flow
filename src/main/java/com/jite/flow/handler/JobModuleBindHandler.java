package com.jite.flow.handler;

import com.jite.flow.constant.JobIdEnum;
import com.jite.flow.constant.ModuleIdEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * 原型模式 + 享元模式
 * @author Lux Sun
 * @date 2021/10/13
 */
public class JobModuleBindHandler {

    private static final Logger LOG = Logger.getLogger(JobModuleBindHandler.class.getName());

    private static final Map<String, String> MODULE_JOB_MAP = new ConcurrentHashMap<>();

    private JobModuleBindHandler() {}

    static {
        register(ModuleIdEnum.DEFAULT_MODULE, JobIdEnum.DEFAULT_JOB);
    }

    public static void init() {
        LOG.info("Load the Module-Job-Binder successfully");
    }

    public static void register(String moduleId, String jobId) {
        MODULE_JOB_MAP.put(moduleId, jobId);
    }

    public static void register(Enum moduleId, Enum jobId) {
        register(moduleId.name(), jobId.name());
    }
}