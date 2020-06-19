package de.fhkiel.advancedjava.exception;

/**
 * LegServiceException is thrown when an error occurred in the LegService.
 *
 * @author Bennet v. Lardon
 */
public class LegServiceException extends RuntimeException {
    public LegServiceException(String message) {
        super(message);
    }
}
