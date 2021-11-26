package com.jite.flow.report;

import com.jite.flow.constant.JobStatusEnum;

/**
 * @author Lux Sun
 * @date 2021/10/14
 */
public class DefaultJobReport implements JobReport {

    private JobStatusEnum jobStatusEnum;

    private Throwable throwable;

    public DefaultJobReport(JobStatusEnum jobStatusEnum) {
        this.jobStatusEnum = jobStatusEnum;
    }

    public DefaultJobReport(JobStatusEnum jobStatusEnum, Throwable throwable) {
        this.jobStatusEnum = jobStatusEnum;
        this.throwable = throwable;
    }

    @Override
    public JobStatusEnum getJobStatusEnum() {
        return this.jobStatusEnum;
    }

    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }

    public void setJobStatusEnum(JobStatusEnum jobStatusEnum) {
        this.jobStatusEnum = jobStatusEnum;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return "DefaultJobReport {" +
                "status = " + jobStatusEnum +
                ", throwable = " + (throwable == null ? "None" : throwable) +
                '}';
    }
}