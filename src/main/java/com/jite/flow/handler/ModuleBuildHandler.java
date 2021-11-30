package com.jite.flow.handler;

import com.alibaba.fastjson.JSON;
import com.jite.flow.constant.ModuleIdEnum;
import com.jite.flow.job.DefaultModule;
import com.jite.flow.job.Module;
import com.jite.flow.util.FlowUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author Lux Sun
 * @date 2021/11/2
 */
public class ModuleBuildHandler {

    private static final Logger LOG = Logger.getLogger(ModuleBuildHandler.class.getName());

    private static final Map<String, Module> MODULE_MAP = new ConcurrentHashMap<>();

    static {
        register(ModuleIdEnum.DEFAULT_MODULE, new DefaultModule());
    }

    public static void init() {
        LOG.info("Load the Modules successfully");
    }

    public static Module getModule(String moduleId) {
        return MODULE_MAP.get(moduleId);
    }

    public static Module getModule(String moduleId, String moduleParam) {
        if (FlowUtil.StringUtil.isEmpty(moduleId)) {
            return null;
        }

        // 获取 moduleId 对应的 <? implements Module>.class
        Module module = MODULE_MAP.get(moduleId);
        return JSON.parseObject(moduleParam, module.getClass());
    }

    public static Module getModule(Enum moduleId, String moduleParam) {
        return getModule(moduleId.name(), moduleParam);
    }

    public static void register(String moduleId, Module module) {
        MODULE_MAP.put(moduleId, module);
    }

    public static void register(Enum moduleId, Module module) {
        register(moduleId.name(), module);
    }
}