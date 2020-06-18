package de.fhkiel.advancedjava.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import de.fhkiel.advancedjava.model.schedule.StopType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StationNotFoundException.class)
    public ResponseEntity<Object> handleStationNotFoundException(StationNotFoundException ex, WebRequest request){
        return getExceptionResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StopNotFoundException.class)
    public ResponseEntity<Object> handleStopNotFoundException(StopNotFoundException ex, WebRequest request){
        return getExceptionResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LegNotFoundException.class)
    public ResponseEntity<Object> handleLegNotFoundException(LegNotFoundException ex, WebRequest request){
        return getExceptionResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongInputException.class)
    public ResponseEntity<Object> handleWrongInputException(WrongInputException ex, WebRequest request){
        return getExceptionResponseEntity(ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(LineNotFoundException.class)
    public ResponseEntity<Object> handleLineNotFoundException(LineNotFoundException ex, WebRequest request){
        return getExceptionResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<Object> handleVehicleNotFoundException(VehicleNotFoundException ex, WebRequest request){
        return getExceptionResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConnectionNotFoundException.class)
    public ResponseEntity<Object> handleConnectionNotFoundException(ConnectionNotFoundException ex, WebRequest request){
        return getExceptionResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> getExceptionResponseEntity(RuntimeException ex, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected  ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", "Could not read HTTP request input");

        Map<String, String> details = new LinkedHashMap<>();

        if (ex.getCause() instanceof InvalidFormatException){
            InvalidFormatException invalidFormatException = (InvalidFormatException) ex.getCause();
            if (invalidFormatException.getTargetType().equals(StopType.class)){
                String givenValue = invalidFormatException.getValue().toString();
                String acceptedValues = Arrays.toString(StopType.values());
                body.replace("message", "Given type is not accepted.");
                details.put("givenValue", givenValue);
                details.put("acceptedValues", acceptedValues);
            }
        }

        body.put("details", details);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
