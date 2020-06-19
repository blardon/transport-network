package de.fhkiel.advancedjava.exception;

/**
 * StationNotFoundException is thrown when a station could not be found.
 *
 * @author Bennet v. Lardon
 */
public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(Long id) {
        super(String.format("Station with ID %d not found.", id));
    }

    public StationNotFoundException(String name) {
        super(String.format("Station with name %s not found.", name));
    }
}
