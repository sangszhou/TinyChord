package core.com;

import core.data.Entry;
import core.data.ID;
import core.data.URL;

import java.util.List;
import java.util.Set;

/**
 * Created by xinszhou on 28/03/2017.
 */
public abstract class Node {

    protected ID id;
    protected URL url;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof Node))
            return false;
        return ((Node) o).id.equals(id);
    }

    public abstract void ping() throws CommunicationException;

    public abstract Node findSuccessor(ID id) throws CommunicationException;

    // tell successor that his predecessor is $argument
    public abstract List<Node> notify(Node potentialPredecessor) throws CommunicationException;

    public abstract void insertEntry(Entry entry) throws CommunicationException;
    public abstract Set<Entry> retrieveEntries(ID id) throws CommunicationException;



    //state2
//    public abstract void insertReplicas(Set<Entry> entries) throws CommunicationException;
//    public abstract void removeReplicas(ID sendingNode, Set<Entry> replicasToRemove) throws CommunicationException;
//    public abstract void leavesNetwork(Node predecessor) throws CommunicationException;
//    public abstract void disconnect() throws CommunicationException;
//    public abstract void removeEntry(Entry entry) throws CommunicationException;
}
