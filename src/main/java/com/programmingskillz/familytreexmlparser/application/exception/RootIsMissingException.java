package com.programmingskillz.familytreexmlparser.application.exception;

public class RootIsMissingException extends RuntimeException {

  public RootIsMissingException() {
    super("Root entry is missing");
  }
}