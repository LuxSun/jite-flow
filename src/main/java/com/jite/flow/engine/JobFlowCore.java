package com.jite.flow.engine;

import com.jite.flow.async.AsyncLineJob;
import com.jite.flow.job.JobContext;
import com.jite.flow.handler.JobBuildHandler;
import com.jite.flow.handler.JobExecuteHandler;
import com.jite.flow.job.Job;
import com.jite.flow.job.JobLocal;
import com.jite.flow.report.JobReport;
import com.jite.flow.report.ReportContext;
import com.jite.flow.util.FlowUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author Lux Sun
 * @date 2021/10/12
 */
class JobFlowCore {

    private JobStruct jobStruct;

    private JobContext jobContext;

    private ReportContext reportContext;

    JobFlowCore(JobContext jobContext) {
        this.jobContext = jobContext;
        this.jobStruct = jobContext.getJobStruct();
        this.reportContext = jobContext.getReportContext();
    }

    static JobFlowCoreBuilder builder() {
        return new JobFlowCoreBuilder();
    }

    static class JobFlowCoreBuilder {

        private JobContext jobContext;

        JobFlowCoreBuilder jobContext(JobContext jobContext) {
            this.jobContext = jobContext;
            return this;
        }

        JobFlowCore build() {
            return new JobFlowCore(this.jobContext);
        }
    }

    ReportContext engining() {
        jobStruct.getJobNodeStartList().forEach(jobNodeStart -> jobStruct.getExecutorService().submit(() -> run(jobNodeStart, null)));
        return this.reportContext;
    }

    private void run(JobNode jobNode, JobNode parentJobNode) {

        String nowJobNodeId = jobNode.getId();
        String parentJobNodeId = null;
        if (FlowUtil.ObjectUtil.nonNull(parentJobNode)) {
            parentJobNodeId = parentJobNode.getId();
        }

        // 保证当前作业未被执行过(相同 JobNode 才锁)
        synchronized (jobNode) {
            if (jobStruct.getJobNodeCalledMap().get(nowJobNodeId)) {
                return;
            }
            jobStruct.getJobNodeCalledMap().put(nowJobNodeId, true);
        }

        // 保证父亲作业节点执行完毕(自旋锁) TODO CountDownLatch instead of it
        List<String> preJobNodeList = jobStruct.getParentJobLineMap().get(nowJobNodeId);
        if (FlowUtil.CollectionUtil.isNotEmpty(preJobNodeList) && preJobNodeList.size() > 1) {
            HashSet preJobNodeSet = new HashSet<>(preJobNodeList);
            int preFinishJobNodeNum = preJobNodeList.size();
            try {
                while (preFinishJobNodeNum > 0) {
                    for (Map.Entry<String, Future> jobNodeFuture : jobStruct.getJobNodeFutureMap().entrySet()) {
                        String fromJobNodeId = jobNodeFuture.getKey();
                        if (!preJobNodeSet.contains(fromJobNodeId)) {
                            continue;
                        }
                        Future fromJobNodeFuture = jobNodeFuture.getValue();
                        String toFromLineCalledId = nowJobNodeId + "@" + fromJobNodeId;
                        if (jobStruct.getJobToFromLineCalledMap().get(toFromLineCalledId) != null && !jobStruct.getJobToFromLineCalledMap().get(toFromLineCalledId) && null != fromJobNodeFuture && fromJobNodeFuture.isDone()) {
                            jobStruct.getJobToFromLineCalledMap().put(toFromLineCalledId, true);
                            preFinishJobNodeNum--;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 执行 JobNode 任务
        JobLocal jobLocal = jobStruct.getJobIdToLocalMap().get(nowJobNodeId);
        // 设定当前 jobLocal
        jobContext.getJobLocalThreadLocal().set(jobLocal);

        // 设定父亲 Node/Line
        if (jobNode.getBoolGraphNode()) {
            if (FlowUtil.ObjectUtil.nonNull(parentJobNode)) {
                jobNode.setParentJobGraphLine(parentJobNode);
                jobNode.setParentJobGraphNode(parentJobNode.getParentJobGraphNode());
            }
        } else {
            jobNode.setParentJobGraphNode(parentJobNode);
        }

        // 异步线调用
        if (FlowUtil.StringUtil.isNotEmpty(parentJobNodeId) && jobStruct.getAsyncGraphMap().containsKey(parentJobNodeId)) {
            new AsyncLineJob().execute(jobContext);
            return;
        }

        Job realJob = JobBuildHandler.getJob(jobNode.getJobModuleId());
        // 开始执行(如果是异步线 JobNode 也执行完先它自己的 Job)
        JobReport jobReport = JobExecuteHandler.builder().job(realJob).build().execute(jobContext);
        reportContext.put(nowJobNodeId, jobReport);

        // 主干流水线计数器减 1 操作
        if (jobStruct.getMainGraphNodeEndIdRateMap().containsKey(nowJobNodeId) && !jobStruct.getMainGraphNodeEndIdRateMap().get(nowJobNodeId)) {
            jobStruct.getCountDownLatch().countDown();
        }

        // 收集子作业节点(下一批执行作业)
        List<String> nextJobNodeList = jobStruct.getChildJobLineMap().get(nowJobNodeId);
        if (FlowUtil.CollectionUtil.isEmpty(nextJobNodeList)) {
            return;
        }

        // 下一批作业进行迭代并收集每个作业的报告结果
        for (String jobNodeId : nextJobNodeList) {
            Future jobNodeFuture = jobStruct.getExecutorService().submit(() -> run(jobStruct.getJobIdToNodeMap().get(jobNodeId), jobNode));
            jobStruct.getJobNodeFutureMap().put(jobNodeId, jobNodeFuture);
        }
    }
}