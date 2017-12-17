package com.programmingskillz.familytreexmlparser.application.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class PathValidator {

  private static final Logger log = LoggerFactory.getLogger(PathValidator.class);

  public boolean isValid(Path path) {
    if (!path.toFile().exists()) {
      log.warn("Directory does not exist: {}", path);
      return false;
    }
    if (!path.toFile().isDirectory()) {
      log.warn("Given path is not a directory: {}", path);
      return false;
    }
    return true;
  }
}
