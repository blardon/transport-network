package de.fhkiel.advancedjava.exception;

public class ConnectionNotFoundException extends RuntimeException{
    public ConnectionNotFoundException(String from, String to){
        super(String.format("No connection found from %s to %s", from, to));
    }
    public ConnectionNotFoundException(Long n, Long m){
        super(String.format("No connection found that includes %d stations in %d minutes", n, m));
    }
}
