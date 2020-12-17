package com.aws.simplestorageservice.model;

import lombok.*;

@Setter
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadResponseBo {
    private byte[] bytes;
    private String contentType;
}
