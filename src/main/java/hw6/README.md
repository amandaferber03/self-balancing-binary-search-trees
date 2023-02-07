# Discussion

## Unit testing TreapMap

Because the priorities are randomly generated, it is not possible to obtain to the expected structure of the
treap without ascertaining the values of each node's priority beforehand. With AVL trees, one could precisely
predict the structure of the final, balanced product due to the easy accessibility of each node's height balance
factor. However, with treaps as we have implemented them, it is impossible to know whether the tree will be exactly
balanced and how the tree will self-select a root and its descendants as the priorities are generated. Therefore, 
I created a non-default constructor that would allow me to pass in a seed to the Random object that serves as 
our pseudorandom number generator. Given the design of the provided test files, however, it made the most sense
to only intialize a Treap map once, therefore giving me only one sequence of randomly generated priorities to work 
with. I chose the seed 50, which meant that the nodes in my treaps were assigned the following priorities in this
order:
1: -1160871061
2: -1727040520
3: -1657178909
4: -765924271
5: -1625295794
6: 2102979123
7: -2137358024
8: -1275367180
9: -942771064
10: -900205280
Given the knowledge of the order of the priority values assigned, I needed to manipulate the values of the keys
of the nodes to create different insertion/removal scenarios in which either the left or right child had a
priority that was less than that of its parent. Here is example 1:
              2                             2
            /     \         insert(5)    /   \
          1       3       --------->    1     3 
                   \                           \ 
                   4                            5
                                                 \
                                                  4
The numbers were inserted in the following order: [1, 2, 3, 4, 5]. A left rotation was needed when 2 was inserted,
and an additional left rotation was needed when 5 was inserted (shown above). If this were an unbalanced binary search tree, 
this would degenerate into a right-heavy linked list. However, since the numbers were assigned the random priorities
shown above, 4's priority (-765924271) is greater than 5's priority (-1625295794). This meant that 4 needed to below
5 in the treap in order to maintain the heap order property. Therefore, I knew that a left rotation needed to be 
performed because 5 would have been the right child of 4 had the tree not restructured itself. Due to the random, imperfect
nature of treaps, this did not result in a balanced tree. 

Example 2:
   2                             2
/     \         remove(3)      /   \
1      3       --------->     1     5
       \                          /  \
        5                        4    6
       / \
      4   6

While one does not need to know the priorities of the nodes to ascertain that rotations will indeed occur to 
restructure the treap, one must ascertain the priorities in order to determine how many right and how many left
rotations will occur in most cases. However, in this example, each rotation required to remove 3 resulted in 3 having 
only one child, allowing us to select the appropriate rotations without having to compare the priorities of two
different children. After the program assigned the max integer value to the node containing 3 (the node to remove),
we know (even without knowledge of the priorities) that an initial left rotation occurred since 3 only has one child (5), 
and that child is a right child. However, the knowledge of the priorities helped to determine that the next rotation 
would be a left rotation since 3's only child is 4.

## Benchmarking
I experimented with three datasets: Federalist01, Moby Dick, and Pride and Prejudice. The results of the experiments
are shown below:
Federalist01:
Benchmark                  Mode  Cnt    Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2   1.399          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2   0.699          ms/op
JmhRuntimeTest.bstMap      avgt    2   0.674          ms/op
JmhRuntimeTest.treapMap    avgt    2   0.878          ms/op


Moby Dick:
Benchmark                  Mode  Cnt    Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  1953.898          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2   109.483          ms/op
JmhRuntimeTest.bstMap      avgt    2   109.622          ms/op
JmhRuntimeTest.treapMap    avgt    2   132.177          ms/op

Pride and Prejudice:
Benchmark                  Mode  Cnt    Score   Error  Units
JmhRuntimeTest.arrayMap    avgt    2  402.491          ms/op
JmhRuntimeTest.avlTreeMap  avgt    2   59.565          ms/op
JmhRuntimeTest.bstMap      avgt    2   58.530          ms/op
JmhRuntimeTest.treapMap    avgt    2   66.831          ms/op

It should be noted that arrayMap was the slowest implementation of the map ADT because it follows an array-based
implementation, meaning that traversal/insertion/remove/find will have a worst-case runtime of O(n) since we must
search the entire data structure for the presence of a key before being able to add or remove it. However, the
latter three data structures experimented with are binary trees in nature, meaning that it is not necessary to
traverse all nodes in the data structure in order to approach a target node. Further, the AVLTreeMap and BST were the fastest
of the  map implementations. With AVLTreeMap, all insertion/removal/traversal operations will have a worstcase runtime
of O(lg n). This is because AVL trees attempt to rebalance themselves with each structural modification
so that they are not right or left heavy. With binary search trees, however, no rebalancing attempts are made (demonstrative 
of their O(height) runtime. If values are inserted in ascending order, BSTs have no defense mechanism to prevent their 
degeneration into a linked list. Since AVLTreeMaps will ultimately do less traversal in the long run, AVLTreeMap
performed the fastest with the longest text (Moby Dick). Lastly, treapMap was the slowest of the three tree map
implementations. While treapMap also has a runtime of O(height) on all pertinent operations, the treap is subject to
random prioritization of certain nodes. While this often results in a near-balanced trees, it can sometimes bode
worse for the balance of the tree when it arbitrarily causes the tree to be right or left heavy.