package com.jite.flow.async;

import com.jite.flow.bootstrap.JiteFlowBoot;
import com.jite.flow.constant.JobStatusEnum;
import com.jite.flow.job.Job;
import com.jite.flow.job.JobContext;
import com.jite.flow.report.DefaultJobReport;
import com.jite.flow.report.JobReport;
import com.jite.flow.util.FlowUtil;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lux Sun
 * @date 2021/10/27
 */
public class AsyncLineJob implements Job {

    @Override
    public JobReport execute(JobContext jobContext) {

        try {
            String asyncLineId = jobContext.getJobLocal().getJobNode().getParentJobGraphLine().getId();
            // 优先使用 Name 来, 有 Id 则自带 Id, 否则 Name 优先策略
            String asyncLineName = jobContext.getJobLocal().getJobNode().getParentJobGraphLine().getName();
            Map<String, AsyncGraph> asyncGraphMap = jobContext.getJobStruct().getAsyncGraphMap();
            AsyncGraph asyncGraph = asyncGraphMap.get(asyncLineId);
            ConcurrentHashMap<String, Object> asyncContext = FlowUtil.KryoUtil.clone(jobContext.getAsyncContext());

            // TODO 支持单体、Kafka、Redis
            JiteFlowBoot.builder()
                    .id(asyncLineName)
                    .nodes(asyncGraph.getAsyncAbstractGraphNodeList())
                    .lines(asyncGraph.getAsyncAbstractGraphLineList())
                    .params(asyncGraph.getParamList())
                    .asyncContext(asyncContext)
                    .build()
                    .invoke();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return new DefaultJobReport(JobStatusEnum.SUCCEED);
    }
}