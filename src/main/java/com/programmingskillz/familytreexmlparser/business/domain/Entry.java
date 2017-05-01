package com.programmingskillz.familytreexmlparser.business.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author Durim Kryeziu
 */
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (parentName != null ? !parentName.equals(entry.parentName) : entry.parentName != null) return false;
        return value != null ? value.equals(entry.value) : entry.value == null;
    }

    @Override
    public int hashCode() {
        int result = parentName != null ? parentName.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}