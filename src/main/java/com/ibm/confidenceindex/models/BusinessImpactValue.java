package com.ibm.confidenceindex.models;

public enum BusinessImpactValue implements ResultValue {
    High("High", 100),
    Medium("Medium", 67),
    Low("Low", 33),
    NotApplicable("N/A", 0);

    final private String value;
    final private int score;

    BusinessImpactValue(String value, int score) {
        this.value = value;
        this.score = score;
    }

    public String value() {
        return value;
    }

    public int score() {
        return score;
    }
}
