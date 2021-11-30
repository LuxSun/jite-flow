package com.jite.flow.engine;

import com.jite.flow.constant.Const;
import com.jite.flow.job.JobContext;
import com.jite.flow.handler.JobBuildHandler;
import com.jite.flow.handler.JobExecuteHandler;
import com.jite.flow.job.Job;
import com.jite.flow.job.JobLocal;
import com.jite.flow.report.JobReport;
import com.jite.flow.report.ReportContext;
import com.jite.flow.util.FlowUtil;
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
        String parentJobNodeId = FlowUtil.ObjectUtil.nonNull(parentJobNode) ? parentJobNode.getId() : null;

        // 保证当前作业未被执行过(相同 JobNode 才锁)
        synchronized (jobNode) {
            if (jobStruct.getJobNodeCalledMap().get(nowJobNodeId)) {
                return;
            }
            jobStruct.getJobNodeCalledMap().put(nowJobNodeId, Const.JobNode.Call.IS_CALLED);
        }

        // 保证父亲作业节点执行完毕(自旋锁) TODO CountDownLatch instead of it
        List<JobNode> preJobNodeList = jobStruct.getParentJobNodeListMap().get(nowJobNodeId);
        Map<String, Boolean> preJobNodeCalledMap = jobStruct.getParentJobNodeCalledMap().get(nowJobNodeId);
        if (FlowUtil.CollectionUtil.isNotEmpty(preJobNodeList) && preJobNodeList.size() > 1) {
            int preFinishJobNodeNum = preJobNodeList.size();
            try {
                while (preFinishJobNodeNum > 0) {
                    String preJobNodeId;
                    for (JobNode preJobNode : preJobNodeList) {
                        preJobNodeId = preJobNode.getId();
                        Future preJobNodeFuture = jobStruct.getJobNodeFutureMap().get(preJobNodeId);
                        // 判断父作业未被判断过 && 父作业是否完成
                        if (preJobNodeCalledMap.get(preJobNodeId).equals(Const.JobNode.Call.NON_CALLED) && null != preJobNodeFuture && preJobNodeFuture.isDone()) {
                            preJobNodeCalledMap.put(preJobNodeId, Const.JobNode.Call.IS_CALLED);
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

        // 设定父亲 Node
        jobNode.setParentJobNode(parentJobNode);

        // TODO 异步线调用
//        if (FlowUtil.StringUtil.isNotEmpty(parentJobNodeId) && jobStruct.getAsyncGraphMap().containsKey(parentJobNodeId)) {
//            new AsyncLineJob().execute(jobContext);
//            return;
//        }

        Job realJob = JobBuildHandler.getJob(jobNode.getModuleId());
        // 开始执行(如果是异步线 JobNode 也执行完先它自己的 Job)
        JobReport jobReport = JobExecuteHandler.builder().job(realJob).build().execute(jobContext);
        reportContext.put(nowJobNodeId, jobReport);

        // TODO 主干流水线计数器减 1 操作
//        if (jobStruct.getMainGraphNodeEndIdRateMap().containsKey(nowJobNodeId) && !jobStruct.getMainGraphNodeEndIdRateMap().get(nowJobNodeId)) {
//            jobStruct.getCountDownLatch().countDown();
//        }

        // 收集子作业节点(下一批执行作业)
        List<JobNode> nextJobNodeList = jobStruct.getChildJobNodeListMap().get(nowJobNodeId);
        if (FlowUtil.CollectionUtil.isEmpty(nextJobNodeList)) {
            return;
        }

        // 下一批作业进行迭代并收集每个作业的报告结果
        for (JobNode nextJobNode : nextJobNodeList) {
            Future jobNodeFuture = jobStruct.getExecutorService().submit(() -> run(nextJobNode, jobNode));
            jobStruct.getJobNodeFutureMap().put(nextJobNode.getId(), jobNodeFuture);
        }
    }
}