package com.programmingskillz.familytreexmlparser.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentControllerIntegrationTest {

  private static final String URL = "/documents";

  private HttpHeaders httpHeaders;

  @Autowired
  private TestRestTemplate restTemplate;

  @Before
  public void init() {
    httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_XML);
  }

  @Test
  public void shouldReturnCreatedWhenValidRequest() {
    String xmlEntity = "<entries>" +
        "<entry>Adam</entry>" +
        "<entry parentName=\"Adam\">Stjepan</entry>" +
        "<entry parentName=\"Stjepan\">Luka</entry>" +
        "<entry parentName=\"Adam\">Leopold</entry>" +
        "</entries>";

    HttpEntity<String> httpEntity = new HttpEntity<>(xmlEntity, httpHeaders);

    ResponseEntity<String> response = restTemplate.postForEntity(URL, httpEntity, String.class);

    assertThat(response.getStatusCodeValue()).isEqualTo(201);
    assertThat(response.getBody()).contains("Document inserted successfully");
  }

  @Test
  public void shouldReturnBadRequestWhenNoRoot() {
    String xmlEntity = "<entries>" +
        "<entry parentName=\"Adam\">Stjepan</entry>" +
        "<entry parentName=\"Stjepan\">Luka</entry>" +
        "<entry parentName=\"Adam\">Leopold</entry>" +
        "</entries>";

    HttpEntity<String> httpEntity = new HttpEntity<>(xmlEntity, httpHeaders);

    ResponseEntity<String> response = restTemplate.postForEntity(URL, httpEntity, String.class);

    assertThat(response.getStatusCodeValue()).isEqualTo(400);
    assertThat(response.getBody()).contains("Root entry is missing");
  }

  @Test
  public void shouldReturnBadRequestWhenMoreThanOneRoot() {
    String xmlEntity = "<entries>" +
        "<entry>Adam</entry>" +
        "<entry>Noa</entry>" +
        "<entry parentName=\"Adam\">Stjepan</entry>" +
        "<entry parentName=\"Stjepan\">Luka</entry>" +
        "<entry parentName=\"Adam\">Leopold</entry>" +
        "</entries>";

    HttpEntity<String> httpEntity = new HttpEntity<>(xmlEntity, httpHeaders);

    ResponseEntity<String> response = restTemplate.postForEntity(URL, httpEntity, String.class);

    assertThat(response.getStatusCodeValue()).isEqualTo(400);
    assertThat(response.getBody()).contains("Only one root entry is allowed");
  }
}