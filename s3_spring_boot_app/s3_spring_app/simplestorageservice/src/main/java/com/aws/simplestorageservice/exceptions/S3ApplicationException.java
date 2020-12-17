package com.aws.simplestorageservice.exceptions;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class S3ApplicationException extends Exception {
    private int code;
    private String status;
}
