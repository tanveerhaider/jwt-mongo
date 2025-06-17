package com.simplejava.security.errorhandler;

import com.simplejava.security.exception.BadDataException;
import com.simplejava.security.jwt.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


/** Global exception handler for handling various exceptions in the application.
 * It provides a structured response for validation errors, bad data exceptions, and unexpected errors.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class); // Logger for logging errors

    /**
     * Handles validation exceptions thrown by the application.
     * It captures field errors and returns a structured ProblemDetail response.
     *
     * @param ex the MethodArgumentNotValidException containing validation errors
     * @return ResponseEntity with ProblemDetail containing validation error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Error");
        problemDetail.setDetail("One or more fields have invalid values.");

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage()));

        problemDetail.setProperty("fieldErrors", fieldErrors);
        LOG.error("handleValidationExceptions", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
    /**
     * Handles BadDataException thrown by the application.
     * It returns a structured ProblemDetail response with error details.
     *
     * @param ex the BadDataException containing error details
     * @return ResponseEntity with ProblemDetail containing error details
     */
    @ExceptionHandler(BadDataException.class)
    public ResponseEntity<ProblemDetail> handleInvalidData(BadDataException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid Data Error");
        problemDetail.setDetail(ex.getErrorMessage());
        problemDetail.setProperty("errorCode", ex.getErrorCode());
        LOG.error("handleInvalidData ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
    /**
     * Handles unexpected exceptions thrown by the application.
     * It returns a structured ProblemDetail response with error details.
     *
     * @param ex the Exception containing error details
     * @return ResponseEntity with ProblemDetail containing error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpectedException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Unexpected Error");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("errorCode", "INTERNAL_ERROR");
        LOG.error("handleUnexpectedException ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }
}
