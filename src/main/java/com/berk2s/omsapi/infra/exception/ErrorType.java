package com.berk2s.omsapi.infra.exception;

import lombok.Getter;

@Getter
public enum ErrorType {
    NOT_FOUND("not_found"),
    INVALID_REQUEST("invalid_request"),
    SERVER_ERROR("server_error");

    final String type;

    ErrorType(String type) {
        this.type = type;
    }
}