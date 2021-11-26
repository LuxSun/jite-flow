package com.jite.flow.config;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Lux Sun
 * @date 2021/10/19
 */
public class LoggerConfig {

    public static final Logger LOG = Logger.getLogger(LoggerConfig.class.getName());

    static {
        if (System.getProperty("java.util.logging.config.file") == null &&
                System.getProperty("java.util.logging.config.class") == null) {
            try {
                LogManager.getLogManager().readConfiguration(LoggerConfig.class.getResourceAsStream("/log.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOG.info("Load the Logger successfully");
    }

    public static void init() {}
}