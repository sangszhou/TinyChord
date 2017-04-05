package core.service;

import core.data.ID;
import core.data.URL;
import io.netty.util.concurrent.Future;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by xinszhou on 28/03/2017.
 */
public interface Chord {
    URL getURL();

    void setURL(URL nodeURL) throws IllegalStateException;

    ID getID();

    void setID(ID nodeID) throws IllegalStateException;

//    void create();
//
//    void create(URL localURL);

    void create(URL localURL, ID localID);

//    void join(URL bootstrapURL);
//
//    void join(URL localURL, URL bootstrapURL);

    void join(URL localURL, ID localID, URL bootstrapURL);


    void insert(Key key, Serializable object);

    Set<Serializable> retrieve(Key key);



    //    stage 2
//    void leave();
//    void remove(Key key, Serializable object);
//    Future<Set<Serializable>> retrieveAsync(Key key);
//    Future<Key> removeAsync(Key key, Serializable object);
//    Future<Key> insertAsync(Key key, Serializable object);


}
