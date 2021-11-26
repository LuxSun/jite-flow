package com.jite.flow.engine;

import com.jite.flow.job.JobContext;
import com.jite.flow.report.ReportContext;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public interface JobFlowEngine {

    ReportContext run(JobContext jobContext);
}