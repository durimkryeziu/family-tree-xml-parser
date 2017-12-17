package com.programmingskillz.familytreexmlparser.web;

import com.programmingskillz.familytreexmlparser.business.domain.Entries;
import com.programmingskillz.familytreexmlparser.business.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController {

  private final DocumentService service;

  @Autowired
  public DocumentController(DocumentService service) {
    this.service = service;
  }

  @PostMapping(value = "documents", consumes = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<Response> addDoc(@RequestBody Entries entries) {

    service.insertDoc(entries);

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(new Response("Document inserted successfully"));
  }

  private static class Response {
    private final String message;

    Response(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }
  }
}