package core.com.impl;


import core.com.Endpoint;
import core.com.Node;
import core.com.impl.local.LocalEndpoint;
import core.data.URL;

/**
 * Created by xinszhou on 30/03/2017.
 */
public class Endpoints {
    public static Endpoint createEndpoint(URL url, Node node) {
        // create local endpoint by default
        return new LocalEndpoint(url, node);
    }
}
