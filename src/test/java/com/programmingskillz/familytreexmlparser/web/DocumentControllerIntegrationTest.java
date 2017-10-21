package com.programmingskillz.familytreexmlparser.web;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Durim Kryeziu
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  private HttpHeaders httpHeaders;

  private String url;

  @Before
  public void init() throws Exception {
    httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_XML);
    url = String.format("http://localhost:%d/documents", port);
  }

  @Test
  public void testInsertDoc() throws Exception {

    String xmlEntity = "<entries>" +
        "<entry>Adam</entry>" +
        "<entry parentName=\"Adam\">Stjepan</entry>" +
        "<entry parentName=\"Stjepan\">Luka</entry>" +
        "<entry parentName=\"Adam\">Leopold</entry>" +
        "</entries>";

    HttpEntity<String> httpEntity = new HttpEntity<>(xmlEntity, httpHeaders);

    ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Document inserted successfully", response.getBody());
  }

  @Test
  public void shouldReturn400NullBody() throws Exception {
    HttpEntity httpEntity = new HttpEntity(httpHeaders);

  }
}