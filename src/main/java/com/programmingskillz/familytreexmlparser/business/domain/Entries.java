package com.programmingskillz.familytreexmlparser.business.domain;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Durim Kryeziu
 */
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
}