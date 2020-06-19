package de.fhkiel.advancedjava.exception;

/**
 * StationServiceException is thrown when an error occurred in the StationService.
 *
 * @author Bennet v. Lardon
 */
public class StationServiceException extends RuntimeException {
    public StationServiceException(String message) {
        super(message);
    }
}
