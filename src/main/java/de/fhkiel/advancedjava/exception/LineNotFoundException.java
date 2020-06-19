package de.fhkiel.advancedjava.exception;

/**
 * LineNotFoundException is thrown when a line could not be found.
 *
 * @author Bennet v. Lardon
 */
public class LineNotFoundException extends RuntimeException {
    public LineNotFoundException(String lineName) {
        super(String.format("Line with name %s not found.", lineName));
    }
}
