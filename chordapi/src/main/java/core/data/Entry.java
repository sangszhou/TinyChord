package core.data;

import java.io.Serializable;

/**
 * Created by xinszhou on 28/03/2017.
 */
public interface Entry extends Serializable {
    ID getId();

    void setId(ID id);

    Serializable getValue();

    void setValue(Serializable value);
}

