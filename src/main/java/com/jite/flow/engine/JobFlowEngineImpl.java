package com.jite.flow.engine;

import com.jite.flow.job.JobContext;
import com.jite.flow.report.ReportContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public class JobFlowEngineImpl implements JobFlowEngine {

    private final static Logger LOG = Logger.getLogger(JobFlowEngineImpl.class.getName());

    @Override
    public ReportContext run(JobContext jobContext) {
        LOG.log(Level.INFO, "Start Running Jobflow: {0}", jobContext.getJobStruct().getId());
        return JobFlowCore.builder().jobContext(jobContext).build().engining();
    }
}