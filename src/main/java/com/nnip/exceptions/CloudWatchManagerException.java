package com.nnip.exceptions;

public class CloudWatchManagerException extends Exception {
    public CloudWatchManagerException() { super(); }
    public CloudWatchManagerException(String message) { super(message); }
    public CloudWatchManagerException(String message, Throwable cause) { super(message, cause); }
    public CloudWatchManagerException(Throwable cause) { super(cause); }
}
