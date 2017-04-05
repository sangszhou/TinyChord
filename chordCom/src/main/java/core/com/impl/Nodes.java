package core.com.impl;

import core.com.Node;
import core.data.URL;

/**
 * Created by xinszhou on 30/03/2017.
 */
public class Nodes {
    public Node createNode(URL localUrl, URL remoteUrl) throws Exception {
        if(localUrl.equals(remoteUrl)) {
            throw new IllegalArgumentException("local url equal remote url");
        }

        return

    }
}
