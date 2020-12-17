package com.aws.simplestorageservice.service;

import com.aws.simplestorageservice.exceptions.S3ApplicationException;
import com.aws.simplestorageservice.exceptions.S3ApplicationRuntimeException;
import com.aws.simplestorageservice.model.*;

import java.util.List;

public interface AWSS3Service {

    UploadFileResponseBo uploadFileToS3Bucket(final UploadFileRequestBo uploadFileRequestBo)
            throws S3ApplicationException, S3ApplicationRuntimeException;

    DeleteFileResponseBo deleteFileInS3Bucket(final DeleteFileRequestBo deleteFileRequestBo)
            throws S3ApplicationException, S3ApplicationRuntimeException;

    List<FileMetaDataBo> listAllFilesInS3(final String bucketName)
            throws S3ApplicationException, S3ApplicationRuntimeException;

    FileDownloadResponseBo downloadFileFromS3(final FileDownloadRequestBo fileDownloadRequestBo)
            throws S3ApplicationException, S3ApplicationRuntimeException;

    FileMetaDataBo getFileMeta(final String bucketName, final String fileName)
            throws S3ApplicationException, S3ApplicationRuntimeException;
}
