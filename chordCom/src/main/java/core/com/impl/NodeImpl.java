package core.com.impl;

import core.com.CommunicationException;
import core.com.Endpoint;
import core.com.Node;
import core.data.Entry;
import core.data.ID;
import core.data.URL;
import core.service.impl.Entries;
import core.service.impl.References;
import core.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by xinszhou on 30/03/2017.
 * NodeImpl 是本地节点的具体实现，endpoint 收到的消息，一般由此 Instance 来处理
 */
public class NodeImpl extends Node {
    Logger logger = LoggerFactory.getLogger(getClass());

    ID localId;
    URL localUrl;
    Entries entries;
    Endpoint localEndpoint;
    References references;

    public NodeImpl(ID localId, URL localUrl) {
        this.localId = localId;
        this.localUrl = localUrl;
        this.entries = new Entries();
        localEndpoint = Endpoints.createEndpoint(localUrl, this);
    }

    // 开始接受数据
    public void acceptEntries() {
        localEndpoint.acceptEntries();
    }

    @Override
    public void ping() throws CommunicationException {
        logger.info("ping msg received from remote");
    }

    @Override
    public Node findSuccessor(ID key) throws CommunicationException {
        if (key == null) {
            logger.error("key cannot be null");
            return null;
        }

        Node successor = references.getSuccessor();
        if (successor == null) {
            logger.info("failed to find successor, this node is the only one in chord ring");
            return this;
        } else if (key.isInInterval(this.getId(), successor.getId()) || key.equals(successor.getId())) {
            logger.info("successor is the node we are looking for, no need to call rpc");
            return successor;
        } else {
            Node closestPrecedingNode = this.references.getClosestPrecedingNode(key);
            return closestPrecedingNode.findSuccessor(key);
        }
    }


    // nodeImpl is callback, so notify here is called in endpoint which means other node told
    // this node its predecessor is $argument
    // if current predecessor is null, then set it
    // if current predecessor is not null, then we might need to remove replicas from successor
    // because if currentPredecessor might < newPredecessor
    // but why return List<Node> ??/?
    @Override
    public List<Node> notify(Node potentialPredecessor) throws CommunicationException {
        List<Node> result = new LinkedList<>();
        if (this.references.getPredecessor() != null) {
            result.add(references.getPredecessor());
        } else {
            result.add(potentialPredecessor);
        }

        result.addAll(this.references.getSuccessors());

        // add predecessor to reference

        return null;
    }

    @Override
    public void insertEntry(Entry entry) throws CommunicationException {
        try {
            logger.info("add entry to nodeImpl " + JSONUtils.toJson(entry));
        } catch (Exception e) {
            logger.error("failed to parse entry",e);
        }

        entries.add(entry);
    }


    @Override
    public Set<Entry> retrieveEntries(ID id) throws CommunicationException {
        Set<Entry> result = entries.getEntries(id);
        if (result == null) {
            return new HashSet<>();
        }
        return result;
    }

}
