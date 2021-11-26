package com.jite.flow.example.mock;

import com.alibaba.fastjson.JSON;
import com.jite.flow.constant.Const;
import com.jite.flow.constant.JobModuleIdEnum;
import com.jite.flow.example.Line;
import com.jite.flow.example.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lux Sun
 * @date 2021/11/4
 */
public class Demo1 extends AbstractDemo {

    public Demo1(List<Node> nodeList, List<Line> lineList, List<Map<String, Object>> paramList) {
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
            return new Demo1(nodeList, lineList, paramList);
        }

        private void mockData() {
            nodeList = new ArrayList<>();
            nodeList.add(new Node("A", JobModuleIdEnum.DEFAULT_JOB.name()));
            nodeList.add(new Node("B", JobModuleIdEnum.DEFAULT_JOB.name()));
            nodeList.add(new Node("C", JobModuleIdEnum.DEFAULT_JOB.name()));

            lineList = new ArrayList<>();

            paramList = new ArrayList<>();
            paramList.add(JSON.parseObject("{\"id\":\"A\",\"second\":1}"));
            paramList.add(JSON.parseObject("{\"id\":\"B\",\"second\":5}"));
            paramList.add(JSON.parseObject("{\"id\":\"C\",\"second\":1}"));
        }
    }
}