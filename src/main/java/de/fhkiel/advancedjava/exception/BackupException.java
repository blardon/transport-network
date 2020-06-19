package de.fhkiel.advancedjava.exception;

/**
 * BackupException is thrown when an error occurred during the backup task.
 *
 * @author Bennet v. Lardon
 */
public class BackupException extends RuntimeException {
    public BackupException(String message, Throwable cause) {
        super(message, cause);
    }
}
