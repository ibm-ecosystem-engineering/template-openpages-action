package com.ibm.openpages.support.util;

public interface FieldMetadata extends ResultValue {
    boolean isEnumField();
    boolean isStringField();
    boolean isIntegerField();
    boolean isDecimalField();
    boolean isDateField();
    boolean isUserField();
    FieldType<?> fieldType();
}
