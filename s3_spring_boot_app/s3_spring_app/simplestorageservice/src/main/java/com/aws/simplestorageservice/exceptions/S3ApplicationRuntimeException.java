package com.aws.simplestorageservice.exceptions;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class S3ApplicationRuntimeException extends RuntimeException {
    private int status;
    private String message;
}
