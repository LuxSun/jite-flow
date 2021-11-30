package com.jite.flow.example.mock;

import com.alibaba.fastjson.JSON;
import com.jite.flow.constant.Const;
import com.jite.flow.constant.ModuleIdEnum;
import com.jite.flow.example.Line;
import com.jite.flow.example.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lux Sun
 * @date 2021/11/4
 */
public class Demo0 extends AbstractDemo {

    public Demo0(List<Node> nodeList, List<Line> lineList, List<Map<String, Object>> paramList) {
        super(nodeList, lineList, paramList);
    }

    public static DemoBuilder builder() {
        return new DemoBuilder();
    }

    public static class DemoBuilder {

        private List<Node> nodeList;

        private List<Line> lineList;

        private List<Map<String, Object>> paramList;

        public AbstractDemo build() {
            mockData();
            return new Demo0(nodeList, lineList, paramList);
        }

        private void mockData() {
            nodeList = new ArrayList<>();
            nodeList.add(new Node("a", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("b", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("c", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("d", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("e", ModuleIdEnum.DEFAULT_MODULE.name()));

            nodeList.add(new Node("A", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("B", "TEST_B", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("C", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("D", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("F", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("G", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("Z", ModuleIdEnum.DEFAULT_MODULE.name()));

            nodeList.add(new Node("E", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("H", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("I", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("J", ModuleIdEnum.DEFAULT_MODULE.name()));

            nodeList.add(new Node("K", ModuleIdEnum.DEFAULT_MODULE.name()));
            nodeList.add(new Node("L", ModuleIdEnum.DEFAULT_MODULE.name()));

            lineList = new ArrayList<>();
            lineList.add(new Line("L1", "A", "B"));
            lineList.add(new Line("L2", "A", "C"));
            lineList.add(new Line("L3", "A", "D"));
            lineList.add(new Line("L4", "C", "F"));
            lineList.add(new Line("L5", "C", "G"));
            lineList.add(new Line("L6", "C", "D"));
            lineList.add(new Line("L7", "D", "Z"));
            lineList.add(new Line("L8", "F", "Z"));
            lineList.add(new Line("L9", "G", "Z"));
            lineList.add(new Line("L10", "B", "F"));
            lineList.add(new Line("L11", "B", "E", Const.Node.DispatchRate.ASYNC));
            lineList.add(new Line("L12", "F", "E", Const.Node.DispatchRate.ASYNC));
            lineList.add(new Line("L13", "E", "H"));
            lineList.add(new Line("L14", "E", "I"));
            lineList.add(new Line("L15", "E", "J", Const.Node.DispatchRate.ASYNC));
            lineList.add(new Line("L16", "E", "L", Const.Node.DispatchRate.ASYNC));
            lineList.add(new Line("L17", "K", "B"));

            lineList.add(new Line("L18", "a", "c"));
            lineList.add(new Line("L19", "b", "c"));
            lineList.add(new Line("L20", "c", "d"));
            lineList.add(new Line("L21", "c", "e", Const.Node.DispatchRate.ASYNC));

            paramList = new ArrayList<>();
            paramList.add(JSON.parseObject("{\"id\":\"a\",\"second\":3}"));
            paramList.add(JSON.parseObject("{\"id\":\"b\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"c\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"d\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"e\",\"second\":3}"));

            paramList.add(JSON.parseObject("{\"id\":\"A\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"B\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"C\",\"second\":5}"));
            paramList.add(JSON.parseObject("{\"id\":\"D\",\"second\":10}"));
            paramList.add(JSON.parseObject("{\"id\":\"F\",\"second\":10}"));
            paramList.add(JSON.parseObject("{\"id\":\"G\",\"second\":15}"));
            paramList.add(JSON.parseObject("{\"id\":\"Z\",\"second\":1}"));

            paramList.add(JSON.parseObject("{\"id\":\"E\",\"second\":25}"));
            paramList.add(JSON.parseObject("{\"id\":\"H\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"I\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"J\",\"second\":1}"));

            paramList.add(JSON.parseObject("{\"id\":\"K\",\"second\":2}"));
            paramList.add(JSON.parseObject("{\"id\":\"L\",\"second\":1}"));

            paramList.add(JSON.parseObject("{\"id\":\"L1\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L2\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L3\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L4\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L5\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L6\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L7\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L8\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L9\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L10\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L11\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L12\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L13\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L14\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L15\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L16\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L17\",\"second\":1}"));

            paramList.add(JSON.parseObject("{\"id\":\"L18\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L19\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L20\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"L21\",\"second\":1}"));
        }
    }
}
