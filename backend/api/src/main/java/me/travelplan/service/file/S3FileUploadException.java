package me.travelplan.service.file;

public class S3FileUploadException extends RuntimeException{
    public S3FileUploadException() {
        super();
    }

    public S3FileUploadException(String message) {
        super(message);
    }
}
