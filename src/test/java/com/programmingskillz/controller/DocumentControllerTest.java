package com.programmingskillz.controller;

import com.programmingskillz.controller.DocumentController;
import com.programmingskillz.exceptions.MoreThanOneRootException;
import com.programmingskillz.exceptions.RootNotFoundException;
import com.programmingskillz.model.Entries;
import com.programmingskillz.model.Entry;
import com.programmingskillz.service.DocumentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

/**
 * @author Durim Kryeziu
 * @since Mar 10, 2017.
 */
public class DocumentControllerTest {

    @InjectMocks
    private DocumentController controller;

    @Mock
    private DocumentService service;

    private Entries entries;
    private List<Entry> allEntries;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        entries = new Entries();
        allEntries = new ArrayList<>();
    }

    @Test
    public void shouldReturn400IfBodyIsNull() throws Exception {
        ResponseEntity<String> nullBody = controller.addDoc(null);

        assertEquals(400, nullBody.getStatusCodeValue());
        assertEquals("HTTP Request message body is missing", nullBody.getBody());
    }

    @Test
    public void shouldReturn400IfNoRoot() throws Exception {
        Entry child = new Entry();
        child.setParentName("Parent");
        child.setValue("Child");

        entries.setEntries(Collections.singletonList(child));

        doThrow(RootNotFoundException.class).when(service).insertDoc(entries);

        ResponseEntity<String> responseEntity = controller.addDoc(entries);

        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("There is no root", responseEntity.getBody());
    }

    @Test
    public void shouldReturn400IfMoreThanOneRoot() throws Exception {
        Entry adam = new Entry();
        adam.setValue("Adam");

        Entry durim = new Entry();
        durim.setValue("Durim");

        Entry luka = new Entry();
        luka.setParentName(adam.getValue());
        luka.setValue("Luka");

        allEntries.add(adam);
        allEntries.add(durim);
        allEntries.add(luka);

        entries.setEntries(allEntries);

        doThrow(MoreThanOneRootException.class).when(service).insertDoc(entries);

        ResponseEntity<String> responseEntity = controller.addDoc(entries);

        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("There is more than one root.", responseEntity.getBody());
    }

    @Test
    public void shouldReturnOkWhenEntriesCorrect() throws Exception {
        Entry adam = new Entry();
        adam.setValue("Adam");

        Entry luka = new Entry();
        luka.setParentName(adam.getValue());
        luka.setValue("Luka");

        Entry leopold = new Entry();
        leopold.setParentName(adam.getValue());
        leopold.setValue("Leopold");

        allEntries.add(adam);
        allEntries.add(luka);
        allEntries.add(leopold);

        entries.setEntries(allEntries);

        doNothing().when(service).insertDoc(entries);

        ResponseEntity<String> responseEntity = controller.addDoc(entries);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}