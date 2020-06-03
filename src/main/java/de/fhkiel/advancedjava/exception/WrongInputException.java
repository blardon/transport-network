package de.fhkiel.advancedjava.exception;

public class WrongInputException extends RuntimeException{
    public WrongInputException(String message){
        super(message);
    }
}
