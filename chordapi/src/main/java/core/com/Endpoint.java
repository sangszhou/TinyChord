package core.com;

import core.data.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by xinszhou on 28/03/2017.
 */
public abstract class Endpoint {
    protected Node node;
    protected URL url;
    private State state;

    public enum State {
        STARTED(-1), LISTENING(1), ACCEPT_ENTRIES(2), DISCONNECTED(3), CRASHED(Integer.MAX_VALUE);

        private int order;

        private State(int order) {
            this.order = order;
        }

        public boolean isRunning() {
            return this == LISTENING || this == ACCEPT_ENTRIES;
        }

        public boolean isCrashed() {
            return this == CRASHED;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }

    public static final List<String> METHODS_ALLOWED_IN_ACCEPT_ENTRIES;

    static {
        String[] temp = new String[]{"insertEntry", "removeEntry", "retrieveEntries"};
        Arrays.sort(temp);
        List<String> list = new ArrayList<String>(Arrays.asList(temp));
        METHODS_ALLOWED_IN_ACCEPT_ENTRIES = Collections.unmodifiableList(list);
    }


    public void acceptEntries() {
        setState(State.ACCEPT_ENTRIES);
        entriesAcceptable();
    }

    protected abstract void entriesAcceptable();

    protected abstract void closeConnections() throws CommunicationException;

    protected abstract void openConnections() throws CommunicationException;

    public void disconnect() throws CommunicationException {
        state = State.STARTED;
        closeConnections();
    }

    public void listen() throws CommunicationException {
        state = State.LISTENING;
        this.openConnections();
    }


    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static List<String> getMethodsAllowedInAcceptEntries() {
        return METHODS_ALLOWED_IN_ACCEPT_ENTRIES;
    }


}
