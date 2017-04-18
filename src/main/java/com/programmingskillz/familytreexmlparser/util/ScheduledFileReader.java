package com.programmingskillz.familytreexmlparser.util;

import com.programmingskillz.familytreexmlparser.business.exception.MoreThanOneRootException;
import com.programmingskillz.familytreexmlparser.business.exception.RootNotFoundException;
import com.programmingskillz.familytreexmlparser.business.domain.Entries;
import com.programmingskillz.familytreexmlparser.business.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Durim Kryeziu
 * @since Mar 08, 2017.
 */
@Component
public class ScheduledFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledFileReader.class);

    @Value("${params.input-directory:default-dir}")
    private String inputDir;
    private DocumentService service;

    @Autowired
    public ScheduledFileReader(DocumentService service) {
        this.service = service;
    }

    @Scheduled(fixedDelayString = "${params.processing-interval:30000}")
    public void parseXmlFiles() {

        Path path = Paths.get(inputDir);

        if (!Files.exists(path)) {
            LOGGER.warn("Directory does not exist: {}", path);
            return;
        }

        if (!Files.isDirectory(path)) {
            LOGGER.warn("Given path is not a directory: {}", path);
            return;
        }

        LOGGER.debug("Checking directory...");
        try (DirectoryStream<Path> stream = getDirectory(path)) {
            for (Path file : stream) {
                Entries entries = parseXml(file);
                if (entries != null) {
                    service.insertDoc(entries);
                }
            }
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        } catch (RootNotFoundException e) {
            LOGGER.error("There is no root", e);
        } catch (MoreThanOneRootException e) {
            LOGGER.error("There is more than one root.", e);
        }
    }

    public Entries parseXml(Path path) {
        Entries entries = null;
        try {
            JAXBContext context = JAXBContext.newInstance(Entries.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            entries = (Entries) unmarshaller.unmarshal(path.toFile());
        } catch (JAXBException e) {
            LOGGER.error("Error parsing the XML file", e);
        }
        return entries;
    }

    public DirectoryStream<Path> getDirectory(Path path) throws IOException {
        return Files.newDirectoryStream(path, "*.xml");
    }
}