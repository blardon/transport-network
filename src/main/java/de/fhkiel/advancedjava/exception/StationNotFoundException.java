package de.fhkiel.advancedjava.exception;

public class StationNotFoundException extends RuntimeException{
    public StationNotFoundException(Long id){
        super(String.format("Station with ID %d not found.", id));
    }

    public StationNotFoundException(String name){
        super(String.format("Station with name %s not found.", name));
    }
}
