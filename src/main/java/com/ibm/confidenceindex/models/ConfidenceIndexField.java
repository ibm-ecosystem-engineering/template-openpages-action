package com.ibm.confidenceindex.models;

public enum ConfidenceIndexField {
    BusinessImpactAccountability(QuestionnaireType.BusinessImpact, AIPrinciple.Accountability, "ConfidenceIndex:BusinessImpact-Accountability"),
    BusinessImpactBiasAndFairness(QuestionnaireType.BusinessImpact, AIPrinciple.BiasAndFairness, "ConfidenceIndex:BusinessImpact-BiasAndFairness"),
    BusinessImpactExplainability(QuestionnaireType.BusinessImpact, AIPrinciple.Explainability, "ConfidenceIndex:BusinessImpact-Explainability"),
    BusinessImpactPrivacy(QuestionnaireType.BusinessImpact, AIPrinciple.Privacy, "ConfidenceIndex:BusinessImpact-Privacy"),
    BusinessImpactReliability(QuestionnaireType.BusinessImpact, AIPrinciple.Reliability, "ConfidenceIndex:BusinessImpact-Reliability"),
    BusinessImpactSecurity(QuestionnaireType.BusinessImpact, AIPrinciple.Security, "ConfidenceIndex:BusinessImpact-Security"),
    BusinessImpactSustainability(QuestionnaireType.BusinessImpact, AIPrinciple.Sustainability, "ConfidenceIndex:BusinessImpact-Sustainability"),
    BusinessImpactTransparency(QuestionnaireType.BusinessImpact, AIPrinciple.Transparency, "ConfidenceIndex:BusinessImpact-Transparency"),
    ConfidenceAccountability(QuestionnaireType.Confidence, AIPrinciple.Accountability, "ConfidenceIndex:Confidence-Accountability"),
    ConfidenceBiasAndFairness(QuestionnaireType.Confidence, AIPrinciple.BiasAndFairness, "ConfidenceIndex:Confidence-BiasAndFairness"),
    ConfidenceExplainability(QuestionnaireType.Confidence, AIPrinciple.Explainability, "ConfidenceIndex:Confidence-Explainability"),
    ConfidencePrivacy(QuestionnaireType.Confidence, AIPrinciple.Privacy, "ConfidenceIndex:Confidence-Privacy"),
    ConfidenceReliability(QuestionnaireType.Confidence, AIPrinciple.Reliability, "ConfidenceIndex:Confidence-Reliability"),
    ConfidenceSecurity(QuestionnaireType.Confidence, AIPrinciple.Security, "ConfidenceIndex:Confidence-Security"),
    ConfidenceSustainability(QuestionnaireType.Confidence, AIPrinciple.Sustainability, "ConfidenceIndex:Confidence-Sustainability"),
    ConfidenceTransparency(QuestionnaireType.Confidence, AIPrinciple.Transparency, "ConfidenceIndex:Confidence-Transparency");

    final private QuestionnaireType questionnaireType;
    final private AIPrinciple aiPrinciple;
    final private String value;

    ConfidenceIndexField(QuestionnaireType questionnaireType, AIPrinciple aiPrinciple, String value) {
        this.questionnaireType = questionnaireType;
        this.aiPrinciple = aiPrinciple;
        this.value = value;
    }

    public static ConfidenceIndexField lookup(QuestionnaireType questionnaireType, AIPrinciple aiPrinciple) {
        final ConfidenceIndexField[] values = ConfidenceIndexField.values();
        for (int i = 0; i < values.length; i++) {
            final ConfidenceIndexField f = values[i];

            if (f.questionnaireType.equals(questionnaireType) && f.aiPrinciple.equals(aiPrinciple)) {
                return f;
            }
        }

        return null;
    }

    public static ConfidenceIndexField lookup(String value) {
        final ConfidenceIndexField[] values = ConfidenceIndexField.values();
        for (int i = 0; i < values.length; i++) {
            final ConfidenceIndexField f = values[i];

            if (f.value.equalsIgnoreCase(value)) {
                return f;
            }
        }

        return null;
    }

    public QuestionnaireType questionnaireType() {
        return questionnaireType;
    }

    public AIPrinciple aiPrinciple() {
        return aiPrinciple;
    }

    public String value() {
        return value;
    }
}
