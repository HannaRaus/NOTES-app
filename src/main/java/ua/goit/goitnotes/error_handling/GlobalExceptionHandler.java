package ua.goit.goitnotes.error_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException
            (NullPointerException e, WebRequest request) {
        String message = "Some of the request data is null";
        return getResponseEntity(request, message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException
            (ObjectNotFoundException e, WebRequest request) {
        return getResponseEntity(request, e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidAccessTypeException.class)
    public ResponseEntity<Object> handleInvalidAccessTypeException
            (InvalidAccessTypeException e, WebRequest request) {
        return getResponseEntity(request, e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExistException
            (UserAlreadyExistException e, WebRequest request) {
        return getResponseEntity(request, e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException
            (AccessDeniedException e, WebRequest request) {
        return getResponseEntity(request, e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleThrowable
            (Throwable e, WebRequest request) {
        return getResponseEntity(request, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> getResponseEntity(WebRequest request, String message, HttpStatus internalServerError) {
        ErrorDetails errorDetails = new ErrorDetails
                (new Date(), message, request.getDescription(false));
        return new ResponseEntity<Object>(errorDetails, internalServerError);
    }
}
