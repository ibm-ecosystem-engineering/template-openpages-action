package com.ibm.confidenceindex.actions;

import com.ibm.confidenceindex.models.AIPrinciple;
import com.ibm.confidenceindex.models.QuestionnaireType;
import com.ibm.openpages.api.metadata.Id;
import com.ibm.openpages.api.resource.IGRCObject;
import com.ibm.openpages.api.resource.IStringField;
import com.ibm.openpages.api.service.IResourceService;
import com.ibm.openpages.api.workflow.actions.IWFCustomProperty;
import com.ibm.openpages.api.workflow.actions.IWFOperationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static com.ibm.confidenceindex.util.OpenPagesUtil.getStringField;
import static com.ibm.confidenceindex.util.OpenPagesUtil.processQuery;

public class AssignAssessmentTemplateAction extends com.ibm.openpages.api.workflow.actions.AbstractCustomAction {
    public static final String FIELD_AI_PRINCIPLE = "ConfidenceIndex-QAssessment:AI Principle";
    public static final String FIELD_QUESTIONNAIRE_TYPE = "ConfidenceIndex-QAssessment:Questionnaire Area";
    public static final String FIELD_QUESTIONNAIRE_SECTION = "ConfidenceIndex-QAssessment:QuestionnaireSection";

    public static String QUESTIONNAIRE_TEMPLATE_SELECT_QUERY = "SELECT [QuestionnaireTemplate].[Resource ID] FROM [QuestionnaireTemplate] WHERE [QuestionnaireTemplate].[Name] = '";
    private static final String LOGGING_SOURCE = "AssignAssessmentTemplateAction";

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public AssignAssessmentTemplateAction(IWFOperationContext context, List<IWFCustomProperty> properties) {
        super(context, properties);
    }

    @Override
    protected void process() throws Exception {
        final String method = "process";
        entering(method);

        final IGRCObject assessment = getContext().getResource();
        logger.info("Assigning template to questionnaire assessment: " + assessment.getType().getName() + "(" + assessment.getId() + ")");

        List<Id> templates = lookupQuestionnaireTemplate(assessment);
        if (templates.isEmpty()) {
            logger.info("  No templates found for assessment");

            exiting(method);
            return;
        }

        logger.info("  Associating template to assessment: " + templates);
        associateTemplate(assessment, templates);

        getContext().flushResourceCache();

        exiting(method);
    }

    protected List<Id> lookupQuestionnaireTemplate(IGRCObject assessment) {
        final String method = "lookupQuestionnaireTemplate";
        entering(method);

        // get AIPrinciple and QuestionnaireArea values from object
        AIPrinciple aiPrinciple = getAIPrinciple(assessment);
        QuestionnaireType questionnaireArea = getQuestionnaireType(assessment);
        String section = getSection(assessment);

        if (aiPrinciple == null || questionnaireArea == null) {
            logger.info("  Filter fields not found in questionnaire assessment: " + FIELD_AI_PRINCIPLE + " or " + FIELD_QUESTIONNAIRE_TYPE);

            return exiting(method, new ArrayList<Id>());
        }

        final String templateName = section != null
                ? questionnaireArea.label() + " - " + aiPrinciple.label() + " - " + section
                : questionnaireArea.label() + " - " + aiPrinciple.label();

        logger.info("  Querying questionnaire template: '" + templateName + "'");

        return exiting(method, processQuery(getContext().getServiceFactory(), QUESTIONNAIRE_TEMPLATE_SELECT_QUERY + templateName + "'"));
    }

    protected void associateTemplate(IGRCObject assessment, List<Id> templates) {
        final String method = "associateTemplate";
        entering(method);

        final IResourceService service = getContext().getServiceFactory().createResourceService();
        service.associate(assessment.getId(), new ArrayList<Id>(), templates);
        service.saveResource(assessment);

        exiting(method);
    }

    private AIPrinciple getAIPrinciple(IGRCObject assessment) {
        final IStringField field = getStringField(assessment, FIELD_AI_PRINCIPLE);

        if (field == null) {
            return null;
        }

        return AIPrinciple.lookup(field.getValue());
    }

    private QuestionnaireType getQuestionnaireType(IGRCObject assessment) {
        final IStringField field = getStringField(assessment, FIELD_QUESTIONNAIRE_TYPE);

        if (field == null) {
            return null;
        }

        return QuestionnaireType.lookup(field.getValue());
    }

    private String getSection(IGRCObject assessment) {
        final IStringField field = getStringField(assessment, FIELD_QUESTIONNAIRE_SECTION);

        if (field == null) {
            return null;
        }

        return field.getValue();
    }

    private void entering(String method) {
        logger.entering(LOGGING_SOURCE, method);
    }

    private void entering(String method, Object[] params) {
        logger.entering(LOGGING_SOURCE, method);
    }

    private void exiting(String method) {
        logger.exiting(LOGGING_SOURCE, method);
    }

    private <T> T exiting(String method, T result) {
        logger.exiting(LOGGING_SOURCE, method, result);

        return result;
    }
}
