package core.service.impl;

import core.com.CommunicationException;
import core.com.Node;
import core.data.ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xinszhou on 28/03/2017.
 */
public class SuccessorList {
    private List<Node> successors;
    private ID localID;
    private int capacity;
    private References references;
    // why successor need entries at all
    private Entries entries;
    private Logger logger;

    SuccessorList(ID localID, int numberOfEntries, References parent, Entries entries) {
        this.logger = LoggerFactory.getLogger(SuccessorList.class + "." + localID);
        this.logger.debug("Logger initialized.");

        if (localID == null || parent == null || entries == null) {
            NullPointerException e = new NullPointerException("Neither paremeter of this constructor may have value " + "null!");
            this.logger.error("Null pointer", e);
            throw e;
        }
        if (numberOfEntries < 1) {
            throw new IllegalArgumentException("SuccessorList has to be at least of length 1! " + numberOfEntries + "is not a valid value!");
        }

        this.localID = localID;
        this.capacity = numberOfEntries;
        this.successors = new LinkedList<>();
        this.references = parent;
        this.entries = entries;
    }

    final int getCapacity() {
        return capacity;
    }

    final int getSize() {
        return this.successors.size();
    }

    final Node getDirectSuccessor() {
        if (this.successors.size() == 0) {
            return null;
        }
        return this.successors.get(0);
    }

    final boolean containsReference(Node nodeToLookup) {
        if (nodeToLookup == null) {
            NullPointerException e = new NullPointerException("Node to look up may not be null!");
            this.logger.error("Null pointer", e);
            throw e;
        }
        return this.successors.contains(nodeToLookup);
    }

    // FastPath 还是必须要这么做？论文没有这一步，但是似乎必须要这么做
    final Node getClosestPrecedingNode(ID idToLookup) {

        if (idToLookup == null) {
            NullPointerException e = new NullPointerException("ID to look up may not be null!");
            this.logger.error("Null pointer", e);
            throw e;
        }

        for (int i = this.successors.size() - 1; i >= 0; i--) {
            Node nextNode = this.successors.get(i);
            if (nextNode.getId().isInInterval(this.localID, idToLookup)) {
                return nextNode;
            }
        }

        return null;
    }

    @Override
    public final String toString() {
        StringBuilder result = new StringBuilder("Successor List:\n");
        for (Node next : this.successors) {
            result.append("  " + next.getId().toString() + ", " + next.getUrl() + "\n");
        }
        return result.toString();
    }

    public final List<Node> getReferences() {
        return Collections.unmodifiableList(this.successors);
    }



//    void addSuccessor(Node nodeToAdd) throws CommunicationException {
//        if (nodeToAdd == null) {
//            NullPointerException e = new NullPointerException("Parameter may not be null!");
//            this.logger.error("Null pointer", e);
//            throw e;
//        }
//
//        if (this.successors.contains(nodeToAdd)) {
//            this.logger.debug("Reference to new node " + nodeToAdd.toString() + " is not added to successor list, because it is " + "already contained.");
//            return;
//        }
//
//        // if new Id is between last ID in list and this node's own ID _AND_ successor list already has maxinum
//        // allowed list, the new reference IS not added
//        if (this.successors.size() >= this.capacity &&
//                nodeToAdd.getId().isInInterval(this.successors.get(this.successors.size() - 1).getId(), this.localID)) {
//            // do nothing
//            this.logger.debug("Reference to new node " + nodeToAdd.toString() +
//                    " is not added to successor list, because the " + "list is already full and the new reference is "
//                    + "further away from the local node than all other " + "successors.");
//            return;
//        }
//
//        boolean inserted = false;
//        for (int i = 0; i < this.successors.size() && !inserted; i++) {
//            if (nodeToAdd.getId().isInInterval(this.localID, this.successors.get(i).getId())) {
//                this.successors.add(i, nodeToAdd);
//                this.logger.info("Added new reference at position " + i);
//                inserted = true;
//            }
//        }
//
//        // insert at end if list not long enough
//        if (!inserted) {
//            this.successors.add(nodeToAdd);
//            this.logger.info("Added new reference to end of list");
//            inserted = true;
//        }
//
//        // determine ID range of entries this node is responsible for
//        // and replicate them on new node
//        ID fromID;
//        Node predecessor = this.references.getPredecessor();
//    }


}
