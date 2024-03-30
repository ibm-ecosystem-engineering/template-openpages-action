package com.ibm.openpages.support.util;

import com.ibm.openpages.api.metadata.IEnumValue;
import com.ibm.openpages.api.metadata.ITypeDefinition;
import com.ibm.openpages.api.metadata.Id;
import com.ibm.openpages.api.query.IQuery;
import com.ibm.openpages.api.query.IResultSetRow;
import com.ibm.openpages.api.query.ITabularResultSet;
import com.ibm.openpages.api.resource.*;
import com.ibm.openpages.api.service.IQueryService;
import com.ibm.openpages.api.service.IResourceService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.ibm.openpages.support.util.StreamUtil.asStream;

public class OpenPagesUtil {
    private static final SimpleLogger logger = SimpleLogger.getLogger(OpenPagesUtil.class.getName());

    public static String getStringFieldValue(IGRCObject object, ResultValue name) {
        return getStringFieldValue(object, name.value());
    }

    public static String getStringFieldValue(IGRCObject object, String name) {
        return getStringField(object, name)
                .map(IStringField::getValue)
                .orElse(null);
    }

    public static Optional<IStringField> getStringField(IGRCObject object, ResultValue name) {
        return getStringField(object, name.value());
    }
    public static Optional<IStringField> getStringField(IGRCObject object, String name) {
        return Optional.ofNullable((IStringField) object.getField(name));
    }

    public static Date getDateFieldValue(IGRCObject object, ResultValue name) {
        return getDateFieldValue(object, name.value());
    }

    public static Date getDateFieldValue(IGRCObject object, String name) {
        return getDateField(object, name)
                .map(IDateField::getValue)
                .orElse(null);
    }

    public static Optional<IDateField> getDateField(IGRCObject object, ResultValue name) {
        return getDateField(object, name.value());
    }

    public static Optional<IDateField> getDateField(IGRCObject object, String name) {
        return Optional.ofNullable((IDateField) object.getField(name));
    }

    public static String getEnumFieldValue(IGRCObject object, ResultValue name) {
        return getEnumFieldValue(object, name.value());
    }

    public static String getEnumFieldValue(IGRCObject object, String name) {
        return getEnumField(object, name)
                .map(IEnumField::getEnumValue)
                .map(IEnumValue::getName)
                .orElse(null);
    }

    public static Optional<Double> getFloatFieldValue(IGRCObject object, ResultValue name) {
        return getFloatFieldValue(object, name.value());
    }

    public static Optional<Double> getFloatFieldValue(IGRCObject object, String name) {
        return getFloatField(object, name).map(IFloatField::getValue);
    }

    public static Optional<IFloatField> getFloatField(IGRCObject object, ResultValue name) {
        return getFloatField(object, name.value());
    }
    public static Optional<IFloatField> getFloatField(IGRCObject object, String name) {
        return Optional.ofNullable((IFloatField) object.getField(name));
    }

    public static Optional<IEnumField> getEnumField(IGRCObject object, ResultValue name) {
        return getEnumField(object, name.value());
    }
    public static Optional<IEnumField> getEnumField(IGRCObject object, String name) {
        return Optional.ofNullable((IEnumField) object.getField(name));
    }

    public static Integer getIntegerFieldValue(IGRCObject object, ResultValue name) {
        return getIntegerFieldValue(object, name.value());
    }

    public static Integer getIntegerFieldValue(IGRCObject object, String name) {
        return getIntegerField(object, name)
                .map(IIntegerField::getValue)
                .orElse(null);
    }

    public static Object getFieldValue(IGRCObject object, FieldMetadata nameAndType) {
        if (nameAndType.isEnumField()) {
            return getEnumFieldValue(object, nameAndType);
        } else if (nameAndType.isDateField()) {
            return getDateFieldValue(object, nameAndType);
        } else if (nameAndType.isDecimalField()) {
            return getFloatFieldValue(object, nameAndType);
        } else if (nameAndType.isStringField()) {
            return getStringFieldValue(object, nameAndType);
        } else if (nameAndType.isIntegerField()) {
            return getIntegerFieldValue(object, nameAndType);
        }

        return null;
    }

