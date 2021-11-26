package com.jite.flow.bootstrap;

import com.jite.flow.example.mock.*;
import com.jite.flow.report.ReportContext;
import com.jite.flow.util.FlowUtil;
import org.junit.After;
import org.junit.Test;
import java.util.List;

/**
 * @author Lux Sun
 * @date 2021/10/18
 */
public class JiteFlowBootTest {

    private ReportContext reportContext;

    private AbstractDemo mock;

    @Test
    public void Demo0() {
        // Mock Demo0 数据
        this.mock = Demo0.builder().build();

        // 启动引擎(附带等待)
        this.reportContext = JiteFlowBoot.builder()
                .id(FlowUtil.UUIDUtil.randomUUID())
                .nodes(mock.getNodeList())
                .lines(mock.getLineList())
                .params(mock.getParams())
                .build()
                .invoke();
    }

    @Test
    public void Demo1() {
        // Mock Demo1 数据
        this.mock = Demo1.builder().build();

        // 启动引擎(附带等待)
        this.reportContext = JiteFlowBoot.builder()
                .id(FlowUtil.UUIDUtil.randomUUID())
                .nodes(mock.getNodeList())
                .lines(mock.getLineList())
                .params(mock.getParamList())
                .build()
                .invoke();
    }

    @Test
    public void Demo2() {
        // Mock Demo2 数据
        this.mock = Demo2.builder().build();

        // 启动引擎(附带等待)
        this.reportContext = JiteFlowBoot.builder()
                .id(FlowUtil.UUIDUtil.randomUUID())
                .nodes(mock.getNodeList())
                .lines(mock.getLineList())
                .params(mock.getParamList())
                .build()
                .invoke();
    }

    @After
    public void print() {
        // 输出结果
        List<String> result = (List<String>) this.reportContext.getJobContext().get("result");
        result.forEach(System.out::println);
    }
}