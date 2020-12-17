package com.aws.simplestorageservice.model;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadRequestBo {
    private String bucketName;
    private String fileName;
}
