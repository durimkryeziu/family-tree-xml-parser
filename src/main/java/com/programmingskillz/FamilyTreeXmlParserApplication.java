package com.programmingskillz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FamilyTreeXmlParserApplication {

  public static void main(String[] args) {
    SpringApplication.run(FamilyTreeXmlParserApplication.class, args);
  }
}