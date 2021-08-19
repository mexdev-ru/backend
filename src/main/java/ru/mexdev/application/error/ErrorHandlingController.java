package ru.mexdev.application.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandlingController {

  @ResponseBody
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public List<Violation> onConstraintValidationException(ConstraintViolationException e) {
    return e.getConstraintViolations().stream()
        .map(violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
        .collect(Collectors.toList());
  }

  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public List<Violation> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    return e.getBindingResult().getFieldErrors().stream()
        .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
        .collect(Collectors.toList());
  }
}
