package com.berk2s.omsapi.domain.validation;

import com.berk2s.omsapi.domain.validation.exception.PassedNullValue;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public final class NullValidator {

    public static void checkNonNull(Object ...args) {
        Arrays.stream(args).forEach(arg -> {
            if (arg == null) {
                log.warn("Given argument can not be null");
                throw new PassedNullValue("field.null");
            }
        });
    }
}
