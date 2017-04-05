package core.service.impl;

import core.com.Node;
import core.data.ID;
import core.data.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by xinszhou on 28/03/2017.
 *
 * reference is very useful, for example, we want to check if one node is reference, we can check predecessor, fingertable
 * and successorList individually or make reference a wrapper and use that wrapper
 */
public class References {

    private Logger logger;
    private FingerTable fingerTable = null;
    private SuccessorList successorList = null;
    private Node predecessor = null;
    private ID localID = null;
    private URL localURL = null;
    private Entries entries;

    public References(ID localID, URL localURL, int numOfEntriesInSuccessorList, Entries entries) {
        if (localID == null || localURL == null || entries == null) {
            logger.error("failed to init because argument is null");
        }
        if (numOfEntriesInSuccessorList < 1) {
            logger.error("success list must be bigger than 1");
        }

        this.logger = LoggerFactory.getLogger(getClass());
        this.localID = localID;
        this.localURL = localURL;
        this.entries = entries;
        this.fingerTable = new FingerTable(localID, this);
        this.successorList = new SuccessorList(localID, numOfEntriesInSuccessorList, this, entries);
    }

    // local search
    // 从 predecessor, fingerTable, successorList 三个位置找最接近的一个
    public synchronized Node getClosestPrecedingNode(ID key) {
        if (key == null) {
            logger.error("key cannot be null");
        }

        Map<ID, Node> candidateNodes = new HashMap<>();
        Node closestNodeFT = this.fingerTable.getClosestPrecedingNode(key);
        if (closestNodeFT != null) {
            candidateNodes.put(closestNodeFT.getId(), closestNodeFT);
        }

        Node closestNodeSL = this.successorList.getClosestPrecedingNode(key);
        if (closestNodeFT != null) {
            candidateNodes.put(closestNodeSL.getId(), closestNodeFT);
        }

        if (this.predecessor != null && key.isInInterval(this.predecessor.getId(), this.localID)) {
            candidateNodes.put(this.predecessor.getId(), this.predecessor);
        }

        List<ID> orderedIdList = new ArrayList<>(candidateNodes.keySet());
        orderedIdList.add(key);
        Collections.sort(orderedIdList);
        int keyIndex = orderedIdList.indexOf(key);

        int sizeOfList = orderedIdList.size();
        int index = (keyIndex-1 + sizeOfList) % sizeOfList;
        ID idOfClosestNode = orderedIdList.get(index);
        Node closestNode = candidateNodes.get(idOfClosestNode);
        if (closestNode == null) {
            logger.error("failed to find closest node");
        }

        return closestNode;
    }

    public Node getSuccessor() {
        return successorList.getDirectSuccessor();
    }

    public List<Node> getSuccessors() {
        return successorList.getReferences();
    }

    public Node getPredecessor() {
        return this.predecessor;
    }

    public void addPredecessor(Node potentialPredecessor) {

    }


}
