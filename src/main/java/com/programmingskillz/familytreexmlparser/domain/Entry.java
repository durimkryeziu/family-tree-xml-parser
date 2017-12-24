package com.programmingskillz.familytreexmlparser.domain;

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
}