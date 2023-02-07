package hw6.bst;

import hw6.OrderedMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * Map implemented as an AVL Tree.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
  }

  private Node<K, V> insert(Node<K, V> n, K k, V v) {
    if (n == null) {
      return new Node<>(k, v);
    }

    int cmp = k.compareTo(n.key);
    if (cmp < 0) {
      n.left = insert(n.left, k, v);
    } else if (cmp > 0) {
      n.right = insert(n.right, k, v);
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }
    updateHeight(n);
    if (notBalanced(n)) {
      n = beginRotation(n);
    }
    return n;
  }

  private void updateHeight(Node<K, V> n) {
    if (!isLeaf(n)) {
      n.height = 1 + maxChildHeight(n);
    } else {
      n.height = 0;
    }
  }

  private int maxChildHeight(Node<K, V> n) {
    if (n.left == null) {
      return n.right.height;
    } else if (n.right == null) {
      return n.left.height;
    } else {
      return Math.max(n.left.height, n.right.height);
    }
  }

  private boolean isLeaf(Node<K, V> n) {
    return (n.left == null && n.right == null);
  }

  private Node<K, V> beginRotation(Node<K, V> n) {
    if (n.bf > 1) {
      if (n.left.bf == -1) {
        n.left = leftRotation(n.left);
      }
      return rightRotation(n);//n.left.bf 1 or 0
    } else {
      if (n.right.bf == 1) {
        n.right = rightRotation(n.right);
      }
      return leftRotation(n);//n.right.bf -1 OR 0
    }
  }

  private void heightAfterRightRotation(Node<K, V> n, Node<K, V> lefTree) {
    updateHeight(n);
    updateHeight(lefTree);
  }

  private void heightAfterLeftRotation(Node<K, V> n, Node<K, V> rigTree) {
    updateHeight(n);
    updateHeight(rigTree);
  }

  private Node<K, V> rightRotation(Node<K, V> n) {
    Node<K, V> lefChild = n.left;
    Node<K, V> lefRigChild = lefChild.right;
    lefChild.right = n;
    n.left = lefRigChild;
    heightAfterRightRotation(n, lefChild);
    return lefChild;
  }

  private Node<K, V> leftRotation(Node<K, V> n) {
    Node<K, V> rigChild = n.right;
    Node<K, V> rigLefChild = rigChild.left;
    rigChild.left = n;
    n.right = rigLefChild;
    heightAfterLeftRotation(n, rigChild);
    return rigChild;
  }


  private boolean notBalanced(Node<K, V> n) {
    int lefTree;
    int rigTree;
    if (n.left == null) {
      lefTree = -1;
    } else {
      lefTree = n.left.height;
    }
    if (n.right == null) {
      rigTree = -1;
    } else {
      rigTree = n.right.height;
    }
    n.bf = lefTree - rigTree;
    return n.bf > 1 || n.bf < -1;
  }

  private Node<K, V> remove(Node<K, V> subtreeRoot, Node<K, V> toRemove) {
    int cmp = subtreeRoot.key.compareTo(toRemove.key);
    if (cmp == 0) {
      return remove(subtreeRoot);
    } else if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
    } else {
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
    }
    updateHeight(subtreeRoot);
    if (notBalanced(subtreeRoot)) {
      subtreeRoot = beginRotation(subtreeRoot);
    }
    return subtreeRoot;
  }

  private Node<K, V> remove(Node<K, V> node) {
    // Easy if the node has 0 or 1 child.
    if (node.right == null) {
      return node.left;
    } else if (node.left == null) {
      return node.right;
    }

    // If it has two children, find the predecessor (max in left subtree),
    Node<K, V> toReplaceWith = max(node);
    // then copy its data to the given node (value change),
    node.key = toReplaceWith.key;
    node.value = toReplaceWith.value;
    // then remove the predecessor node (structural change).
    node.left = remove(node.left, toReplaceWith);

    return node;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    V value = node.value;
    root = remove(root, node);
    size--;
    return value;
  }

  private Node<K, V> max(Node<K, V> node) {
    Node<K, V> curr = node.left;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
  }

  private Node<K, V> findForSure(K k) {
    Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
  }

  private Node<K, V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> n = root;
    while (n != null) {
      int cmp = k.compareTo(n.key);
      if (cmp < 0) {
        n = n.left;
      } else if (cmp > 0) {
        n = n.right;
      } else {
        return n;
      }
    }
    return null;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    n.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    return n.value;
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return find(k) != null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
  }

  private class InorderIterator implements Iterator<K> {
    private final Stack<Node<K, V>> stack;

    InorderIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }

    private void pushLeft(Node<K, V> curr) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      Node<K, V> top = stack.pop();
      pushLeft(top.right);
      return top.key;
    }
  }

  /*** Do not change this function's name or modify its code. ***/
  @Override
  public String toString() {
    return BinaryTreePrinter.printBinaryTree(root);
  }

  /**
   * Feel free to add whatever you want to the Node class (e.g. new fields).
   * Just avoid changing any existing names, deleting any existing variables,
   * or modifying the overriding methods.
   *
   * <p>Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers.</p>
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int height;
    int bf;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      height = 0;
      bf = 0;
    }

    @Override
    public String toString() {
      return key + ":" + value;
    }

    @Override
    public BinaryTreeNode getLeftChild() {
      return left;
    }

    @Override
    public BinaryTreeNode getRightChild() {
      return right;
    }
  }

}
