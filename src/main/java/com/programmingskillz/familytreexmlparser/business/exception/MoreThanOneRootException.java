package com.programmingskillz.familytreexmlparser.business.exception;

/**
 * @author Durim Kryeziu
 */
public class MoreThanOneRootException extends Exception {

  public MoreThanOneRootException() {
    super("There is more than one root");
  }
}