package com.programmingskillz.familytreexmlparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FamilyTreeXmlParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(FamilyTreeXmlParserApplication.class, args);
    }
}