    public static Optional<IIntegerField> getIntegerField(IGRCObject object, ResultValue name) {
        return getIntegerField(object, name.value());
    }
    public static Optional<IIntegerField> getIntegerField(IGRCObject object, String name) {
        return Optional.ofNullable((IIntegerField) object.getField(name));
    }

    public static List<Id> processQuery(final IQueryService queryService, final String querySelect) {
        final IQuery query = queryService.buildQuery(querySelect);
        final ITabularResultSet resultSet = query.fetchRows(0);

        return asStream(resultSet.iterator())
                .flatMap((IResultSetRow row) -> asStream(row.iterator()))
                .filter((IField field) -> field instanceof IIdField)
                .map((IField field) -> ((IIdField) field).getValue())
                .collect(Collectors.toList());
    }

    public static IGRCObject lookupResource(IResourceService service, IAssociationNode node) {
        return lookupResource(service, node.getId(), null);
    }
    public static IGRCObject lookupResource(IResourceService service, Id id) {
        return lookupResource(service, id, null);
    }

    public static IGRCObject lookupResource(IResourceService service, IAssociationNode node, IncludeAssociations includeAssociations) {
        return lookupResource(service, node.getId(), includeAssociations);
    }
    public static IGRCObject lookupResource(IResourceService service, Id id, IncludeAssociations includeAssociations) {
        if (includeAssociations == null) {
            return service.getGRCObject(id);
        }

        final GRCObjectFilter filter = new GRCObjectFilter();
        filter.getAssociationFilter().setIncludeAssociations(includeAssociations);

        return service.getGRCObject(id, filter);
    }

    public static boolean isModelUseCase(IGRCObject obj) {
        return isModelUseCase(obj.getType());
    }

    public static boolean isModelUseCase(IAssociationNode node) {
        return isModelUseCase(node.getType());
    }

    public static boolean isModelUseCase(ITypeDefinition type) {
        final boolean result = "Model Use Case".equalsIgnoreCase(type.getName()) || "Register".equalsIgnoreCase(type.getName());

        logger.info("isModelUseCase(" + type.getName() + ")? " + result);

        return result;
    }

    public static boolean isModelOrDeployment(IGRCObject obj) {
        return isModelOrDeployment(obj.getType());
    }

    public static boolean isModelOrDeployment(IAssociationNode node) {
        return isModelOrDeployment(node.getType());
    }

    public static boolean isModelOrDeployment(ITypeDefinition type) {
        return isModel(type) || isDeployment(type);
    }

    public static boolean isDeployment(IGRCObject obj) {
        return isDeployment(obj.getType());
    }
    public static boolean isDeployment(IAssociationNode node) {
        return isDeployment(node.getType());
    }

    public static boolean isDeployment(ITypeDefinition type) {
        final boolean result = "Deployment".equalsIgnoreCase(type.getName());

        logger.info("isDeployment(" + type.getName() + ") " + result);

        return result;
    }

    public static boolean isModel(IGRCObject obj) {
        return isModel(obj.getType());
    }
    public static boolean isModel(IAssociationNode node) {
        return isModel(node.getType());
    }
    public static boolean isModel(ITypeDefinition type) {
        final boolean result = "Model".equalsIgnoreCase(type.getName());

        logger.info("isModel(" + type.getName() + ")? " + result);

        return result;
    }

    public static boolean isModelRiskScorecard(IGRCObject obj) {
        return isModelRiskScorecard(obj.getType());
    }
    public static boolean isModelRiskScorecard(IAssociationNode node) {
        return isModelRiskScorecard(node.getType());
    }
    public static boolean isModelRiskScorecard(ITypeDefinition type) {
        final boolean result = "ModelScorecard".equalsIgnoreCase(type.getName());

        logger.info("isModelRiskScorecard(" + type.getName() + ")? " + result);

        return result;
    }

    public static boolean isMetric(IGRCObject obj) {
        return isMetric(obj.getType());
    }

    public static boolean isMetric(IAssociationNode node) {
        return isMetric(node.getType());
    }

    public static boolean isMetric(ITypeDefinition type) {
        final boolean result = "Metric".equalsIgnoreCase(type.getName());

        logger.info("isMetric(" + type.getName() + ")? " + result);

        return result;
    }

    public static boolean isMetricValue(IGRCObject obj) {
        return isMetricValue(obj.getType());
    }

