package com.ibm.confidenceindex.models;

public class QuestionnaireAssessmentValues {
    private AIPrinciple aiPrinciple;
    private QuestionnaireType questionnaireType;
    private Integer complianceScore;
    private Integer maxScore;
    private Integer riskScore;

    public QuestionnaireAssessmentValues() {
    }

    public QuestionnaireAssessmentValues(AIPrinciple aiPrinciple, QuestionnaireType questionnaireArea, Integer complianceScore, Integer maxScore, Integer riskScore) {
        this();

        this.setAiPrinciple(aiPrinciple);
        this.setQuestionnaireType(questionnaireArea);
        this.complianceScore = complianceScore;
        this.maxScore = maxScore;
        this.riskScore = riskScore;
    }

    public AIPrinciple getAiPrinciple() {
        return aiPrinciple;
    }

    public void setAiPrinciple(String aiPrinciple) {
        this.setAiPrinciple(AIPrinciple.lookup(aiPrinciple));
    }

    public void setAiPrinciple(AIPrinciple aiPrinciple) {
        this.aiPrinciple = aiPrinciple;
    }

    public QuestionnaireAssessmentValues withAiPrinciple(String aiPrinciple) {
        this.setAiPrinciple(aiPrinciple);

        return this;
    }

    public QuestionnaireAssessmentValues withAiPrinciple(AIPrinciple aiPrinciple) {
        this.setAiPrinciple(aiPrinciple);

        return this;
    }

    public QuestionnaireType getQuestionnaireType() {
        return questionnaireType;
    }

    public void setQuestionnaireType(String questionnaireType) {
        this.setQuestionnaireType(QuestionnaireType.lookup(questionnaireType));
    }

    public void setQuestionnaireType(QuestionnaireType questionnaireType) {
        this.questionnaireType = questionnaireType;
    }

    public QuestionnaireAssessmentValues withQuestionnaireArea(String questionnaireArea) {
        this.setQuestionnaireType(questionnaireArea);

        return this;
    }

    public QuestionnaireAssessmentValues withQuestionnaireArea(QuestionnaireType questionnaireArea) {
        this.setQuestionnaireType(questionnaireArea);

        return this;
    }

    public Integer getComplianceScore() {
        return complianceScore;
    }

    public void setComplianceScore(Integer complianceScore) {
        this.complianceScore = complianceScore;
    }

    public QuestionnaireAssessmentValues withComplianceScore(Integer complianceScore) {
        this.complianceScore = complianceScore;

        return this;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public QuestionnaireAssessmentValues withMaxScore(Integer maxScore) {
        this.maxScore = maxScore;

        return this;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public QuestionnaireAssessmentValues withRiskScore(Integer riskScore) {
        this.riskScore = riskScore;

        return this;
    }

    public boolean isValid() {
        return this.questionnaireType != null && this.aiPrinciple != null;
    }

    public boolean isType(QuestionnaireType type) {
        return type == this.questionnaireType && isValid();
    }

    public String getField() {
        final ConfidenceIndexField field = ConfidenceIndexField.lookup(questionnaireType, aiPrinciple);

        if (field != null) {
            return field.value();
        }

        return null;
    }

    public ResultValue getValue() {
        switch (questionnaireType) {
            case BusinessImpact: {
                return AIPrinciple.Reliability.equals(aiPrinciple)
                        ? calculateBusinessImpactValue(20, 10)
                        : calculateBusinessImpactValue();
            }
            case Confidence: {
                return calculateConfidenceValue();
            }
            default: {
                return null;
            }
        }
    }

    protected BusinessImpactValue calculateBusinessImpactValue() {
        return calculateBusinessImpactValue(10, 0);
    }

    protected BusinessImpactValue calculateBusinessImpactValue(final int complianceHigh, final int maxScoreThreshold) {
        return calculateBusinessImpactValue(complianceHigh, 0, maxScoreThreshold);
    }

    protected BusinessImpactValue calculateBusinessImpactValue(final int complianceHigh, final int complianceLow, final int maxScoreThreshold) {
        if (complianceScore >= complianceHigh) {
            return BusinessImpactValue.High;
        } else if (complianceScore > complianceLow) {
            return BusinessImpactValue.Medium;
        } else if (complianceScore == complianceLow && maxScore > maxScoreThreshold) {
            return BusinessImpactValue.Low;
        } else {
            return BusinessImpactValue.NotApplicable;
        }
    }

    protected ConfidenceValue calculateConfidenceValue() {
        if (complianceScore < ConfidenceValue.Low.score()) {
            return ConfidenceValue.Low;
        } else if (complianceScore < ConfidenceValue.Medium.score()) {
            return ConfidenceValue.Medium;
        } else {
            return ConfidenceValue.High;
        }
    }

    public String toString() {
        return "[Questionnaire Assessment values (" +
                "aiPrinciple=" + aiPrinciple + ", " +
                "questionnaireType=" + questionnaireType + ", " +
                "complianceScore=" + complianceScore + ", " +
                "maxRiskScore=" + maxScore + ", " +
                "riskScore=" + riskScore + ")]";
    }
}
