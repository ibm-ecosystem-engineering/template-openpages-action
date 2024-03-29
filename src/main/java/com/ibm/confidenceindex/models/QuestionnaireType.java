package com.ibm.confidenceindex.models;

public enum QuestionnaireType {
    BusinessImpact("BusinessImpact", "Business Impact"),
    Confidence("Confidence");

    final private String value;
    final private String label;

    QuestionnaireType(String value) {
        this(value, value);
    }
    QuestionnaireType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static QuestionnaireType lookup(final String value) {
        final QuestionnaireType[] values = QuestionnaireType.values();
        for (int i = 0; i < values.length; i++) {
            final QuestionnaireType val = values[i];

            if (val.value.equalsIgnoreCase(value)) {
                return val;
            }
        }

        return null;
    }

    public String value() {
        return value;
    }

    public String label() {
        return label;
    }
}
