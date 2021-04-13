package me.travelplan.service.file;

import me.travelplan.exception.BusinessException;

public class S3FileUploadException extends BusinessException {

    public S3FileUploadException(String message) {
        super(message);
    }
}
