package com.programmingskillz.familytreexmlparser.util;

import com.programmingskillz.familytreexmlparser.business.domain.Entries;
import com.programmingskillz.familytreexmlparser.business.exception.MoreThanOneRootException;
import com.programmingskillz.familytreexmlparser.business.exception.RootIsMissingException;
import org.springframework.stereotype.Component;

@Component
public class EntriesValidator {

  public void validate(Entries entries) {
    if (entries.getRoot() == null) {
      throw new RootIsMissingException();
    }

    if (moreThanOneRoot(entries)) {
      throw new MoreThanOneRootException();
    }
  }

  private boolean moreThanOneRoot(Entries entries) {
    long count = entries.getEntries().stream()
        .filter(entry -> entry.getParentName() == null)
        .count();
    return count > 1;
  }
}