Update

2017年03月30日 星期四

实现 1) join cluster 2) find successor in local mode


## Design procedure

1. design local chord, not considering networking but should have interface reserved
2. write high level API, such as chord, node, endpoint, etc.


## Test cases

1. Join node to cluster, the newly joined node should be able to init his finger table. And the existing
node should be able to detect the change and update accordingly (the latter may need stabilize task)

2. a cluster with two node, the node should be able to find the destination node and store the value in it
same is for remove, retrieve and so on.

3. remove a node from cluster, the only node in cluster should be able to  