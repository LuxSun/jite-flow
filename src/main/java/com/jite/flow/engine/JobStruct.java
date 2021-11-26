package com.jite.flow.engine;

import java.util.*;
import com.alibaba.fastjson.JSON;
import com.jite.flow.async.AsyncGraph;
import com.jite.flow.config.LoggerConfig;
import com.jite.flow.constant.Const;
import com.jite.flow.handler.JobBuildHandler;
import com.jite.flow.handler.JobModuleBuildHandler;
import com.jite.flow.job.JobLocal;
import com.jite.flow.util.FlowUtil;
import java.util.concurrent.*;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public class JobStruct {

    private JobStruct(String id, List<? extends AbstractGraphNode> abstractGraphNodeList, List<? extends AbstractGraphLine> abstractGraphLineList, List<Map<String, Object>> paramList) {
        this.id = id;
        this.paramList = paramList;
        this.abstractGraphNodeList = abstractGraphNodeList;
        this.abstractGraphLineList = abstractGraphLineList;
    }

    /**
     * 大作业Id(默认值: UUID)
     */
    private String id;

    /**
     * 原始图 节点/线 参数模板
     */
    private List<Map<String, Object>> paramList;

    /**
     * 原始图 nodeId 对应 参数模板
     */
    private Map<String, Map<String, Object>> idToParamMap;

    /**
     * 原始图 Line Id To FromToId & FromToId To Id Map
     */
//    private Map<String, String> lineIdMap;

    /**
     * Aysnc 原始图 Async Line fromToId 对应 子树 Async 原始图
     */
//    private Map<String, AsyncGraph> asyncGraphMap;

    /**
     * 原始图节点
     */
    private List<? extends AbstractGraphNode> abstractGraphNodeList;

    /**
     * 原始图关系线
     */
    private List<? extends AbstractGraphLine> abstractGraphLineList;

    /**
     * 原始图节点 Id 对应 原始图节点 Map
     */
    private Map<String, AbstractGraphNode> abstractGraphNodeMap;

    /**
     * 原始图线 Id(from/to) 对应 原始图线 Map
     */
    private Map<String, AbstractGraphLine> abstractGraphLineMap;

    /**
     * 原始图节点 Id 对应 原始图子节点列表 Map
     */
    private Map<String, List<AbstractGraphNode>> childAbstractGraphNodeListMap;

    /**
     * 原始图节点 Id 对应 原始图父节点列表 Map
     */
    private Map<String, List<AbstractGraphNode>> parentAbstractGraphNodeListMap;

    /**
     * 作业起始节点
     */
    private List<JobNode> jobNodeStartList;

    /**
     * 原始图起始节点 Id
     */
    private List<String> graphNodeStartIdList;

    /**
     * TODO 取名
     * 原始图结束节点 Id 对应 是否异步 Map
     * TRUE: ASYNC
     * FALSE: SYNC
     */
//    private Map<String, Boolean> mainGraphNodeEndIdRateMap;

    /**
     * 作业图节点
     * 较原始图节点区别: 新增关系线作业节点
     */
    private List<JobNode> jobNodeList;

    /**
     * 作业图关系线
     * 较原始图关系线区别: 父亲节点 -> 儿子节点 转换为 父亲节点 -> 父子关系线节点 -> 儿子节点
     */
    private List<JobLine> jobLineList;

    /**
     * 作业节点 Id 对应 作业节点 Map
     */
    private Map<String, JobNode> jobIdToNodeMap;

    /**
     * 作业节点 Id 对应 作业本地 Map
     */
    private Map<String, JobLocal> jobIdToLocalMap;

    /**
     * 儿子作业 Id 对应 所有父亲作业节点的关系线
     */
    private Map<String, List<String>> parentJobLineMap;

    /**
     * 父亲作业 Id 对应 所有儿子作业节点的关系线
     */
    private Map<String, List<String>> childJobLineMap;

    /**
     * 线程池: 提供给引擎 JobNode 执行作业
     */
    private ExecutorService executorService;

    /**
     * 作业节点 Id 对应 作业线程响应体 Map
     * 必须使用高并发容器, 否则会引发 modCount 问题
     * 比如一个 Job A 入度多个的时候, 其中 Job B 并未开始工作
     * 但是 Job A 已经开始等待所有父 Job 工作完毕, 此时因为已经在遍历中
     * 突然 Job B 开始工作并结束往 jobNodeFutureMap put
     * 此时 Job A 继续遍历会导致 modCount 问题
     */
    private Map<String, Future> jobNodeFutureMap;

    /**
     * 作业节点 Id 对应 作业执行状态
     * TRUE: 已执行
     * FALSE: 未执行
     */
    private Map<String, Boolean> jobNodeCalledMap;

    /**
     * 子到父关系线 Id 对应 关系线使用状态
     * TRUE: 已使用
     * FALSE: 未使用
     */
    private Map<String, Boolean> jobToFromLineCalledMap;

    /**
     * 主干流水线计数器
     */
    private CountDownLatch countDownLatch;

    private void init() {
        // 初始化日志框架
        initLogger();

        // 初始化作业模板、作业属性模板
        initJobs();

        // 初始化所有开始节点 Id
        initGraphStartIds();

        // 初始化 idToParamMap、abstractGraphNodeMap、abstractGraphLineMap、parentAbstractGraphNodeListMap、childAbstractGraphNodeListMap
        initGraphMaps();

        // 初始化异步原始图 asyncGraphMap
//        initAsyncGraphs();

        // Graph模式 Node/Line 转 Job模式 Node/Line
        initJobNodeLineList();

        // 初始化 jobNodeFutureMap、jobNodeCalledMap、jobIdToNodeMap
        initJobNodeMaps();

        // 初始化 parentJobLineMap、childJobLineMap、jobToFromLineCalledMap
        initJobLineMaps();

//        initParentJobNodeLineMap();

        // 初始化 jobIdToModuleMap
        initJobLocalMap();

        // 初始化 executorService、id(默认值: UUID)、起始作业节点
        initOthers();
    }

    /**
     * 初始化日志框架
     * @param
     */
    private void initLogger() {
        LoggerConfig.init();
    }

    /**
     * 初始化作业模板、作业属性模板
     * @param
     */
    private void initJobs() {
        JobBuildHandler.init();
        JobModuleBuildHandler.init();
    }

    /**
     * 初始化所有开始节点 Id
     * @param
     */
    private void initGraphStartIds() {
        this.graphNodeStartIdList = new ArrayList<>();
        HashSet<String> initStartIdSet = new HashSet<>();

        for (AbstractGraphLine abstractGraphLine : abstractGraphLineList) {
            initStartIdSet.add(abstractGraphLine.getToId());
        }

        String graphNodeId;
        for (AbstractGraphNode abstractGraphNode : abstractGraphNodeList) {
            graphNodeId = abstractGraphNode.getId();
            // 找到起始节点
            if (!initStartIdSet.contains(graphNodeId)) {
                this.graphNodeStartIdList.add(graphNodeId);
            }
        }
    }

    /**
     * 初始化 idToParamMap、abstractGraphNodeMap、abstractGraphLineMap
     * parentAbstractGraphNodeListMap、childAbstractGraphNodeListMap
     * @param
     */
    private void initGraphMaps() {
        abstractGraphNodeMap = new HashMap<>();
        abstractGraphLineMap = new HashMap<>();
        childAbstractGraphNodeListMap = new HashMap<>();
        parentAbstractGraphNodeListMap = new HashMap<>();
        idToParamMap = new HashMap<>();

        paramList.forEach(param -> idToParamMap.put((String) param.get(Const.AbstractJobModule.Key.ID), param));

        abstractGraphNodeList.forEach(abstractGraphNode -> {
            abstractGraphNode.setModuleParam(JSON.toJSONString(idToParamMap.get(abstractGraphNode.getId())));
            abstractGraphNodeMap.put(abstractGraphNode.getId(), abstractGraphNode);
        });

        String fromId;
        String toId;
        for (AbstractGraphLine abstractGraphLine : abstractGraphLineList) {
            fromId = abstractGraphLine.getFromId();
            toId = abstractGraphLine.getToId();

            if (!parentAbstractGraphNodeListMap.containsKey(toId)) {
                parentAbstractGraphNodeListMap.put(toId, new ArrayList<>());
            }
            parentAbstractGraphNodeListMap.get(toId).add(abstractGraphNodeMap.get(fromId));

            if (!childAbstractGraphNodeListMap.containsKey(fromId)) {
                childAbstractGraphNodeListMap.put(fromId, new ArrayList<>());
            }
            childAbstractGraphNodeListMap.get(fromId).add(abstractGraphNodeMap.get(toId));
        }
    }

    /**
     * TODO 重构
     * 因为 JobNode 包含 GraphNode + GraphLine 所以
     * GraphNode 父亲有 JobLine + JobNode
     * GraphLine 父亲仅有 JobNode 业务才有意义
     * @param
     */
//    private void initParentJobNodeLineMap() {
//        List<String> parentJobNodeIdList;
//        for (JobNode jobNode : jobNodeList) {
//            parentJobNodeIdList = parentJobLineMap.get(jobNode.getId());
//            if (FlowUtil.CollectionUtil.isNotEmpty(parentJobNodeIdList)) {
//
//                boolean isNode = jobNode.getBoolGraphNode();
//
//                if (isNode && FlowUtil.ObjectUtil.isNull(jobNode.getParentJobGraphLineMap())) {
//                    jobNode.setParentJobGraphLineMap(new HashMap<>());
//                }
//                if (FlowUtil.ObjectUtil.isNull(jobNode.getParentJobGraphNodeMap())) {
//                    jobNode.setParentJobGraphNodeMap(new HashMap<>());
//                }
//
//                if (isNode) {
//                    for (String parentGraphLineId : parentJobNodeIdList) {
//                        JobNode parentJobNode = jobIdToNodeMap.get(parentGraphLineId);
//                        jobNode.getParentJobGraphLineMap().put(parentGraphLineId, parentJobNode);
//                        String parentGraphNodeId = parentJobLineMap.get(parentGraphLineId).get(0);
//                        jobNode.getParentJobGraphNodeMap().put(parentGraphNodeId, jobIdToNodeMap.get(parentGraphNodeId));
//                    }
//                } else {
//                    for (String parentGraphNodeId : parentJobNodeIdList) {
//                        JobNode parentJobNode = jobIdToNodeMap.get(parentGraphNodeId);
//                        jobNode.getParentJobGraphNodeMap().put(parentGraphNodeId, parentJobNode);
//                    }
//                }
//            }
//        }
//    }

    /**
     * TODO 重做
     * 筛选出异步调用子树, 但不剪枝掉原始图, 因为需要在 core 里做判断等待父节点完成后才开始异步调用子树
     * @param
     */
//    private void initAsyncGraphs() {
//        this.asyncGraphMap = new HashMap<>();
//        Map<String, Boolean> idToUsedMap = new HashMap<>();
//
//        String fromId;
//        String toId;
//        String fromToId;
//        Queue<String> graphNodeIdQueue = new LinkedList<>(this.graphNodeStartIdList);
//        String graphNodeId;
//        while (!graphNodeIdQueue.isEmpty()) {
//            graphNodeId = graphNodeIdQueue.poll();
//            List<AbstractGraphNode> abstractGraphNodeList = childAbstractGraphNodeListMap.get(graphNodeId);
//            if (FlowUtil.CollectionUtil.isEmpty(abstractGraphNodeList)) {
//                continue;
//            }
//            Boolean isAsync;
//            for (AbstractGraphNode childAbstractGraphNode : abstractGraphNodeList) {
//                fromId = graphNodeId;
//                toId = childAbstractGraphNode.getId();
//                fromToId = fromId + "_" + toId;
//                graphNodeIdQueue.add(toId);
//                // 防止重复添加异步调用原始图, 以及异步原始图嵌套的子异步原始图无需再剪
//                isAsync = FlowUtil.BooleanUtil.isTrue(abstractGraphLineMap.get(fromToId).getAsync()) && !idToUsedMap.containsKey(fromId) && !idToUsedMap.containsKey(toId);
//                if (isAsync) {
//                    AsyncGraph asyncGraph = new AsyncGraph(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//                    initAsyncGraph(toId, asyncGraph, idToUsedMap);
//                    this.asyncGraphMap.put(fromToId, asyncGraph);
//                }
//            }
//        }
//    }
//
//    private void initAsyncGraphLeafNodeId(String asyncGraphLeafNodeId) {
//        mainGraphNodeEndIdRateMap.put(asyncGraphLeafNodeId, Const.Node.DispatchRate.ASYNC);
//    }
//
//    private void initAsyncGraph(String fromId,
//                                AsyncGraph asyncGraph,
//                                Map<String, Boolean> idToUsedMap) {
//
//        if (idToUsedMap.containsKey(fromId)) {
//            return;
//        }
//
//        idToUsedMap.put(fromId, true);
//        AbstractGraphNode abstractGraphNode = abstractGraphNodeMap.get(fromId);
//        asyncGraph.getAsyncAbstractGraphNodeList().add(abstractGraphNode);
//        asyncGraph.getParamList().add(this.idToParamMap.get(fromId));
//
//        List<AbstractGraphNode> childAbstractGraphNodeList = childAbstractGraphNodeListMap.get(fromId);
//        // 剪枝 & 异步调用图的叶子节点
//        if (FlowUtil.CollectionUtil.isEmpty(childAbstractGraphNodeList)) {
//            initAsyncGraphLeafNodeId(fromId);
//            return;
//        }
//
//        for (AbstractGraphNode childAbstractGraphNode : childAbstractGraphNodeList) {
//            String toId = childAbstractGraphNode.getId();
//            String fromToId = fromId + "_" + toId;
//            asyncGraph.getAsyncAbstractGraphLineList().add(abstractGraphLineMap.get(fromToId));
//            asyncGraph.getParamList().add(this.idToParamMap.get(this.lineIdMap.get(fromToId)));
//            initAsyncGraph(toId, asyncGraph, idToUsedMap);
//        }
//    }

    /**
     * Graph模式 Node/Line 转 Job模式 Node/Line
     * @param
     */
    private void initJobNodeLineList() {
        jobNodeList = new ArrayList<>();
        jobLineList = new ArrayList<>();
        abstractGraphNodeList.forEach(abstractGraphNode -> jobNodeList.add(new JobNode(abstractGraphNode.getId(), getJobNodeName(abstractGraphNode), abstractGraphNode.getModuleId(), abstractGraphNode.getModuleParam(), abstractGraphNode.getAsync(), abstractGraphNode)));
        abstractGraphLineList.forEach(abstractGraphLine -> jobLineList.add(new JobLine(abstractGraphLine.getFromId(), abstractGraphLine.getToId())));
    }

    /**
     * 初始化 jobNodeFutureMap、jobNodeCalledMap、jobIdToNodeMap
     * @param
     */
    private void initJobNodeMaps() {
        jobNodeFutureMap = new ConcurrentHashMap<>();
        jobIdToNodeMap = new HashMap<>();
        jobNodeCalledMap = new HashMap<>();

        String jobNodeId;
        for (JobNode jobNode : jobNodeList) {
            jobNodeId = jobNode.getId();
            jobIdToNodeMap.put(jobNodeId, jobNode);
            jobNodeCalledMap.put(jobNodeId, Const.JobNode.Call.NON_CALLED);
        }
    }

    /**
     * 初始化 parentJobLineMap、childJobLineMap、jobToFromLineCalledMap
     * @param
     */
    private void initJobLineMaps() {
        parentJobLineMap = new HashMap<>();
        childJobLineMap = new HashMap<>();
        jobToFromLineCalledMap = new HashMap<>();

        String fromId;
        String toId;
        for (JobLine jobLine : jobLineList) {
            fromId = jobLine.getFromId();
            toId = jobLine.getToId();

            if (!childJobLineMap.containsKey(fromId)) {
                childJobLineMap.put(fromId, new ArrayList<>());
            }
            childJobLineMap.get(fromId).add(toId);

            if (!parentJobLineMap.containsKey(toId)) {
                parentJobLineMap.put(toId, new ArrayList<>());
            }
            parentJobLineMap.get(toId).add(fromId);

            // TODO 待确认是否有用
            jobToFromLineCalledMap.put(jobLine.getToFromId(), Const.JobLine.Call.NON_CALLED);
        }
    }

    /**
     * TODO check
     * 初始化 jobIdToModuleMap
     * @param
     */
    private void initJobLocalMap() {
        jobIdToLocalMap = new HashMap<>();

        String jobNodeId;
        List<String> parentJobNodeIdList;
        List<String> childJobNodeIdList;
        List<JobNode> parentJobNodeList = new ArrayList<>();
        List<JobNode> childJobNodeList = new ArrayList<>();
        for (JobNode jobNode : jobNodeList) {
            jobNodeId = jobNode.getId();

            parentJobNodeIdList = parentJobLineMap.get(jobNodeId);
            if (FlowUtil.CollectionUtil.isNotEmpty(parentJobNodeIdList)) {
                for (String parentJobNodeId : parentJobNodeIdList) {
                    parentJobNodeList.add(jobIdToNodeMap.get(parentJobNodeId));
                }
            }

            childJobNodeIdList = childJobLineMap.get(jobNodeId);
            if (FlowUtil.CollectionUtil.isNotEmpty(childJobNodeIdList)) {
                for (String childJobNodeId : childJobNodeIdList) {
                    childJobNodeList.add(jobIdToNodeMap.get(childJobNodeId));
                }
            }

            JobLocal jobLocal = new JobLocal(jobNode, parentJobNodeList, childJobNodeList);
            // 转化自定义节点配置
            AbstractGraph graphNodeOrLine = jobLocal.getJobNode().getAbstractGraph();
            jobLocal.setLocalMap(JSON.parseObject(JSON.toJSONString(graphNodeOrLine), Map.class));
            jobIdToLocalMap.put(jobNodeId, jobLocal);
        }
    }

    /**
     * 初始化 executorService、id(默认值: UUID)、起始作业节点
     * @param
     */
    private void initOthers() {
        countDownLatch = new CountDownLatch((int) mainGraphNodeEndIdRateMap.values().stream().filter(item -> FlowUtil.BooleanUtil.isFalse(item.booleanValue())).count());
        executorService = Executors.newFixedThreadPool(10);

        if (FlowUtil.StringUtil.isEmpty(id)) {
            id = FlowUtil.UUIDUtil.randomUUID();
        }

        jobNodeStartList = new ArrayList<>();
        this.graphNodeStartIdList.forEach(graphNodeStartId -> jobNodeStartList.add(jobIdToNodeMap.get(graphNodeStartId)));
    }

    /**
     * 获取 Job 名称 (默认 Id)
     * 如果为 Line, fromId & toId 优先各自 name, 否则各自 id (如: A_B, A_UUIDB, UUIDA_B, UUIDA_UUIDB)
     * @param abstractGraph
     */
    private String getJobNodeName(AbstractGraph abstractGraph) {
        String jobNodeName = abstractGraph.getJobNodeName();
        if (FlowUtil.StringUtil.isEmpty(jobNodeName)) {
            if (abstractGraph instanceof AbstractGraphLine) {
                AbstractGraphLine abstractGraphLine = (AbstractGraphLine) abstractGraph;
                jobNodeName = getJobNodeName(abstractGraphNodeMap.get(abstractGraphLine.getFromId()))
                        + "_"
                        + getJobNodeName(abstractGraphNodeMap.get(abstractGraphLine.getToId()));
            } else {
                jobNodeName = abstractGraph.getJobNodeId();
            }
        }
        return jobNodeName;
    }

    public static JobStructBuilder builder() {
        return new JobStructBuilder();
    }

    public static class JobStructBuilder {

        private String id;

        private List<Map<String, Object>> paramList;

        private List<? extends AbstractGraphNode> abstractGraphNodeList;

        private List<? extends AbstractGraphLine> abstractGraphLineList;

        public JobStructBuilder id(String id) {
            this.id = id;
            return this;
        }

        public JobStructBuilder paramList(List<Map<String, Object>> paramList) {
            this.paramList = paramList;
            return this;
        }

        public JobStructBuilder graphNodeList(List<? extends AbstractGraphNode> abstractGraphNodeList) {
            this.abstractGraphNodeList = abstractGraphNodeList;
            return this;
        }

        public JobStructBuilder graphLineList(List<? extends AbstractGraphLine> abstractGraphLineList) {
            this.abstractGraphLineList = abstractGraphLineList;
            return this;
        }

        public JobStruct build() {
            JobStruct jobStruct = new JobStruct(this.id, this.abstractGraphNodeList, this.abstractGraphLineList, this.paramList);
            jobStruct.init();
            return jobStruct;
        }
    }

    public String getId() {
        return id;
    }

    public List<JobNode> getJobNodeStartList() {
        return jobNodeStartList;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public Map<String, Future> getJobNodeFutureMap() {
        return jobNodeFutureMap;
    }

    Map<String, Boolean> getJobNodeCalledMap() {
        return jobNodeCalledMap;
    }

    Map<String, Boolean> getJobToFromLineCalledMap() {
        return jobToFromLineCalledMap;
    }

    public List<? extends AbstractGraphNode> getAbstractGraphNodeList() {
        return abstractGraphNodeList;
    }

    public List<? extends AbstractGraphLine> getAbstractGraphLineList() {
        return abstractGraphLineList;
    }

    public List<JobNode> getJobNodeList() {
        return jobNodeList;
    }

    public List<JobLine> getJobLineList() {
        return jobLineList;
    }

    public Map<String, JobNode> getJobIdToNodeMap() {
        return jobIdToNodeMap;
    }

    public Map<String, JobLocal> getJobIdToLocalMap() {
        return jobIdToLocalMap;
    }

    public Map<String, List<String>> getParentJobLineMap() {
        return parentJobLineMap;
    }

    public Map<String, List<String>> getChildJobLineMap() {
        return childJobLineMap;
    }

    public Map<String, AsyncGraph> getAsyncGraphMap() {
        return asyncGraphMap;
    }

    public Map<String, Boolean> getMainGraphNodeEndIdRateMap() {
        return mainGraphNodeEndIdRateMap;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }
}