package com.programmingskillz.familytreexmlparser.business.domain;

import java.util.Objects;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class Entry {

  private String parentName;
  private String value;

  public String getParentName() {
    return parentName;
  }

  @XmlAttribute
  public void setParentName(String parentName) {
    this.parentName = parentName;
  }

  public String getValue() {
    return value;
  }

  @XmlValue
  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Entry entry = (Entry) o;
    return Objects.equals(parentName, entry.parentName) &&
        Objects.equals(value, entry.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parentName, value);
  }
}