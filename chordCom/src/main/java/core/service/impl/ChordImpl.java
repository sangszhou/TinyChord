package core.service.impl;

import core.com.Node;
import core.com.impl.NodeImpl;
import core.com.impl.Nodes;
import core.data.ID;
import core.data.URL;
import core.service.Chord;
import core.service.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by xinszhou on 30/03/2017.
 */
public class ChordImpl implements Chord{

    NodeImpl localNode;
    References references;
    URL localUrl;
    ID localId;
    Logger logger = LoggerFactory.getLogger(getClass());

    //@todo read from config
    private int NUM_OF_SUCCESSORS = 1;

    public ChordImpl() {
        logger.info("chord impl inited");
    }

    public ChordImpl(ID localId, URL localUrl) {
        logger.info("chord impl inited");
        this.localId = localId;
        this.localUrl = localUrl;
    }

    @Override
    public URL getURL() {
        return this.localUrl;
    }

    @Override
    public void setURL(URL nodeURL) throws IllegalStateException {
        this.localUrl = nodeURL;
    }

    @Override
    public ID getID() {
        return this.localId;
    }

    @Override
    public void setID(ID nodeID) throws IllegalStateException {
        this.localId = nodeID;
    }

    @Override
    public void create(URL localURL, ID localID) {
        logger.info("creating a new chord ring");

        setID(localID);
        setURL(localURL);

        // set entry to null because don't know why entries are needed here
        this.references = new References(localId, localUrl, NUM_OF_SUCCESSORS);
        this.localNode = new NodeImpl(localId, localUrl);

        // 开始提供别的节点的接入服务, 自己作为集群中的一个节点
        localNode.acceptEntries();
        // 此时的 fingerTable 还是空的
    }


    // join 和 create 是并列的，两个自能选择一个
    @Override
    public void join(URL localURL, ID localID, URL bootstrapURL) {
        if (bootstrapURL == null) {
            logger.error("cluster seed is null");
        }

        if (localNode == null) {
            logger.error("local node cannot be null when joining cluster");
        }

        setID(localID);
        setURL(localURL);

        this.references = new References(localId, localUrl, NUM_OF_SUCCESSORS);
        this.localNode = new NodeImpl(localId, localUrl);

        try {

        }

        Node bootstrapNode = Nodes.createNode(localURL, bootstrapURL);

        bootstrapNode = Nodes



    }


    @Override
    public void insert(Key key, Serializable object) {

    }


    @Override
    public Set<Serializable> retrieve(Key key) {
        return null;
    }
}
