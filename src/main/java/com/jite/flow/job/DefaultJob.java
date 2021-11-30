package com.jite.flow.job;

import com.jite.flow.constant.JobStatusEnum;
import com.jite.flow.engine.JobNode;
import com.jite.flow.report.DefaultJobReport;
import com.jite.flow.report.JobReport;
import com.jite.flow.util.FlowUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Lux Sun
 * @date 2021/10/13
 */
public class DefaultJob implements Job {

    private static final Logger LOG = Logger.getLogger(DefaultJob.class.getName());

    public DefaultJob() {}

    // TODO 添加注解
//    private Module module;

    @Override
    public JobReport execute(JobContext jobContext) {
        if (jobContext.get("result") == null) {
            jobContext.put("result", new ArrayList<String>());
        }

        JobNode jobNode = jobContext.getJobLocal().getJobNode();
        String name = jobNode.getName();
        String graphId = jobContext.getJobStruct().getId();
        if (graphId.length() > 8) {
            graphId = graphId.substring(0, 8);
        }
        String startString = String.format("[%s] %s - %s 开始执行任务...", graphId, FlowUtil.DateTimeUtil.now(), name);
        LOG.info(startString);
        ((List<String>) jobContext.get("result")).add(startString);
        try {
            DefaultModule defaultModule = jobNode.getModule(DefaultModule.class);
            if (FlowUtil.ObjectUtil.nonNull(defaultModule)) {
                String content = String.format("[%s] %s - %s 模板内容: %s", graphId, FlowUtil.DateTimeUtil.now(), name, defaultModule);
                LOG.info(content);
            }
            Thread.sleep(FlowUtil.DateTimeUtil.toMillisecond(defaultModule.getSecond(), FlowUtil.DateTimeUtil.TimeUnitEnum.SECOND));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String endString = String.format("[%s] %s - %s 任务执行完毕!", graphId, FlowUtil.DateTimeUtil.now(), name);
        LOG.info(endString);
        ((List<String>) jobContext.get("result")).add(endString);
        return new DefaultJobReport(JobStatusEnum.SUCCEED);
    }

    public static DefaultJobBuilder build() {
        return new DefaultJobBuilder();
    }

    public static class DefaultJobBuilder {

        private JobLocal jobLocal;

        public DefaultJobBuilder jobLocal(JobLocal jobLocal) {
            this.jobLocal = jobLocal;
            return this;
        }

        public DefaultJob build() {
            return new DefaultJob();
        }
    }
}