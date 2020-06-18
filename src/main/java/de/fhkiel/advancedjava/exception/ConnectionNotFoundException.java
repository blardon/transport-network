package de.fhkiel.advancedjava.exception;

public class ConnectionNotFoundException extends RuntimeException{
    public ConnectionNotFoundException(String from, String to){
        super(String.format("No connection found from %s to %s", from, to));
    }
}
