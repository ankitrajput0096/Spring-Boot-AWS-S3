package com.aws.simplestorageservice.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.aws.simplestorageservice.exceptions.S3ApplicationException;
import com.aws.simplestorageservice.exceptions.S3ApplicationRuntimeException;
import com.aws.simplestorageservice.model.DeleteFileRequestBo;
import com.aws.simplestorageservice.model.DeleteFileResponseBo;
import com.aws.simplestorageservice.model.UploadFileRequestBo;
import com.aws.simplestorageservice.model.UploadFileResponseBo;
import com.aws.simplestorageservice.service.AWSS3Service;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import static com.aws.simplestorageservice.constants.ApplicationErrorConstants.INTERNAL_SERVER_ERROR;
import static com.aws.simplestorageservice.constants.ApplicationErrorConstants.INTERNAL_SERVER_ERROR_RUNTIME;

@Slf4j
@Service
public class AWSS3ServiceImpl implements AWSS3Service {

    private AmazonS3 amazonS3;

    @Autowired
    public AWSS3ServiceImpl(
            @Qualifier("awss3client") final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /** ================> V V Remember <===================
     * Simple way to generate javadoc stubs as shown below in intellij:
     * Typing `/**` then pressing `Enter` above a method signature will create Javadoc stub for your method.
     */

    /**
     * This function is used to upload a multipart file in to S3 bucket.
     *
     * @param uploadFileRequestBo - {@link UploadFileRequestBo}
     * @return - {@link UploadFileResponseBo}
     * @throws S3ApplicationException
     * @throws S3ApplicationRuntimeException
     */
    @Async
    @Override
    public UploadFileResponseBo uploadFileToS3Bucket(UploadFileRequestBo uploadFileRequestBo)
            throws S3ApplicationException, S3ApplicationRuntimeException {
        try {
            /**
             *  ====================> V V Remember <==================
             *  Validations for checking arguments passed to method.
             */
            Preconditions.checkArgument(Objects.nonNull(uploadFileRequestBo.getBucketName()),
                    "S3 bucket name cannot be null");
            Preconditions.checkArgument(Objects.nonNull(uploadFileRequestBo.getMultipartFile()),
                    "S3 multipart file cannot be null");

            //creating the file in the server (temporarily)
            String fileName = uploadFileRequestBo.getMultipartFile().getOriginalFilename();

            log.info("Received file with name = {}", fileName);

            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(uploadFileRequestBo.getMultipartFile().getBytes());
            fos.close();

            log.info("Starting to upload file in S3");
            PutObjectResult putObjectResult = this.amazonS3.putObject(
                    new PutObjectRequest(uploadFileRequestBo.getBucketName(), fileName, file));
            log.info("File with name={} and version id={} is successfully uploaded in s3", fileName,
                    putObjectResult.getVersionId());
            file.delete();
            // removing the file created in server
            return UploadFileResponseBo.builder()
                    .message("File uploaded into S3 successfully with name = " +
                            uploadFileRequestBo.getMultipartFile().getOriginalFilename())
                    .build();
        } catch (IOException | AmazonServiceException ex) {
            log.error("An error occurred while uploading file={}",
                    uploadFileRequestBo.getMultipartFile().getOriginalFilename(), ex);
            throw new S3ApplicationRuntimeException(INTERNAL_SERVER_ERROR_RUNTIME.getCode(),
                    ex.getMessage());
        } catch (Exception ex) {
            log.error("An error occurred while uploading file={}",
                    uploadFileRequestBo.getMultipartFile().getOriginalFilename(), ex);
            throw new S3ApplicationException(INTERNAL_SERVER_ERROR.getCode(),
                    ex.getMessage());
        }
    }

    /**
     * This function is used to delete a file in s3 bucket
     *
     * @param deleteFileRequestBo - {@link DeleteFileRequestBo}
     * @return - {@link DeleteFileResponseBo}
     * @throws S3ApplicationException
     * @throws S3ApplicationRuntimeException
     */
    @Override
    public DeleteFileResponseBo deleteFileInS3Bucket(final DeleteFileRequestBo deleteFileRequestBo)
            throws S3ApplicationException, S3ApplicationRuntimeException {
        try {
            log.info("deleting file={} in bucket={}", deleteFileRequestBo.getFileName(), deleteFileRequestBo.getBucketName());
            this.amazonS3.deleteObject(new DeleteObjectRequest(deleteFileRequestBo.getBucketName(), deleteFileRequestBo.getFileName()));
            log.info("successfully deleted file={} in bucket={}", deleteFileRequestBo.getFileName(), deleteFileRequestBo.getBucketName());
            return DeleteFileResponseBo.builder()
                    .message("successfully delete file=" + deleteFileRequestBo.getFileName() + " in bucket={}" + deleteFileRequestBo.getBucketName())
                    .build();
        } catch (AmazonServiceException ex) {
            log.error("An error occurred while delete file={} in s3 bucket={}", deleteFileRequestBo.getFileName(), deleteFileRequestBo.getBucketName(), ex);
            throw new S3ApplicationRuntimeException(INTERNAL_SERVER_ERROR_RUNTIME.getCode(), INTERNAL_SERVER_ERROR_RUNTIME.getMessage());
        } catch (Exception ex) {
            log.error("An error occurred while delete file={} in s3 bucket={}", deleteFileRequestBo.getFileName(), deleteFileRequestBo.getBucketName(), ex);
            throw new S3ApplicationRuntimeException(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMessage());
        }
    }
}
