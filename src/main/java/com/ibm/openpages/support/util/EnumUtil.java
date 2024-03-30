package com.ibm.openpages.support.util;

import java.util.Arrays;
import java.util.Optional;

public class EnumUtil {
    public static <T extends ResultValueAndLabel> Optional<T> lookup(SimpleMethodLogger logger, T[] values, String value) {
        logger.entering(new Object[]{value});

        logger.info("Looking up enum value: " + value);

        final Optional<T> result = Arrays.stream(values).sequential()
                .filter((T val) -> val.value().equalsIgnoreCase(value) || val.label().equalsIgnoreCase(value))
                .findFirst();

        if (result.isPresent()) {
            logger.info("Value found: " + result.get());
        } else {
            logger.info("Value not found");
        }

        return logger.exiting(result);
    }

    public static <T extends ResultValue> Optional<T> lookup(SimpleMethodLogger logger, T[] values, String value) {
        logger.entering(new Object[]{value});

        logger.info("Looking up enum value: " + value);

        final Optional<T> result = Arrays.stream(values).sequential()
                .filter((T val) -> val.value().equalsIgnoreCase(value))
                .findFirst();

        if (result.isPresent()) {
            logger.info("AIPrinciple found: " + result.get());
        } else {
            logger.info("AIPrinciple not found");
        }

        return logger.exiting(result);
    }
}
