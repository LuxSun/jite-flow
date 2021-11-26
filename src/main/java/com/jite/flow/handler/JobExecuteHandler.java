package com.jite.flow.handler;

import com.jite.flow.job.JobContext;
import com.jite.flow.job.Job;
import com.jite.flow.report.JobReport;

/**
 * 策略模式
 * @author Lux Sun
 * @date 2021/10/13
 */
public class JobExecuteHandler {

    private Job job;

    public JobExecuteHandler(Job job) {
        this.job = job;
    }

    public JobReport execute(JobContext jobContext) {
        return job.execute(jobContext);
    }

    public static JobExecuteHandlerBuilder builder() {
        return new JobExecuteHandlerBuilder();
    }

    public static class JobExecuteHandlerBuilder {

        private Job job;

        public JobExecuteHandlerBuilder job(Job job) {
            this.job = job;
            return this;
        }

        public JobExecuteHandler build() {
            return new JobExecuteHandler(job);
        }
    }
}
