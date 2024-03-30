package com.ibm.openpages.support.util;

import com.ibm.openpages.api.resource.IField;

import java.util.logging.Logger;

public class SimpleLogger {
    private final Logger logger;
    private final String name;

    public SimpleLogger(String name, Logger logger) {
        this.name = name;
        this.logger = logger;
    }

    public static SimpleLogger getLogger(String name) {
        return new SimpleLogger(name, Logger.getLogger(name));
    }

    public static SimpleLogger getLogger(String name, String resourceBundleName) {
        return new SimpleLogger(name, Logger.getLogger(name, resourceBundleName));
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void throwing(String method, Throwable ex) {
        logger.throwing(name, method, ex);
    }

    public void entering(String method) {
        logger.entering(name, method);
    }

    public void entering(String method, Object[] params) {
        logger.entering(name, method, params);
    }

    public void exiting(String method) {
        logger.exiting(name, method);
    }

    public <T> T exiting(String method, T result) {
        logger.exiting(name, method, result);

        return result;
    }

    public void logSetField(String context, IField field, Object value) {
        logger.info("** Setting value for " + context + " field: " + field.getName() + "[" + field.getDataType().name() + "]" + "(" + field.getId() + ")=" + value);
    }

    public SimpleMethodLogger methodLogger(String method) {
        return new SimpleMethodLogger(this, method);
    }
}
