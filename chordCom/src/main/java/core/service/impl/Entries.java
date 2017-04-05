package core.service.impl;

import core.data.Entry;
import core.data.ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by xinszhou on 28/03/2017.
 */
public class Entries {

    static Logger logger = LoggerFactory.getLogger(Entries.class);

    private Map<ID, Set<Entry>> entries = null;

    public Entries() {
        this.entries = Collections.synchronizedMap(new TreeMap<ID, Set<Entry>>());
    }

    public final void addAll(Set<Entry> entriesToAdd) {

        if (entriesToAdd == null) {
            NullPointerException e = new NullPointerException("Set of entries to be added to the local hash table may " + "not be null!");
            Entries.logger.error("Null pointer", e);
            throw e;
        }

        for (Entry nextEntry : entriesToAdd) {
            this.add(nextEntry);
        }

        Entries.logger.debug("Set of entries of length " + entriesToAdd.size() + " was added.");
    }

    public final void add(Entry entryToAdd) {

        if (entryToAdd == null) {
            NullPointerException e = new NullPointerException("Entry to add may not be null!");
            Entries.logger.error("Null pointer", e);
            throw e;
        }

        Set<Entry> values;
        synchronized (this.entries) {
            if (this.entries.containsKey(entryToAdd.getId())) {
                values = this.entries.get(entryToAdd.getId());
            } else {
                values = new HashSet<>();
                this.entries.put(entryToAdd.getId(), values);
            }
            values.add(entryToAdd);
        }
        Entries.logger.debug("Entry was added: " + entryToAdd);
    }

    public final void remove(Entry entryToRemove) {

        if (entryToRemove == null) {
            NullPointerException e = new NullPointerException("Entry to remove may not be null!");
            Entries.logger.error("Null pointer", e);
            throw e;
        }

        synchronized (this.entries) {
            if (this.entries.containsKey(entryToRemove.getId())) {
                Set<Entry> values = this.entries.get(entryToRemove.getId());
                values.remove(entryToRemove);
                if (values.size() == 0) {
                    this.entries.remove(entryToRemove.getId());
                }
            }
        }
        Entries.logger.debug("Entry was removed: " + entryToRemove);
    }

    public final Set<Entry> getEntries(ID id) {

        if (id == null) {
            NullPointerException e = new NullPointerException("ID to find entries for may not be null!");
            Entries.logger.error("Null pointer", e);
            throw e;
        }
        synchronized (this.entries) {
            /*
             * This has to be synchronized as the test if the map contains a set associated with id can succeed and then the thread may hand control over to
			 * another thread that removes the Set belonging to id. In that case this.entries.get(id) would return null which would break the contract of this
			 * method.
			 */
            if (this.entries.containsKey(id)) {
                Set<Entry> entriesForID = this.entries.get(id);
                /*
                 * Return a copy of the set to avoid modification of Set stored in this.entries from outside this class. (Avoids also modifications concurrent
				 * to iteration over the Set by a client of this class.
				 */
                Entries.logger.debug("Returning entries " + entriesForID);
                return new HashSet<Entry>(entriesForID);
            }
        }
        Entries.logger.debug("No entries available for " + id + ". Returning empty set.");
        return new HashSet<Entry>();
    }


    public final void removeAll(Set<Entry> toRemove) {

        if (toRemove == null) {
            NullPointerException e = new NullPointerException("Set of entries may not have value null!");
            Entries.logger.error("Null pointer", e);
            throw e;
        }

        for (Entry nextEntry : toRemove) {
            this.remove(nextEntry);
        }

        Entries.logger.debug("Set of entries of length " + toRemove.size() + " was removed.");
    }

    public final Map<ID, Set<Entry>> getEntries() {
        return Collections.unmodifiableMap(this.entries);
    }

    public final int getNumberOfStoredEntries() {
        return this.entries.size();
    }

    @Override
    public final String toString() {
        StringBuilder result = new StringBuilder("Entries:\n");
        for (Map.Entry<ID, Set<Entry>> entry : this.entries.entrySet()) {
            result.append("  key = " + entry.getKey().toString() + ", value = " + entry.getValue() + "\n");
        }
        return result.toString();
    }


}
