package com.programmingskillz.familytreexmlparser.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import com.programmingskillz.familytreexmlparser.domain.Entries;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JAXBContext.class)
public class EntriesXmlParserTest {

  @Mock
  private JAXBContext jaxbContext;

  @Mock
  private Unmarshaller unmarshaller;

  @Mock
  private Path path;

  private EntriesXmlParser xmlParser;

  @Before
  public void setUp() throws JAXBException {
    this.xmlParser = new EntriesXmlParser();

    mockStatic(JAXBContext.class);

    when(JAXBContext.newInstance(Entries.class)).thenReturn(jaxbContext);
    when(jaxbContext.createUnmarshaller()).thenReturn(unmarshaller);
  }

  @Test
  public void shouldReturnEntriesWhenXmlIsValid() throws JAXBException {
    when(unmarshaller.unmarshal(any(File.class))).thenReturn(new Entries());

    Optional<Entries> optionalEntries = xmlParser.parseXml(path);

    assertThat(optionalEntries.isPresent()).isTrue();
    optionalEntries.ifPresent(
        e -> assertThat(optionalEntries.get()).isEqualToComparingFieldByField(e)
    );
  }

  @Test
  public void shouldReturnEmptyWhenXmlIsInvalid() throws JAXBException {
    doThrow(new JAXBException("")).when(unmarshaller).unmarshal(any(File.class));

    Optional<Entries> optionalEntries = xmlParser.parseXml(path);

    assertThat(optionalEntries.isPresent()).isFalse();
  }

  @After
  public void tearDown() throws JAXBException {
    verifyStatic(JAXBContext.class);
    JAXBContext.newInstance(Entries.class);
  }
}