package core.service.impl;

import core.com.CommunicationException;
import core.com.Node;
import core.data.ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xinszhou on 28/03/2017.
 */
public class FingerTable {
    private final ID localID;
    private final Node[] remoteNodes;
    private Logger logger;
    References references;


    FingerTable(ID localID, References references) {
        if (localID == null) {
            throw new NullPointerException("LocalId cannot be null");
        }

        this.localID = localID;
        this.references = references;
        this.logger = LoggerFactory.getLogger(getClass());
        this.remoteNodes = new Node[ID.size];
    }

    // do we need to open connection to remote node?
    private final void setEntry(int index, Node proxy) {
        if (index < 0 || index > this.remoteNodes.length) {
            logger.error("index out of range");
        }

        if (proxy == null) {
            logger.error("proxy to be inserted cannot be null");
        }

        this.remoteNodes[index] = proxy;
    }

    private final Node getEntry(int index) {
        if (index < 0 || index >= this.remoteNodes.length) {
            logger.error("index of range for get entry");
        }

        return this.remoteNodes[index];
    }

    final void unsetEntry(int index) throws CommunicationException {
        if (index < 0 || index >= this.remoteNodes.length) {
            logger.error("index out of range for unset entry method");
        }

        Node overwrittenNode = this.getEntry(index);
        this.remoteNodes[index] = null;

        if (overwrittenNode == null) {
            logger.info("unsetEntry didnot change anything, because its null before");
        } else {
            overwrittenNode.disconnect();
        }
    }

    final Node getClosestPrecedingNode(ID key) {
        if (key == null) {
            logger.info("key is null");
        }

        for(int i = this.remoteNodes.length-1; i >= 0; i --) {
            if (this.remoteNodes[i] != null
                    && this.remoteNodes[i].getId().isInInterval(this.localID, key)) {
                return this.remoteNodes[i];
            }
        }

        return null;
    }

    // the first (i+1) entries of finger table
    List<Node> getFirstFingerTableEntries(int i) {
        Set<Node> result = new HashSet<>();
        for(int j = 0; j < this.remoteNodes.length; j ++) {
            if (this.getEntry(j) != null) {
                result.add(this.getEntry(j));
            }
            if (result.size() >= i) {
                break;
            }
        }
        return new ArrayList<>(result);
    }

    final void addNode(Node proxy) throws CommunicationException {
        if (proxy == null) {
            logger.error("node to be added is null");
        }

        for(int i = 0; i < this.remoteNodes.length; i ++) {
            ID startOfInterval = this.localID.addPowerOfTwo(i);
            // if not so, proxy.getId is out of range
            if (!startOfInterval.isInInterval(this.localID, proxy.getId())) {
                break;
            }

            if (getEntry(i) == null) {
                setEntry(i, proxy);

            } else if (proxy.getId().isInInterval(this.localID, getEntry(i).getId())) {
                // why not using compare to?
                Node oldEntry = getEntry(i);
                setEntry(i, proxy);
                //@error, need to check
                oldEntry.disconnect();
            }
        }
    }


    void removeNode(Node node) throws CommunicationException {
        if (node == null) {
            logger.error("failed to remove node, because node is null");
        }

        // node that bigger than toBeRemovedNode
        Node nextBiggerNode = null;
        for(int i = ID.size; i >= 0; i --) {
            Node n = this.getEntry(i);
            if (node.equals(getEntry(i))) {
                break;
            }
            if (n != null) {
                nextBiggerNode = n;
            }
        }

        // remove node and fill the hole
        for(int i = 0; i < ID.size; i ++) {
            if (node.equals(this.remoteNodes[i])) {
                if (nextBiggerNode == null) {
                    unsetEntry(i);
                } else {
                    setEntry(i, nextBiggerNode);
                }
            }
        }

        // optimize, try to use successor to fill the hold @todo
    }





}
