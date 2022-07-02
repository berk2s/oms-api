package com.berk2s.omsapi.domain.validation;

import com.berk2s.omsapi.domain.validation.exception.PassedNullValue;

import java.util.Arrays;

public final class NullValidator {

    public static void checkNonNull(Object ...args) {
        Arrays.stream(args).forEach(arg -> {
            if (arg == null) {
                throw new PassedNullValue("Given value can not be null");
            }
        });
    }
}
