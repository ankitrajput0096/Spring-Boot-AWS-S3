package com.aws.simplestorageservice.controller;

import com.aws.simplestorageservice.data.*;
import com.aws.simplestorageservice.exceptions.S3ApplicationException;
import com.aws.simplestorageservice.exceptions.S3ApplicationRuntimeException;
import com.aws.simplestorageservice.model.*;
import com.aws.simplestorageservice.service.AWSS3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/s3")
public class S3ApplicationController {

    @Autowired
    private AWSS3Service awss3Service;

    @GetMapping(path = "/list/files",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FileMetaDataDto>> listOfFilesInS3(
            @Valid @RequestHeader(value = "bucket_name") final String bucketName)
            throws S3ApplicationException, S3ApplicationRuntimeException {
        log.info("received request to list all files in bucket={}", bucketName);
        List<FileMetaDataBo> filesMetaData = this.awss3Service.listAllFilesInS3(bucketName);
        return ResponseEntity.ok(filesMetaData.stream()
                .map(e -> FileMetaDataDto.builder()
                        .fileKey(e.getFileKey())
                        .fileOwner(e.getFileOwner())
                        .fileSize(e.getFileSize())
                        .bucketName(e.getBucketName())
                        .lastModifiedDate(e.getLastModifiedDate())
                        .build())
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "/file/metadata",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileMetaDataDto> getFileMetadata(
            @Valid @RequestHeader(value = "bucket_name") final String bucketName,
            @Valid @RequestHeader(value = "file_name") final String fileName)
            throws S3ApplicationException, S3ApplicationRuntimeException {
        log.info("received request to get metadata for file={} in bucket={}", fileName, bucketName);
        FileMetaDataBo fileMetaDataBo = this.awss3Service.getFileMeta(bucketName, fileName);
        return new ResponseEntity<>(FileMetaDataDto.builder()
                .bucketName(fileMetaDataBo.getBucketName())
                .fileKey(fileMetaDataBo.getFileKey())
                .fileSize(fileMetaDataBo.getFileSize())
                .lastModifiedDate(fileMetaDataBo.getLastModifiedDate())
                .fileOwner(fileMetaDataBo.getFileOwner())
                .build(), HttpStatus.FOUND);
    }

    @PostMapping(path = "/download/file",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> downloadFileFromS3(
            @Valid @RequestBody final FileDownloadRequestDto fileDownloadRequestDto)
            throws S3ApplicationException, S3ApplicationRuntimeException {
        log.info("received request to download file={} from bucket={}",
                fileDownloadRequestDto.getFileName(), fileDownloadRequestDto.getBucketName());
        FileDownloadResponseBo fileDownloadResponseBo = this.awss3Service
                .downloadFileFromS3(FileDownloadRequestBo.builder()
                        .bucketName(fileDownloadRequestDto.getBucketName())
                        .fileName(fileDownloadRequestDto.getFileName())
                        .build());
        return ResponseEntity.ok().contentType(MediaType
                .valueOf(fileDownloadResponseBo.getContentType()))
                .body(fileDownloadResponseBo.getBytes());
    }


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
                        .build());
        return new ResponseEntity<>(UploadFileResponseDto.builder()
                .message(uploadFileResponseBo.getMessage())
                .build(), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete/file",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeleteFileResponseDto> deleteFileInS3(
            @Valid @RequestBody final DeleteFileRequestDto deleteFileRequestDto)
            throws S3ApplicationException, S3ApplicationRuntimeException {
        log.info("received a request to delete a file in s3 with bucket name={} and filename={}",
                deleteFileRequestDto.getBucketName(), deleteFileRequestDto.getFileName());
        DeleteFileResponseBo deleteFileResponseBo = this.awss3Service
                .deleteFileInS3Bucket(DeleteFileRequestBo.builder()
                        .fileName(deleteFileRequestDto.getFileName())
                        .bucketName(deleteFileRequestDto.getBucketName())
                        .build());
        return ResponseEntity.ok(DeleteFileResponseDto.builder()
                .message(deleteFileResponseBo.getMessage())
                .build());
    }
}
