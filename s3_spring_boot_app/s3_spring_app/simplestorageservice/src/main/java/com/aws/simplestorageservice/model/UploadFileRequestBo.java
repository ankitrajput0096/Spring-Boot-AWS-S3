package com.aws.simplestorageservice.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileRequestBo {

    private MultipartFile multipartFile;
    private String bucketName;
}
