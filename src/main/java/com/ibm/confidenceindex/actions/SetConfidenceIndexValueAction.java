package com.ibm.confidenceindex.actions;

import com.ibm.confidenceindex.models.QuestionnaireAssessmentValues;
import com.ibm.confidenceindex.models.QuestionnaireType;
import com.ibm.confidenceindex.util.OpenPagesUtil;
import com.ibm.openpages.api.resource.IAssociationNode;
import com.ibm.openpages.api.resource.IGRCObject;
import com.ibm.openpages.api.resource.IIntegerField;
import com.ibm.openpages.api.resource.IStringField;
import com.ibm.openpages.api.workflow.actions.IWFCustomProperty;
import com.ibm.openpages.api.workflow.actions.IWFOperationContext;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import static com.ibm.confidenceindex.util.OpenPagesUtil.getIntegerField;
import static com.ibm.confidenceindex.util.OpenPagesUtil.getStringField;

/**
 *
 */
public class SetConfidenceIndexValueAction extends com.ibm.openpages.api.workflow.actions.AbstractCustomAction {
    private static final String LOGGING_SOURCE = "AssignAssessmentTemplateAction";

    private final Logger logger = Logger.getLogger(this.getClass().toString());

    public SetConfidenceIndexValueAction(IWFOperationContext context, List<IWFCustomProperty> properties) {
        super(context, properties);
    }

    @Override
    protected void process() throws Exception {
        final String method = "process";
        entering(method);

        // assert object instanceof Questionnaire Assessment
        final IGRCObject assessment = getContext().getResource();
        logger.info("Updating ModelUseCase associated with QuestionnaireAssessment: " + assessment.getType().getName() + "(" + assessment.getId() + ")");

        final IGRCObject modelUseCase = getModelUseCase(assessment);
        if (modelUseCase == null) {
            logger.info("  Unable to find ModelUseCase associated with QuestionnaireAssessment");

            exiting(method);
            return;
        }

        final QuestionnaireAssessmentValues value = getAssessmentValues(assessment);
        if (value == null) {
            return;
        }

        IStringField resultField = getStringField(modelUseCase, value.getField());

        if (resultField == null) {
            return;
        }

        resultField.setValue(value.getValue().value());
    }

    protected IGRCObject getModelUseCase(IGRCObject assessment) {
        final String method = "getModelUseCase";
        entering(method, assessment);

        IGRCObject result = null;

        final Iterator<IAssociationNode> childrenIterator = assessment.getChildren().iterator();
        while (childrenIterator.hasNext()) {
            IAssociationNode node = childrenIterator.next();

            if (isModelUseCase(node)) {
                result = lookupResource(node);
                break;
            }
        }

        return exiting(method, result);
    }

    protected IGRCObject lookupResource(IAssociationNode node) {
        final String method = "lookupResource";
        entering(method, node);

        return exiting(
                method,
                OpenPagesUtil.lookupResource(getContext().getServiceFactory(), node.getId()));
    }

    protected QuestionnaireAssessmentValues getAssessmentValues(IGRCObject assessment) {
        final String method = "getAssessmentValues";
        entering(method, assessment);

        final QuestionnaireAssessmentValues value = new QuestionnaireAssessmentValues()
                .withAiPrinciple(getStringFieldValue(assessment, "ConfidenceIndex-QAssessment:AI Principle"))
                .withQuestionnaireArea(getStringFieldValue(assessment, "ConfidenceIndex-QAssessment:Questionnaire Area"))
                .withComplianceScore(getIntegerFieldValue(assessment, "OPSS-QAssessment:ComplianceScore"))
                .withMaxScore(getIntegerFieldValue(assessment, "OPSS-QAssessment:MaxRiskScore"))
                .withRiskScore(getIntegerFieldValue(assessment, "OPSS-QAssessment:RiskScore"));

        logger.info("Questionnaire Assessment values: " + value);

        return exiting(
                method,
                value.isType(QuestionnaireType.Confidence) ? value : null
        );
    }

    protected String getStringFieldValue(IGRCObject object, String name) {
        final IStringField field = getStringField(object, name);

        if (field == null) {
            return null;
        }

        return field.getValue();
    }

    protected Integer getIntegerFieldValue(IGRCObject object, String name) {
        final IIntegerField field = getIntegerField(object, name);

        if (field == null) {
            return null;
        }

        return field.getValue();
    }

    private boolean isModelUseCase(IAssociationNode node) {
        final boolean match = OpenPagesUtil.isModelUseCase(node.getType());

        logger.fine("isModelUseCase? " + node.getType().getName() + "::" + match);

        return match;
    }

    private void entering(String method) {
        logger.entering(LOGGING_SOURCE, method);
    }

    private void entering(String method, Object param) {
        logger.entering(LOGGING_SOURCE, method, param);
    }

    private void entering(String method, Object[] params) {
        logger.entering(LOGGING_SOURCE, method, params);
    }

    private void exiting(String method) {
        logger.exiting(LOGGING_SOURCE, method);
    }

    private <T> T exiting(String method, T result) {
        logger.exiting(LOGGING_SOURCE, method, result);

        return result;
    }

}

