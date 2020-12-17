package com.aws.simplestorageservice.exceptions.handler;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class S3ApplicationErrorSchema {
    private int code;
    private String message;
    private String date;
    private String requestUrl;
}
