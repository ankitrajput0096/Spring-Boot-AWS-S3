package com.aws.simplestorageservice.controller;

import com.aws.simplestorageservice.data.UploadFileRequestDto;
import com.aws.simplestorageservice.data.UploadFileResponseDto;
import com.aws.simplestorageservice.exceptions.S3ApplicationException;
import com.aws.simplestorageservice.exceptions.S3ApplicationRuntimeException;
import com.aws.simplestorageservice.model.UploadFileRequestBo;
import com.aws.simplestorageservice.model.UploadFileResponseBo;
import com.aws.simplestorageservice.service.AWSS3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/s3")
public class S3ApplicationController {

    @Autowired
    private AWSS3Service awss3Service;

    @PostMapping(path = "/upload/file",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UploadFileResponseDto> uploadFileInS3(
            final @Valid @RequestBody UploadFileRequestDto uploadFileRequestDto)
            throws S3ApplicationException, S3ApplicationRuntimeException {
        UploadFileResponseBo uploadFileResponseBo = this.awss3Service.uploadFileToS3Bucket(
                UploadFileRequestBo.builder()
                        .bucketName(uploadFileRequestDto.getBucketName())
                        .multipartFile(uploadFileRequestDto.getMultipartFile())
                        .build()
        );
        return new ResponseEntity<>(UploadFileResponseDto.builder()
                .message(uploadFileResponseBo.getMessage())
                .build(), HttpStatus.CREATED);
    }

}
