package com.jite.flow.bootstrap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jite.flow.engine.*;
import com.jite.flow.job.JobContext;
import com.jite.flow.report.ReportContext;
import com.jite.flow.util.FlowUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author Lux Sun
 * @date 2021/10/15
 */
public class JiteFlowBoot {

    private static final Logger LOG = Logger.getLogger(JiteFlowBoot.class.getName());

    private JobContext jobContext;

    public JiteFlowBoot(JobContext jobContext) {
        this.jobContext = jobContext;
    }

    public static JiteFlowBootBuilder builder() {
        return new JiteFlowBootBuilder();
    }

    public ReportContext run() {
        ReportContext reportContext = JobFlowEngineBuilder.build().run(this.jobContext);
        return reportContext;
    }

    public ReportContext invoke() {
        ReportContext reportContext = run();
        // TODO 支持全局等待
        awaitMain();
//        try {
//            Thread.sleep(1000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return reportContext;
    }

    private void awaitMain() {
        try {
            jobContext.getJobStruct().getCountDownLatch().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class JiteFlowBootBuilder {

        private String id;

        private List<Map<String, Object>> paramList;

        private ConcurrentHashMap<String, Object> asyncContext;

        private List<? extends AbstractGraphNode> abstractGraphNodeList;

        private List<? extends AbstractGraphLine> abstractGraphLineList;

        private Map<String, Object> context = new HashMap<>();

        public JiteFlowBootBuilder id(String id) {
            this.id = id;
            return this;
        }

        public JiteFlowBootBuilder params(String params) {
            this.paramList = JSON.parseObject(params, new TypeReference<List<Map<String, Object>>>(){});
            return this;
        }

        public JiteFlowBootBuilder params(List<Map<String, Object>> paramList) {
            return params(JSON.toJSONString(paramList));
        }

        public JiteFlowBootBuilder asyncContext(ConcurrentHashMap<String, Object> asyncContext) {
            this.asyncContext = asyncContext;
            return this;
        }

        public JiteFlowBootBuilder nodes(List<? extends AbstractGraphNode> abstractGraphNodeList) {
            this.abstractGraphNodeList = abstractGraphNodeList;
            return this;
        }

        public JiteFlowBootBuilder lines(List<? extends AbstractGraphLine> abstractGraphLineList) {
            this.abstractGraphLineList = abstractGraphLineList;
            return this;
        }

        public JiteFlowBootBuilder map(Map<String, Object> context) {
            if (FlowUtil.CollectionUtil.isNotEmpty(context)) {
                context.entrySet().forEach(ctx -> this.context.put(ctx.getKey(), ctx.getValue()));
            }
            return this;
        }

        public JiteFlowBootBuilder of(String key, Object value) {
            this.context.put(key, value);
            return this;
        }

        public JiteFlowBootBuilder of(Object value) {
            return of(value.getClass().getName(), value);
        }

        public JiteFlowBoot build() {
            printJiteFlow();

            JobContext jobContext = JobContext.builder().jobStruct(JobStruct.builder()
                    .id(id)
                    .paramList(paramList)
                    .graphNodeList(abstractGraphNodeList)
                    .graphLineList(abstractGraphLineList)
                    .build()).asyncContext(asyncContext).build();

            if (FlowUtil.CollectionUtil.isNotEmpty(this.context)) {
                this.context.entrySet().forEach(ctx -> jobContext.put(ctx.getKey(), ctx.getValue()));
            }

            return new JiteFlowBoot(jobContext);
        }

        private void printJiteFlow() {
            System.out.println("\n" +
                    "     ____..__   __                    ___________.__                    \n" +
                    "    |    ||__|_/  |_   ____           \\_   _____/|  |    ____  __  _  __\n" +
                    "    |    ||  |\\   __\\_/ __ \\   ______  |    __)  |  |   /  _ \\ \\ \\/ \\/ /\n" +
                    "/\\__|    ||  | |  |  \\  ___/  /_____/  |     \\   |  |__(  <_> ) \\     / \n" +
                    "\\________||__| |__|   \\___  >          \\___  /   |____/ \\____/   \\/\\_/  \n" +
                    "                          \\/               \\/                           \n");
        }
    }
}