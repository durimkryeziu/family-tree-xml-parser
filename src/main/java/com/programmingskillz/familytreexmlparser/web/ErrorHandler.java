package com.programmingskillz.familytreexmlparser.web;

import com.programmingskillz.familytreexmlparser.business.exception.MoreThanOneRootException;
import com.programmingskillz.familytreexmlparser.business.exception.RootIsMissingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(MoreThanOneRootException.class)
  public ResponseEntity<ErrorMessage> moreRoots(MoreThanOneRootException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(new ErrorMessage(e.getMessage()));
  }

  @ExceptionHandler(RootIsMissingException.class)
  public ResponseEntity<ErrorMessage> noRoot(RootIsMissingException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(new ErrorMessage(e.getMessage()));
  }

  private static final class ErrorMessage {
    private final String message;

    ErrorMessage(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }
  }
}
