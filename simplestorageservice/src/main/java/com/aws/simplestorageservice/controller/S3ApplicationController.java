package com.aws.simplestorageservice.controller;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/s3")
public class S3ApplicationController {

    @Autowired
    private AWSS3Service awss3Service;

    @PostMapping(path = "/upload/file",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UploadFileResponseDto> uploadFileInS3(
            @Valid @RequestParam(value = "file") final MultipartFile fileToUpload,
            @Valid @RequestHeader(value = "bucket_name") final String bucketName)
            throws S3ApplicationException, S3ApplicationRuntimeException {

        log.info("received a request to upload file in s3 with bucket name={}", bucketName);

        UploadFileResponseBo uploadFileResponseBo = this.awss3Service.uploadFileToS3Bucket(
                UploadFileRequestBo.builder()
                        .bucketName(bucketName)
                        .multipartFile(fileToUpload)
                        .build()
        );
        return new ResponseEntity<>(UploadFileResponseDto.builder()
                .message(uploadFileResponseBo.getMessage())
                .build(), HttpStatus.CREATED);
    }

}
