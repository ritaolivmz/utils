package com.nnip.exceptions;

public class DynamoDBManagerException extends Exception {
    public DynamoDBManagerException() { super(); }
    public DynamoDBManagerException(String message) { super(message); }
    public DynamoDBManagerException(String message, Throwable cause) { super(message, cause); }
    public DynamoDBManagerException(Throwable cause) { super(cause); }
}
