package com.programmingskillz.familytreexmlparser.business.exception;

/**
 * @author Durim Kryeziu
 */
public class MoreThanOneRootException extends RuntimeException {

  public MoreThanOneRootException() {
    super("There is more than one root");
  }
}