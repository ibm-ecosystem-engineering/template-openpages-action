package com.ibm.confidenceindex.models;

public enum AIPrinciple {
    Accountability("Accountability"),
    BiasAndFairness("BiasAndFairness", "Bias and Fairness"),
    Explainability("Explainability"),
    Privacy("Privacy"),
    Reliability("Reliability"),
    Security("Security"),
    Sustainability("Sustainability"),
    Transparency("Transparency");

    final private String value;
    final private String label;

    AIPrinciple(String value) {
        this(value, value);
    }

    AIPrinciple(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public static AIPrinciple lookup(String value) {
        final AIPrinciple[] values = AIPrinciple.values();
        for (int i = 0; i < values.length; i++) {
            final AIPrinciple val = values[i];

            if (val.value.equalsIgnoreCase(value)) {
                return val;
            }
        }

        return null;
    }

    public String value() {
        return value;
    }

    public String label() { return label; }
}
