package com.programmingskillz.familytreexmlparser.application;

import com.programmingskillz.familytreexmlparser.domain.Entries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Optional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Component
public class EntriesXmlParser {

  private static final Logger log = LoggerFactory.getLogger(EntriesXmlParser.class);

  public Optional<Entries> parseXml(Path path) {
    try {
      JAXBContext context = JAXBContext.newInstance(Entries.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      return Optional.of((Entries) unmarshaller.unmarshal(path.toFile()));
    } catch (JAXBException e) {
      log.error("Error parsing the XML file", e);
    }
    return Optional.empty();
  }
}
