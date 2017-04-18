package com.programmingskillz.familytreexmlparser.web;

import com.programmingskillz.familytreexmlparser.business.exception.MoreThanOneRootException;
import com.programmingskillz.familytreexmlparser.business.exception.RootNotFoundException;
import com.programmingskillz.familytreexmlparser.business.domain.Entries;
import com.programmingskillz.familytreexmlparser.business.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Durim Kryeziu
 * @since Mar 08, 2017.
 */
@RestController
public class DocumentController {

    private DocumentService service;

    @Autowired
    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @RequestMapping(
            value = "documents",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> addDoc(@RequestBody Entries entries) {

        if (entries == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("HTTP Request message body is missing");
        }

        try {
            service.insertDoc(entries);
        } catch (MoreThanOneRootException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is more than one root.");
        } catch (RootNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no root");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Document inserted successfully");
    }
}