package com.jite.flow.bootstrap;

import com.jite.flow.handler.JobBuildHandler;
import com.jite.flow.handler.JobModuleBindHandler;
import com.jite.flow.handler.ModuleBuildHandler;
import com.jite.flow.job.Job;
import com.jite.flow.job.Module;

/**
 * @author Lux Sun
 * @date 2021/11/8
 */
public class JiteFlowRegister {

    public static void register(String jobId, Job job) {
        JobBuildHandler.register(jobId, job);
    }

    public static void register(String moduleId, Module module) {
        ModuleBuildHandler.register(moduleId, module);
    }

    public static void register(Enum jobId, Job job) {
        JobBuildHandler.register(jobId.name(), job);
    }

    public static void register(Enum moduleId, Module module) {
        ModuleBuildHandler.register(moduleId.name(), module);
    }

    public static void register(String moduleId, String jobId) {
        JobModuleBindHandler.register(moduleId, jobId);
    }

    public static void register(Enum moduleId, Enum jobId) {
        register(moduleId.name(), jobId.name());
    }
}