    public static boolean isMetricValue(IAssociationNode node) {
        return isMetricValue(node.getType());
    }

    public static boolean isMetricValue(ITypeDefinition type) {
        final boolean result = "MetricValue".equalsIgnoreCase(type.getName());

        logger.info("isMetricValue(" + type.getNameDisplay() + ")? " + result);

        return result;
    }

    public static boolean isAssessmentTemplate(IAssociationNode node) {
        return isAssessmentTemplate(node.getType());
    }

    public static boolean isAssessmentTemplate(ITypeDefinition obj) {
        final boolean result = "AssessmentTemplate".equalsIgnoreCase(obj.getName()) || "Assessment Template".equalsIgnoreCase(obj.getName());

        logger.info("isAssessmentTemplate(" + obj.getName() + ")? " + result);

        return result;
    }

    public static boolean isQuestionnaireAssessment(IAssociationNode node) {
        return isQuestionnaireAssessment(node.getType());
    }

    public static boolean isQuestionnaireAssessment(ITypeDefinition type) {
        final boolean result = "Questionnaire Assessment".equalsIgnoreCase(type.getName()) || "QuestionnaireAssessment".equalsIgnoreCase(type.getName());

        logger.info("isQuestionnaireAssessment(" + type.getName() + ")? " + result);

        return result;
    }

    public static boolean assessmentMissingTemplate(IGRCObject assessment) {
        return !assessmentHasTemplate(assessment);
    }

    public static boolean assessmentHasTemplate(IGRCObject assessment) {
        final SimpleMethodLogger log = logger.methodLogger("assessmentHasTemplate");
        log.entering(new Object[]{assessment});

        final boolean result = assessment.getChildren().stream()
                .map(OpenPagesUtil::isAssessmentTemplate)
                .anyMatch(val -> val);

        if (result) {
            log.info("  Found questionnaire template");
        } else {
            log.info("  Questionnaire template not found");
        }

        return log.exiting(result);
    }

    public static void associateTemplateWithAssessment(IResourceService service, IGRCObject assessment, List<Id> templates) {
        final SimpleMethodLogger log = logger.methodLogger("associateTemplateWithAssessment");
        log.entering(new Object[]{service, assessment, templates});

        if (service == null) {
            throw new NullPointerException("ResourceService cannot be null!!!");
        }

        log.info("  Adding template as child to assessment( " + assessment.getId() + "): " + templates);
        service.associate(assessment.getId(), Collections.emptyList(), templates);

        log.info("  Saving assessment with children");
        service.saveResource(assessment);

        log.exiting();
    }

    public static void findAndApplyMatchingEnumValue(IEnumField field, String value) {
        findMatchingEnumValue(field, value).ifPresent(v -> {
            logger.logSetField("enum", field, v);
            field.setEnumValue(v);
        });
    }

    public static void findAndApplyMatchingEnumValue(IEnumField field, ResultValue value) {
        findMatchingEnumValue(field, value).ifPresent(v -> {
            logger.logSetField("enum", field, v);
            field.setEnumValue(v);
        });
    }

    public static Optional<IEnumValue> findMatchingEnumValue(IEnumField field, ResultValue value) {
        return findMatchingEnumValue(field, value.value());
    }

    public static Optional<IEnumValue> findMatchingEnumValue(IEnumField field, String value) {
        final SimpleMethodLogger log = logger.methodLogger("findMatchingEnumValue");
        log.entering();

        final Optional<IEnumValue> result = asStream(field.getFieldDefinition().getEnumValues().iterator())
                .filter((IEnumValue enumValue) -> enumValue.getName().equalsIgnoreCase(value))
                .findFirst();

        if (result.isPresent()) {
            log.info("  Found enum match for value: " + value);
        }

        return log.exiting(result);
    }

    public static boolean setStringField(IGRCObject target, String fieldName, String fieldValue) {
        final SimpleMethodLogger log = logger.methodLogger("setStringField");
        log.entering();

        return getStringField(target, fieldName)
                .map(f -> {
                    logger.info("  Setting value on field: " + fieldName + "=" + fieldValue);
                    f.setValue(fieldValue);
                    return log.exiting(true);
                })
                .orElseGet(() -> {
                    log.info("  Unable to find matching field target object(" + target.getType().getName() + "): " + fieldName);
                    return log.exiting(false);
                });
    }

