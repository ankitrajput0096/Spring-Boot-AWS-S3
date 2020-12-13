package com.aws.simplestorageservice.service;

import com.aws.simplestorageservice.exceptions.S3ApplicationException;
import com.aws.simplestorageservice.exceptions.S3ApplicationRuntimeException;
import com.aws.simplestorageservice.model.UploadFileRequestBo;
import com.aws.simplestorageservice.model.UploadFileResponseBo;

public interface AWSS3Service {

    UploadFileResponseBo uploadFileToS3Bucket(final UploadFileRequestBo uploadFileRequestBo)
            throws S3ApplicationException, S3ApplicationRuntimeException;
}
