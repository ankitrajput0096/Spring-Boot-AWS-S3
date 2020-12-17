package com.aws.simplestorageservice.model;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FileMetaDataBo {
    private String bucketName;
    private String fileOwner;
    private String fileKey;
    private Date lastModifiedDate;
    private Long fileSize;
}
