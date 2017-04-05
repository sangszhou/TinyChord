package core.com.impl.local;

import core.com.CommunicationException;
import core.com.Endpoint;
import core.com.Node;
import core.data.Entry;
import core.data.ID;
import core.data.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.CommandInfo;
import java.util.List;
import java.util.Set;

/**
 * Created by xinszhou on 30/03/2017.
 */
public class LocalEndpoint extends Endpoint {

    Logger logger = LoggerFactory.getLogger(getClass());

    public LocalEndpoint(URL url, Node node) {
        this.url = url;
        this.node = node;
    }

    public void ping() throws CommunicationException {
        logger.info("local end point ping message received");
        this.node.ping();
    }

    public Node findSuccessor(ID id) throws CommunicationException {
        logger.info("local endpoint findSuccessor message received");
        return this.node.findSuccessor(id);
    }

    public List<Node> notify(Node potentialPredecessor) throws CommunicationException {
        logger.info("local endpoint notify message received");
        return this.node.notify(potentialPredecessor);
    }

    public void insertEntry(Entry entry) throws CommunicationException {
        logger.info("local endpoint insertEntry message received");
        this.node.insertEntry(entry);
    }

    public Set<Entry> retrieveEntries(ID id) throws CommunicationException {
        logger.info("local endpoint retrieveEntries message received");
        return this.node.retrieveEntries(id);
    }


    @Override
    protected void entriesAcceptable() {
        logger.info("entries acceptable");
        Registry.getInstance().bind(this);
    }

    @Override
    protected void closeConnections() throws CommunicationException {
        logger.info("close connection");
        Registry.getInstance().unBind(this);
    }

    @Override
    protected void openConnections() throws CommunicationException {
        logger.info("open connections");
    }
}
