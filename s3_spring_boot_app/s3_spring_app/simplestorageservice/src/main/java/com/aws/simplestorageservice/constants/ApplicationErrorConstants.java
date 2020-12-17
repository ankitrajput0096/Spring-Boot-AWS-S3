package com.aws.simplestorageservice.constants;

import lombok.Getter;

@Getter
public enum ApplicationErrorConstants {

    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR IN APPLICATION"),
    INTERNAL_SERVER_ERROR_RUNTIME(500, "INTERNAL_SERVER_ERROR AT RUNTIME");

    private int code;
    private String message;

    ApplicationErrorConstants(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
