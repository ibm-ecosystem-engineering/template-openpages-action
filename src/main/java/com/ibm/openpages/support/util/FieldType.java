package com.ibm.openpages.support.util;

import com.ibm.openpages.api.resource.*;

public class FieldType<T> {
    public static final FieldType<IIdField> User = new FieldType<IIdField>(IIdField.class);
    public static final FieldType<IIntegerField> Integer = new FieldType<IIntegerField>(IIntegerField.class);
    public static final FieldType<IFloatField> Decimal = new FieldType<IFloatField>(IFloatField.class);
    public static final FieldType<IDateField> Date = new FieldType<IDateField>(IDateField.class);
    public static final FieldType<IStringField> String = new FieldType<IStringField>(IStringField.class);
    public static final FieldType<IEnumField> Enum = new FieldType<IEnumField>(IEnumField.class);

    private final Class<T> type;
    private FieldType(Class<T> type) {
        this.type = type;
    }

    public Class<T> type() {
        return type;
    }

    public boolean isEnumField() {
        return FieldType.Enum.equals(this);
    }
    public boolean isStringField() {
        return FieldType.String.equals(this);
    }
    public boolean isIntegerField() {
        return FieldType.Integer.equals(this);
    }
    public boolean isDecimalField() {
        return FieldType.Decimal.equals(this);
    }
    public boolean isDateField() {
        return FieldType.Date.equals(this);
    }
    public boolean isUserField() {
        return FieldType.User.equals(this);
    }

}
