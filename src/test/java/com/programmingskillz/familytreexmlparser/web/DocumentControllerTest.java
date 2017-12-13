package com.programmingskillz.familytreexmlparser.web;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.programmingskillz.familytreexmlparser.business.domain.Entries;
import com.programmingskillz.familytreexmlparser.business.domain.Entry;
import com.programmingskillz.familytreexmlparser.business.exception.MoreThanOneRootException;
import com.programmingskillz.familytreexmlparser.business.exception.RootNotFoundException;
import com.programmingskillz.familytreexmlparser.business.service.DocumentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @author Durim Kryeziu
 */
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DocumentControllerTest {

  @Mock
  private DocumentService service;

  @Autowired
  private MockMvc mockMvc;

  private Entries entries;
  private List<Entry> allEntries;

  @Before
  public void setUp() throws Exception {
    this.entries = new Entries();
    this.allEntries = new ArrayList<>();
  }

  @Test
  public void shouldReturn400IfNoRoot() throws Exception {
    Entry child = new Entry();
    child.setParentName("Parent");
    child.setValue("Child");

    entries.setEntries(Collections.singletonList(child));

    doThrow(RootNotFoundException.class).when(service).insertDoc(entries);

    this.mockMvc
        .perform(
            post("/documents")
                .contentType(MediaType.APPLICATION_XML)
                .content(getRequestBody(entries))
        )
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.message").value("There is no root"));
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

    this.mockMvc
        .perform(
            post("/documents")
                .contentType(MediaType.APPLICATION_XML)
                .content(getRequestBody(entries))
        )
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.message").value("There is more than one root"));
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

    this.mockMvc
        .perform(
            post("/documents")
                .contentType(MediaType.APPLICATION_XML)
                .content(getRequestBody(entries))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.TEXT_PLAIN))
        .andExpect(content().string("Document inserted successfully"));
  }

  private String getRequestBody(Entries entries) throws JAXBException {
    JAXBContext context = JAXBContext.newInstance(Entries.class);
    Marshaller marshaller = context.createMarshaller();
    StringWriter stringWriter = new StringWriter();
    marshaller.marshal(entries, stringWriter);
    return stringWriter.toString();
  }
}