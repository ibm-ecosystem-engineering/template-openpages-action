package com.ibm.confidenceindex.models;

public enum ConfidenceValue implements ResultValue {
    High("High", 100),
    Medium("Medium", 67),
    Low("Low", 33);

    final private String value;
    final private int score;

    ConfidenceValue(String value, int score) {
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
