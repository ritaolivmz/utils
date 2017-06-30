package com.nnip.exceptions;

public class RDSConnectionManagerException extends Exception {
    public RDSConnectionManagerException() { super(); }
    public RDSConnectionManagerException(String message) { super(message); }
    public RDSConnectionManagerException(String message, Throwable cause) { super(message, cause); }
    public RDSConnectionManagerException(Throwable cause) { super(cause); }
}