    public static boolean setIntegerField(IGRCObject target, ResultValue fieldName, Integer fieldValue) {
        return setIntegerField(target, fieldName.value(), fieldValue);
    }
    public static boolean setIntegerField(IGRCObject target, String fieldName, Integer fieldValue) {
        final SimpleMethodLogger log = logger.methodLogger("setIntegerField");
        log.entering();

        Optional<IIntegerField> resultField = getIntegerField(target, fieldName);

        if (!resultField.isPresent()) {
            log.info("  Unable to find matching field target object(" + target.getType().getName() + "): " + fieldName);
            return log.exiting(false);
        }

        logger.info("  Setting value on field: " + fieldName + "=" + fieldValue);
        resultField.ifPresent(f -> f.setValue(fieldValue));

        return true;
    }

    public static boolean setEnumField(IGRCObject target, ResultValue fieldName, String fieldValue) {
        return setEnumField(target, Optional.ofNullable(fieldName).map(ResultValue::value).orElse(null), fieldValue);
    }

    public static boolean setEnumField(IGRCObject target, ResultValue fieldName, ResultValue fieldValue) {
        return setEnumField(target, Optional.ofNullable(fieldName).map(ResultValue::value).orElse(null), Optional.ofNullable(fieldValue).map(ResultValue::value).orElse(null));
    }

    public static boolean setEnumField(IGRCObject target, String fieldName, ResultValue fieldValue) {
        return setEnumField(target, fieldName, Optional.ofNullable(fieldValue).map(ResultValue::value).orElse(null));
    }

    public static boolean setEnumField(IGRCObject target, String fieldName, String fieldValue) {
        final SimpleMethodLogger log = logger.methodLogger("setEnumField");
        log.entering();

        final Optional<IEnumField> resultField = getEnumField(target, fieldName);

        if (!resultField.isPresent()) {
            log.info("  Unable to find matching field target object(" + target.getType().getName() + "): " + fieldName);
            return log.exiting(false);
        }

        return resultField
                .flatMap(f -> findMatchingEnumValue(f, fieldValue))
                .map(val -> {
                    logger.logSetField("enum", resultField.get(), val.getName());
                    resultField.get().setEnumValue(val);
                    return log.exiting(true);
                })
                .orElseGet(() -> {
                    log.info("  Unable to find matching enum value on field(" + fieldName + "): " + fieldValue);
                    return log.exiting(false);
                });
    }

    public static boolean setFloatField(IGRCObject target, ResultValue fieldName, Double fieldValue) {
        return setFloatField(target, fieldName.value(), fieldValue);
    }
    public static boolean setFloatField(IGRCObject target, String fieldName, Double fieldValue) {
        final SimpleMethodLogger log = logger.methodLogger("setFloatField");
        log.entering();

        Optional<IFloatField> resultField = getFloatField(target, fieldName);

        if (!resultField.isPresent()) {
            log.info("  Unable to find matching field target object(" + target.getType().getName() + "): " + fieldName);
            return log.exiting(false);
        }

        logger.info("  Setting value on field: " + fieldName + "=" + fieldValue);
        resultField.ifPresent(f -> f.setValue(fieldValue));

        return true;
    }

    public static void setFloatValue(IFloatField field, Double value) {
        final Double truncatedValue = BigDecimal.valueOf(value)
                .setScale(8, RoundingMode.HALF_UP)
                .doubleValue();

        logger.logSetField("float", field, truncatedValue);
        field.setValue(truncatedValue);
    }

