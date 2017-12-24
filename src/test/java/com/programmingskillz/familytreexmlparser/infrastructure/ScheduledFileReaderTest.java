package com.programmingskillz.familytreexmlparser.infrastructure;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import com.programmingskillz.familytreexmlparser.application.DocumentService;
import com.programmingskillz.familytreexmlparser.application.EntriesXmlParser;
import com.programmingskillz.familytreexmlparser.application.exception.RootIsMissingException;
import com.programmingskillz.familytreexmlparser.application.validator.PathValidator;
import com.programmingskillz.familytreexmlparser.domain.Entries;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ScheduledFileReader.class)
public class ScheduledFileReaderTest {

  @Mock
  private PathValidator validator;

  @Mock
  private EntriesXmlParser xmlParser;

  @Mock
  private DocumentService service;

  @Mock
  private Path path;

  @InjectMocks
  private ScheduledFileReader scheduledFileReader;

  private static final String INPUT_DIRECTORY = "/Users/durimkryeziu";

  @Before
  public void setUp() {
    ReflectionTestUtils.setField(scheduledFileReader, "inputDirectory", INPUT_DIRECTORY);

    mockStatic(Paths.class);

    when(Paths.get(INPUT_DIRECTORY)).thenReturn(path);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void insertXmlDocWhenAllConditionsMet() throws Exception {
    mockStatic(Files.class);

    when(validator.isValid(path)).thenReturn(true);

    DirectoryStream<Path> directoryStream = mock(DirectoryStream.class);
    when(Files.newDirectoryStream(path, "*.xml")).thenReturn(directoryStream);

    Iterator<Path> iterator = mock(Iterator.class);
    when(directoryStream.iterator()).thenReturn(iterator);

    when(iterator.hasNext()).thenReturn(true, false);
    Path file = mock(Path.class);
    when(iterator.next()).thenReturn(file);

    Entries entries = new Entries();
    when(xmlParser.parseXml(file)).thenReturn(Optional.of(entries));

    doNothing().when(service).insertDoc(entries);

    scheduledFileReader.parseXmlFiles();

    verify(service).insertDoc(entries);

    verifyStatic(Files.class);
    Files.newDirectoryStream(path, "*.xml");
  }

  @Test
  public void doNothingWhenPathIsInvalid() {
    when(validator.isValid(path)).thenReturn(false);

    scheduledFileReader.parseXmlFiles();

    verifyZeroInteractions(service);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void doNothingWhenPathCannotBeParsed() throws Exception {
    mockStatic(Files.class);

    when(validator.isValid(path)).thenReturn(true);

    DirectoryStream<Path> directoryStream = mock(DirectoryStream.class);
    when(Files.newDirectoryStream(path, "*.xml")).thenReturn(directoryStream);

    Iterator<Path> iterator = mock(Iterator.class);
    when(directoryStream.iterator()).thenReturn(iterator);

    when(iterator.hasNext()).thenReturn(true, false);
    Path file = mock(Path.class);
    when(iterator.next()).thenReturn(file);

    when(xmlParser.parseXml(file)).thenReturn(Optional.empty());

    scheduledFileReader.parseXmlFiles();

    verifyZeroInteractions(service);

    verifyStatic(Files.class);
    Files.newDirectoryStream(path, "*.xml");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void doNothingWhenXmlFileHasInvalidFormat() throws Exception {
    mockStatic(Files.class);

    when(validator.isValid(path)).thenReturn(true);

    DirectoryStream<Path> directoryStream = mock(DirectoryStream.class);
    when(Files.newDirectoryStream(path, "*.xml")).thenReturn(directoryStream);

    Iterator<Path> iterator = mock(Iterator.class);
    when(directoryStream.iterator()).thenReturn(iterator);

    when(iterator.hasNext()).thenReturn(true, false);
    Path file = mock(Path.class);
    when(iterator.next()).thenReturn(file);

    Entries entries = new Entries();

    when(xmlParser.parseXml(file)).thenReturn(Optional.of(entries));

    doThrow(RootIsMissingException.class).when(service).insertDoc(entries);

    scheduledFileReader.parseXmlFiles();

    verify(service).insertDoc(entries);

    verifyStatic(Files.class);
    Files.newDirectoryStream(path, "*.xml");
  }

  @After
  public void tearDown() {
    verifyStatic(Paths.class);
    Paths.get(INPUT_DIRECTORY);
  }
}