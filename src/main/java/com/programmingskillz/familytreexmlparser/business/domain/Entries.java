package com.programmingskillz.familytreexmlparser.business.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

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