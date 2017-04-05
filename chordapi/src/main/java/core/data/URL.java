package core.data;

import java.io.Serializable;
import java.net.MalformedURLException;

/**
 * Created by xinszhou on 28/03/2017.
 */
public interface URL extends Serializable {
    String getProtocol();

    String getHost();

    int getPort();

    String getPath();
}
