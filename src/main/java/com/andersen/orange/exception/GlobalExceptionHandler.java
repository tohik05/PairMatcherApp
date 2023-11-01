package com.andersen.orange.exception;

import com.andersen.orange.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundExceptionHandler(EntityNotFoundException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(TeamHasAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> TeamHasAlreadyExistHandler(TeamHasAlreadyExistException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(NoMorePairException.class)
    public ResponseEntity<ErrorResponse> NoMorePairHandler(NoMorePairException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(SameTeamException.class)
    public ResponseEntity<ErrorResponse> allUsersFromSameTeamHandler(SameTeamException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(RepeatCouplePerformanceException.class)
    public ResponseEntity<ErrorResponse> repeatPerfomanceHandler(RepeatCouplePerformanceException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(DateFormatException.class)
    public ResponseEntity<ErrorResponse> DateFormatExceptionHandler(DateFormatException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
