package com.programmingskillz.familytreexmlparser.application.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.nio.file.Path;

@RunWith(MockitoJUnitRunner.class)
public class PathValidatorTest {

  @Mock
  private Path path;

  @Mock
  private File file;

  private PathValidator pathValidator;

  @Before
  public void setUp() {
    when(path.toFile()).thenReturn(file);
    this.pathValidator = new PathValidator();
  }

  @Test
  public void shouldReturnTrueWhenPathIsValid() {
    when(file.exists()).thenReturn(true);
    when(file.isDirectory()).thenReturn(true);

    assertThat(pathValidator.isValid(path)).isTrue();
  }

  @Test
  public void shouldReturnFalseWhenDirectoryDoesNotExists() {
    when(file.exists()).thenReturn(false);

    assertThat(pathValidator.isValid(path)).isFalse();
  }

  @Test
  public void shouldReturnFalseWhenPathIsNotDirectory() {
    when(file.exists()).thenReturn(true);
    when(file.isDirectory()).thenReturn(false);

    assertThat(pathValidator.isValid(path)).isFalse();
  }
}