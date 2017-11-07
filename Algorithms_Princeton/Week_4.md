## Priority Queue

### Binary Heap
Binary heap: array representation of a heap-ordered complete binary tree    
Heap-ordered binary tree: parent key no smaller than children key

Propositions:
1. Root(largest key) in a[1]
2. Parent of node k is at a[k/2]  
    Children of node k are a[2k] and a[2k+1]    

**Operations**:
1. Promotion in a Binary Heap - swim  
  ```
  private void swim(int k) {
      while(k>1 && a[k/2].compareTo(a[k]) < 0) {
          swap(k, k/2);
          k=k/2;
      }
  }
  ```
2. Insertion in a Binary Heap
  ```
  public void insert(Key v) {
      a[++N] = v;
      swim(N);
  }
  ```
3. Demotion in a Binary Heap - sink
  ```
  private void sink(k) {
      while(2*k <= N) {
          int j = 2*k;
          if(j<N && a[j].compareTo(a[j+1]) < 0) j++;
          if(a[k].compareTo(a[j])>=0) break;
          swap(k,j);
          k = j;
      }
  }
  ```
4. Delete the maximum in a heap
  ```
  public Key delMax() {
      Key max = a[1];
      swap(1, N--);
      sink(1);
      a[N+1] = null;
      return max;
  }
  ```


### Heap Sort
1. Heap construction: bottom-up method
2. Sort down: repeatedly delete the max

In-place sorting with NlogN worst case guarantee   
Not stable




## Elementary Symbol Tables
1. Associative array abstraction:one value for each key     
2. Java equals() method      
  ```java
  public boolean equals(Object that) {
      if(that == this) return true;
      if(that == null) return false;
      // religion: getClass() vs. instanceof
      if(that.getClass() != this.getClass()) return false;
      // specific equal code for Object

  }
  ```
3. Ordered Operations      
search; insert/delete; min/max; floor/ceiling; rank; select; ordered iteration

### Binary Search Trees
**Definition**: A BST is a binary tree in symmetric order.      
**Symmetric order**: Each node has a key, and each key is: Larger than all keys in its left subtree; Smaller than all keys in its right subtree.     
**Java definition**: A BST is a reference to root node  
```java
public class BST<Key implements Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private int count;
        public Node(Key key, Value value, int count) {
            this.key = key;
            this.value = value;
            this.count = count;
        }
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        if(x == null) return new Node(key, value, 1);
        int cmp = key.compareTo(x.key);
        if(cmp > 0)
            x.right = put(x.right, key, value);
        else if(cmp < 0)
            x.left = put(x.left, key, value);
        else
            x.value = value;
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Value get(Key key) {
        Node x = root;
        while(x != null) {
            int cmp = key.compareTo(x.key);
            if(cmp > 0) x = x.right;
            else if(cmp < 0) x = x.left;
            else return x.value;
        }
        return null;
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if(x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0) return x;
        if(cmp < 0) return floor(x.left, key);
        Node y = floor(x.right, key);
        if(y != null) return y;
        else return x;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if(x == null) return 0;
        return x.count;
    }

    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if(x == null) return 0;
        int cmp = key.compareTo(x.key);
        if(cmp == 0) return size(x.left);
        else if(cmp < 0) return rank(key, x.left);
        else if(cmp > 0) return 1 + size(x.left) + rank(key, x.right);
    }

    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        inorder(root, q);
        return q;
    }

    private void inorder(Node x, Queue<Key> q) {
        if(x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if(x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    // Hibbard deletion -- leads to asymmetric of sqrt(N) height
    public void delete() {
        root = delete(root);
    }

    private Node delete(Node x, Key key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) x.left = delete(x.left, key);
        else if(xmp > 0) x.right = delete(x.right, key);
        else {
            if(x.right == null) return x.left;
            if(x.left == null) return x.right;

            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.count = 1 + size(x.left) + size(x.right);
    }
}

```

**Remark**: tree shape depends on order of insertion; correspondence with quicksort partitioning; Hibbard deletion leads to sqrt(N) performance.
