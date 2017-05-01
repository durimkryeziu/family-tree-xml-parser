package com.programmingskillz.familytreexmlparser.util;

import com.programmingskillz.familytreexmlparser.business.domain.Entries;
import com.programmingskillz.familytreexmlparser.business.domain.Entry;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Durim Kryeziu
 */
public class EntryUtilsTest {

    private Entries entries;
    private List<Entry> allEntries;

    @Before
    public void setUp() throws Exception {
        entries = new Entries();
        allEntries = new ArrayList<>();
    }

    @Test
    public void shouldReturnNullWhenNoRoot() throws Exception {
        Entry entry1 = new Entry();
        entry1.setParentName("Parent name");
        entry1.setValue("Entry name");

        entries.setEntries(Collections.singletonList(entry1));

        assertNull(EntryUtils.getRootEntry(entries));
    }

    @Test
    public void shouldReturnRootIfPresent() throws Exception {
        Entry root = new Entry();
        root.setValue("Root");

        Entry child = new Entry();
        child.setParentName(root.getValue());
        child.setValue("Child");

        allEntries.add(root);
        allEntries.add(child);

        entries.setEntries(allEntries);

        assertEquals(root, EntryUtils.getRootEntry(entries));
    }

    @Test
    public void shouldReturnFalseIfMoreThanOneRoot() throws Exception {
        Entry parent1 = new Entry();
        parent1.setValue("Parent 1");

        Entry parent2 = new Entry();
        parent2.setValue("Parent 2");

        allEntries.add(parent1);
        allEntries.add(parent2);

        entries.setEntries(allEntries);

        assertFalse(EntryUtils.isOneRoot(entries));
    }

    @Test
    public void shouldReturnChildren() throws Exception {
        Entry root = new Entry();
        root.setValue("Adam");

        Entry luka = new Entry();
        luka.setParentName(root.getValue());
        luka.setValue("Luka");

        Entry leopold = new Entry();
        leopold.setParentName(root.getValue());
        leopold.setValue("Leopold");

        allEntries.add(root);
        allEntries.add(luka);
        allEntries.add(leopold);

        entries.setEntries(allEntries);

        ArrayList<Entry> children = new ArrayList<>();
        children.add(luka);
        children.add(leopold);

        assertEquals(children, EntryUtils.getChildrenEntries(entries));
    }

    @Test
    public void shouldReturnEmptyListForNoChildren() throws Exception {
        Entry root = new Entry();
        root.setValue("Adam");

        allEntries.add(root);

        entries.setEntries(allEntries);

        assertTrue(EntryUtils.getChildrenEntries(entries).isEmpty());
    }
}