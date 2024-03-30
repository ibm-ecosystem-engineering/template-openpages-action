package com.ibm.openpages.support.util;

import com.ibm.openpages.api.resource.IField;

public class SimpleMethodLogger {
    private final SimpleLogger logger;
    private final String method;

    public SimpleMethodLogger(SimpleLogger logger, String method) {
        this.logger = logger;
        this.method = method;
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void throwing(Throwable ex) {
        logger.throwing(method, ex);
    }

    public void entering() {
        logger.entering(method);
    }

    public void entering(Object[] params) {
        logger.entering(method, params);
    }

    public void exiting() {
        logger.exiting(method);
    }

    public <T> T exiting(T result) {
        logger.exiting(method, result);

        return result;
    }

    public void logSetField(String context, IField field, Object value) {
        logger.logSetField(context, field, value);
    }

}
