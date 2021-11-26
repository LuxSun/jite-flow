package com.jite.flow.job;

import com.jite.flow.report.JobReport;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public interface Job {

    JobReport execute(JobContext jobContext);
}