package com.programmingskillz.familytreexmlparser.business.exception;

public class RootIsMissingException extends RuntimeException {

  public RootIsMissingException() {
    super("Root entry is missing");
  }
}