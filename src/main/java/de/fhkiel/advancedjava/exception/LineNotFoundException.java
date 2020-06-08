package de.fhkiel.advancedjava.exception;

public class LineNotFoundException extends RuntimeException{
    public LineNotFoundException(String lineName){
        super(String.format("Line with name %s not found.", lineName));
    }
}
