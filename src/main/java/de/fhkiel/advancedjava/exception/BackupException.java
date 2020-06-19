package de.fhkiel.advancedjava.exception;

public class BackupException extends RuntimeException{
    public BackupException(String message, Throwable cause){
        super(message, cause);
    }
}
