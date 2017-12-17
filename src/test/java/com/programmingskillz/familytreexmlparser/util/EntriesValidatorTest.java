package com.programmingskillz.familytreexmlparser.util;

import com.programmingskillz.familytreexmlparser.business.domain.Entries;
import com.programmingskillz.familytreexmlparser.business.domain.Entry;
import com.programmingskillz.familytreexmlparser.business.exception.MoreThanOneRootException;
import com.programmingskillz.familytreexmlparser.business.exception.RootIsMissingException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class EntriesValidatorTest {

  private Entries entries;
  private EntriesValidator validator;

  @Before
  public void setUp() {
    this.entries = new Entries();
    this.validator = new EntriesValidator();
  }

  @Test
  public void doNothingWhenValid() {
    Entry durim = new Entry();
    durim.setValue("Durim");

    Entry nejla = new Entry();
    nejla.setParentName("Durim");
    nejla.setValue("Neyla");

    entries.setEntries(Arrays.asList(durim, nejla));

    validator.validate(entries);
  }

  @Test(expected = RootIsMissingException.class)
  public void shouldThrowRootIsMissingExceptionWhenNoRoot() {
    Entry entry = new Entry();
    entry.setParentName("Durim");
    entry.setValue("Neyla");

    entries.setEntries(Collections.singletonList(entry));

    validator.validate(entries);
  }

  @Test(expected = MoreThanOneRootException.class)
  public void shouldThrowMoreThanOneRootExceptionWhenTwoRoots() {
    Entry durim = new Entry();
    durim.setValue("Durim");

    Entry nejla = new Entry();
    nejla.setValue("Neyla");

    entries.setEntries(Arrays.asList(durim, nejla));

    validator.validate(entries);
  }
}