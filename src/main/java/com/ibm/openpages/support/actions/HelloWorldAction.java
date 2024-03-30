package com.ibm.openpages.support.actions;

import com.ibm.openpages.api.workflow.actions.IWFCustomProperty;
import com.ibm.openpages.api.workflow.actions.IWFOperationContext;
import com.ibm.openpages.support.util.SimpleLogger;
import com.ibm.openpages.support.util.SimpleMethodLogger;

import java.util.List;

public class HelloWorldAction extends com.ibm.openpages.api.workflow.actions.AbstractCustomAction {

    private final SimpleLogger baseLogger = SimpleLogger.getLogger(this.getClass().getName());

    public HelloWorldAction(IWFOperationContext context, List<IWFCustomProperty> properties) {
        super(context, properties);
    }

    @Override
    protected void process() throws Exception {
        final SimpleMethodLogger logger = baseLogger.methodLogger("process");
        logger.entering();

        logger.info("Hello world from inside custom action");

        logger.exiting();
    }
}