    public static Optional<IGRCObject> getModelUseCaseFromMetricValue(IResourceService service, IGRCObject metricValue) {
        // metricValue -> metric -> model || deployment -> modelUseCase
        final IGRCObject metric = lookupResource(service, metricValue.getPrimaryParent());

        if (!isMetric(metric)) {
            logger.info("  The primary parent of MetricValue is not a Metric: " + metric.getType().getName());
            return Optional.empty();
        }

        final IGRCObject model = lookupResource(service, metric.getPrimaryParent(), IncludeAssociations.PARENT);

        if (!isModelOrDeployment(model)) {
            logger.info("  The primary parent of Metric is not a Model or Deployment: " + model.getType().getName());
            return Optional.empty();
        }

        final List<IAssociationNode> modelUseCaseParents = model.getParents().stream()
                .filter(OpenPagesUtil::isModelUseCase)
                .collect(Collectors.toList());

        logger.info("  Found " + modelUseCaseParents.size() + " Model Use Case parents for model: " + model.getName());

        final Optional<Id> modelUseCaseId = modelUseCaseParents.stream()
                .findFirst()
                .map(IAssociationNode::getId);

        if (!modelUseCaseId.isPresent()) {
            logger.info("  Unable to find Model Use Case parent of Model: " + model.getName());
            return Optional.empty();
        }

        // TODO handle multiple use cases associated with model
        //   the calculated values need to be set on the Model then replicated into all the use cases associated with the model
        final IGRCObject modelUseCase = lookupResource(service, modelUseCaseId.get(), IncludeAssociations.CHILD);

        if (!isModelUseCase(modelUseCase)) {
            logger.info("  The parent of Model is not a Model Use Case: " + modelUseCase.getType().getName());
            return Optional.empty();
        }

        return Optional.of(modelUseCase);
    }

    public static Optional<IGRCObject> getModelFromMetricValue(IResourceService service, IGRCObject metricValue) {
        // metricValue -> metric -> model || deployment -> modelUseCase
        final IGRCObject metric = lookupResource(service, metricValue.getPrimaryParent());

        if (!isMetric(metric)) {
            logger.info("  The primary parent of MetricValue is not a Metric: " + metric.getType().getName());
            return Optional.empty();
        }

        final IGRCObject modelOrDeployment = lookupResource(service, metric.getPrimaryParent(), IncludeAssociations.BOTH);

        if (!isModelOrDeployment(modelOrDeployment)) {
            logger.info("  The primary parent of Metric is not a Model or Deployment: " + modelOrDeployment.getType().getName());
            return Optional.empty();
        }

        if (isModel(modelOrDeployment)) {
            return Optional.of(modelOrDeployment);
        }

        final IGRCObject model = lookupResource(service, modelOrDeployment.getPrimaryParent(), IncludeAssociations.CHILD);
        if (!isModel(model)) {
            logger.info("  The primary parent of Deployment is not a Model: " + model.getType().getName());
            return Optional.empty();
        }

        return Optional.of(model);
    }

    public static boolean isGreaterThan(IGRCObject obj, ResultValue fieldName, Double minValue) {
        return getFloatField(obj, fieldName)
                .map(IFloatField::getValue)
                .map(val -> {
                    final boolean result = val > minValue;
                    logger.info("  isGreaterThan(" + fieldName + "[" + val + "], " + minValue + ")? " + result);

                    return result;
                })
                .orElseGet(() -> {
                    logger.info("  Unable to find field value: " + fieldName);

                    return false;
                });
    }

    public static boolean isMatchingValue(IGRCObject obj, ResultValue fieldName, ResultValueAndLabel fieldValue) {
        return getEnumField(obj, fieldName)
                .map(field -> field.getEnumValue().getName())
                .map(name -> {
                    final boolean result = name.equalsIgnoreCase(fieldValue.value()) || name.equalsIgnoreCase(fieldValue.label());
                    logger.info("  isMatchingValue(" + name + ", " + fieldValue + "): " + result);

                    return result;
                })
                .orElse(false);
    }

    public static boolean isNotMatchingValue(IGRCObject obj, ResultValue fieldName, ResultValueAndLabel fieldValue) {
        return getEnumField(obj, fieldName)
                .map(field -> field.getEnumValue().getName())
                .map(name -> {
                    final boolean result = !name.equalsIgnoreCase(fieldValue.value()) && !name.equalsIgnoreCase(fieldValue.label());
                    logger.info("  isNotMatchingValue(" + name + ", " + fieldValue + "): " + result);

                    return result;
                })
                .orElse(false);
    }

    public static List<IGRCObject> getAssessmentsFromModel(IResourceService service, IGRCObject model) {
        return model.getChildren().stream()
                .filter(OpenPagesUtil::isQuestionnaireAssessment)
                .map(node -> lookupResource(service, node, IncludeAssociations.CHILD))
                .collect(Collectors.toList());
    }
}
