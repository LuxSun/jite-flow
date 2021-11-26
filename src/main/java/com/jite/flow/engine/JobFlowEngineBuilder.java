package com.jite.flow.engine;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public class JobFlowEngineBuilder {

    private JobFlowEngineBuilder() {}

    public static JobFlowEngine build() {
        return new JobFlowEngineImpl();
    }
}
