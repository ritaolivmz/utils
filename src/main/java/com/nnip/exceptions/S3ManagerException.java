package com.nnip.exceptions;

public class S3ManagerException extends Exception {
    public S3ManagerException() { super(); }
    public S3ManagerException(String message) { super(message); }
    public S3ManagerException(String message, Throwable cause) { super(message, cause); }
    public S3ManagerException(Throwable cause) { super(cause); }
}
