## Balanced Search Trees

### 2-3 Search Trees
2-node: 1 key, 2 children     
3-node: 2 keys, 3 children      

Insert into a 2-node at bottom      
Insert into a 3-node at bottom

Invariant: Maintains symmetric order and perfect balance    
Guaranteed logarithmic performance      


### Red-Black BST (Left-leaning)
1. Represent 2-3 tree as BST
2. Use internal left-leaning link as glue for 3-nodes

**Observations**:
1. perfect black balance    
2. 1-1 correspondence between 2-3 and LLRB   
3. Search is the same as Elementary BST

**Elementary Operations**:      
1. Left Rotation     
  ```java
  private Node rotateLeft(Node h) {
      Node x = h.right;
      h.right = x.left;
      x.left = h;
      x.color = h.color;
      h.color = RED;
      return x;
  }
  ```
2. Right Rotation    
    ```java
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }
    ```
3. Color flip      
    ```java
    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }
    ```

**Insertion**     
1. Insert into a 2-node at the bottom   
  * Do standard BST insert, color the link red;     
  * If new red link is right, rotate left
2. Insert into a 3-node at the bottom     
  * Do standard BST insert, color the link red;     
  * Rotate to balance the 4-node if need;     
  * Flip colors to pass red link on level up;     
  * Rotate to make lean left if needed;   
  * Repeat up the tree if needed.


```java
private Node put(Node h, Key key, Value value) {
    if(h == null) return new Node(key, value, RED);
    int cmp = key.compareTo(h.key);
    if (cmp < 0) h.left = put(h.left, key, value);
    else if (cmp > 0) h.right = put(h.right, key, value);
    else h.value = value;

    if(isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
    if(isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
    if(isRed(h.left) && isRed(h.right)) flipColors(h);

    return h;
}
```

**Performance**
1. worst case: 2lgN
2. average: 1.0lgN


### B-Trees


## Geometric Applications of BST

### 1-d Range Search
* Range Count: rank
* Range Search: recursive check


### Line Segment Intersection
sweep-line algorithm leads to 1-d range search

## Kd-Trees     
recursively divide space into half-spaces, using kth coordinate

### 1-d Interval Search     
Interval Search Tree
* Use left endpoint as BST key
* Store max endpoint in subtree rooted at node

### Rectangle Intersection
sweep-line algorithm leads to 1-d interval search 
