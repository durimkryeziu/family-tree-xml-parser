package com.programmingskillz.familytreexmlparser.domain;

import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Entries {

  private List<Entry> entries;

  public List<Entry> getEntries() {
    return entries;
  }

  @XmlElement(name = "entry")
  public void setEntries(List<Entry> entries) {
    this.entries = entries;
  }

  public Entry getRoot() {
    return this.getEntries().stream()
        .filter(this::isParent)
        .findFirst()
        .orElse(null);
  }

  public List<Entry> getChildren() {
    return this.getEntries().stream()
        .filter(entry -> !isParent(entry))
        .collect(Collectors.toList());
  }

  private boolean isParent(Entry entry) {
    return entry.getParentName() == null;
  }
}