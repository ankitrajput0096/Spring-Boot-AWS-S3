package com.aws.simplestorageservice.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DeleteFileRequestBo {
    private String fileName;
    private String bucketName;
}
