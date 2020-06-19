package de.fhkiel.advancedjava.exception;

/**
 * WrongInputException is thrown when the user input could not be processed.
 *
 * @author Bennet v. Lardon
 */
public class WrongInputException extends RuntimeException {
    public WrongInputException(String message) {
        super(message);
    }
}
