package com.programmingskillz.familytreexmlparser.infrastructure;

import com.programmingskillz.familytreexmlparser.application.DocumentService;
import com.programmingskillz.familytreexmlparser.application.EntriesXmlParser;
import com.programmingskillz.familytreexmlparser.application.validator.PathValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ScheduledFileReader {

  private static final Logger log = LoggerFactory.getLogger(ScheduledFileReader.class);

  @Value("${checking-parameters.input-directory}")
  private String inputDirectory;

  private final PathValidator pathValidator;
  private final EntriesXmlParser xmlParser;
  private final DocumentService service;

  @Autowired
  public ScheduledFileReader(PathValidator pathValidator, EntriesXmlParser xmlParser,
                             DocumentService service) {
    this.pathValidator = pathValidator;
    this.xmlParser = xmlParser;
    this.service = service;
  }

  @Scheduled(fixedDelayString = "${checking-parameters.processing-interval}")
  public void parseXmlFiles() {
    Path path = Paths.get(inputDirectory);

    if (pathValidator.isValid(path)) {
      log.debug("Checking directory: {}", path);
      try (DirectoryStream<Path> stream = getDirectory(path)) {
        for (Path file : stream) {
          xmlParser.parseXml(file).ifPresent(service::insertDoc);
        }
      } catch (Exception e) {
        log.error("Error occurred while checking directory", e);
      }
    }
  }

  private DirectoryStream<Path> getDirectory(Path path) throws IOException {
    return Files.newDirectoryStream(path, "*.xml");
  }
}