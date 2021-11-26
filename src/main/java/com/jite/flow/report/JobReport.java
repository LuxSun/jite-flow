package com.jite.flow.report;

import com.jite.flow.constant.JobStatusEnum;
import com.jite.flow.job.JobContext;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public interface JobReport {

    JobStatusEnum getJobStatusEnum();

    Throwable getThrowable();
}
