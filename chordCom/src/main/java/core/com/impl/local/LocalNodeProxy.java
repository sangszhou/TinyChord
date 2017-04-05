package core.com.impl.local;

import core.com.CommunicationException;
import core.com.Endpoint;
import core.com.Node;
import core.data.Entry;
import core.data.ID;
import core.data.URL;

import java.util.List;
import java.util.Set;

/**
 * Created by xinszhou on 30/03/2017.
 * proxy for remote node
 */
public class LocalNodeProxy extends Node {

    Registry registry;
    URL srcUrl;
    // remote local endpoint, best to set localEndpoint
    LocalEndpoint endpoint;

    public LocalNodeProxy(URL srcUrl, URL dstUrl) {
        this.srcUrl = srcUrl;
        this.url = dstUrl;
        registry = Registry.getInstance();
        endpoint = registry.registedEndpoint.get(dstUrl);
    }

    @Override
    public void ping() throws CommunicationException {
        endpoint.ping();
    }

    @Override
    public Node findSuccessor(ID id) throws CommunicationException {
        return endpoint.findSuccessor(id);
    }

    @Override
    public List<Node> notify(Node potentialPredecessor) throws CommunicationException {
        return endpoint.notify(potentialPredecessor);
    }

    @Override
    public void insertEntry(Entry entry) throws CommunicationException {
        endpoint.insertEntry(entry);
    }

    @Override
    public Set<Entry> retrieveEntries(ID id) throws CommunicationException {
        return endpoint.retrieveEntries(id);
    }
}
