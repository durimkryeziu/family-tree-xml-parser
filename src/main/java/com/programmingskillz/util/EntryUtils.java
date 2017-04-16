package com.programmingskillz.util;

import com.programmingskillz.model.Entries;
import com.programmingskillz.model.Entry;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Durim Kryeziu
 * @since Mar 08, 2017.
 */
public class EntryUtils {

    public static Entry getRootEntry(Entries entries) {
        Optional<Entry> first = entries.getEntries()
                .stream()
                .filter(e -> e.getParentName() == null)
                .findFirst();

        return first.orElse(null);
    }

    public static List<Entry> getChildrenEntries(Entries entries) {
        return entries.getEntries()
                .stream()
                .filter(e -> e.getParentName() != null)
                .collect(Collectors.toList());
    }

    public static boolean isOneRoot(Entries entries) {

        long count = entries.getEntries()
                .stream()
                .filter(e -> e.getParentName() == null)
                .count();

        return count == 1;
    }
}