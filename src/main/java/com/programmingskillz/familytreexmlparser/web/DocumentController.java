package com.programmingskillz.familytreexmlparser.web;

import com.programmingskillz.familytreexmlparser.business.domain.Entries;
import com.programmingskillz.familytreexmlparser.business.exception.MoreThanOneRootException;
import com.programmingskillz.familytreexmlparser.business.exception.RootNotFoundException;
import com.programmingskillz.familytreexmlparser.business.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Durim Kryeziu
 */
@RestController
public class DocumentController {

  private final DocumentService service;

  @Autowired
  public DocumentController(DocumentService service) {
    this.service = service;
  }

  @PostMapping(value = "documents", consumes = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> addDoc(@RequestBody Entries entries)
      throws MoreThanOneRootException, RootNotFoundException {

    service.insertDoc(entries);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.TEXT_PLAIN)
        .body("Document inserted successfully");
  }

  @ExceptionHandler(MoreThanOneRootException.class)
  public ResponseEntity<ErrorMessage> moreRoot(MoreThanOneRootException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(new ErrorMessage(e.getMessage()));
  }

  @ExceptionHandler(RootNotFoundException.class)
  public ResponseEntity<ErrorMessage> noRoot(RootNotFoundException e) {
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