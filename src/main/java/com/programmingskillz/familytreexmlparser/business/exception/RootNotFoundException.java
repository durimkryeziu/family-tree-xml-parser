package com.programmingskillz.familytreexmlparser.business.exception;

/**
 * @author Durim Kryeziu
 */
public class RootNotFoundException extends RuntimeException {

  public RootNotFoundException() {
    super("There is no root");
  }
}