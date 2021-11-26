package com.jite.flow.bootstrap;

import com.jite.flow.handler.JobBuildHandler;
import com.jite.flow.handler.JobModuleBuildHandler;
import com.jite.flow.job.Job;
import com.jite.flow.job.JobModule;

/**
 * @author Lux Sun
 * @date 2021/11/8
 */
public class JiteFlowRegister {

    public static void register(String jobModuleId, Job job) {
        JobBuildHandler.register(jobModuleId, job);
    }

    public static void register(String jobModuleId, JobModule jobModule) {
        JobModuleBuildHandler.register(jobModuleId, jobModule);
    }

    public static void register(Enum jobModuleIdEnum, Job job) {
        JobBuildHandler.register(jobModuleIdEnum.name(), job);
    }

    public static void register(Enum jobModuleIdEnum, JobModule jobModule) {
        JobModuleBuildHandler.register(jobModuleIdEnum.name(), jobModule);
    }

    public static void register(String jobModuleId, Job job, JobModule jobModule) {
        register(jobModuleId, job);
        register(jobModuleId, jobModule);
    }

    public static void register(Enum jobModuleIdEnum, Job job, JobModule jobModule) {
        register(jobModuleIdEnum.name(), job, jobModule);
    }
}
