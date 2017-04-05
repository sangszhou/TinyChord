package core.com.impl.local;

import core.com.Endpoint;
import core.data.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinszhou on 30/03/2017.
 */
public class Registry {

    private final static Logger logger = LoggerFactory.getLogger(Registry.class.getName());

    Map<URL, LocalEndpoint> registedEndpoint;

    private Registry() {
        registedEndpoint = new HashMap<>();
    }


    public void bind(LocalEndpoint endpoint) {
        setEndpoint(endpoint.getUrl(), endpoint);
    }

    public void unBind(Endpoint endpoint) {
        registedEndpoint.remove(endpoint.getUrl());
    }

    private Endpoint getEndpoint(URL url) {
        return registedEndpoint.get(url);
    }

    private void setEndpoint(URL url, LocalEndpoint endpoint) {
        this.registedEndpoint.put(url, endpoint);
    }

    public static volatile Registry registry;

    public static Registry getInstance() {
        if (registry == null) {
            synchronized (Registry.class) {
                if (registry == null) {
                    registry = new Registry();
                }
            }
        }
        return registry;
    }
}
