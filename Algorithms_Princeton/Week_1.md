## Union-Find Problem

### Quick-Find Algorithm (Eager approach)
```java
public class QuickFind() {

    private int[] id;

    public QuickFind(int N) {
        id = new int[N];
        for(int i = 0; i < N; i++) id[i] = i;
    }

    public boolean connected(int p, int q) {
        return id[p] == id [q];
    }

    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for(int i = 0; i < id.length; i ++) {
            if(id[i] == pid) id[i] = qid;
        }
    }

}
```

### Quick-Union Algorithm (Lazy approach)
id[i] stores root of connected component

```java
public class QuickUnion() {

    private int[] id;

    public QuickUnion(int N) {
        id = new int[N];
        for(int i = 0; i < N; i++) id[i] = i;
    }

    public void union(int p, int q) {
        int pRoot = id[p];
        int qRoot = id[q];
        id[pRoot] = qRoot;
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    private int root(int i) {
        while(i != id[i]) i = id[i];
        return i;
    }
}
```

## Quick-Union Improvements

a. Weighted Quick-Union
    extra size[] to decrease tree height

b. Path compression


## Percolation Problem

1. use one WQUF object but has the backwash bug

```java
public class PercolationWithBackwash {
    private int size;
    private boolean[][] open;
    private WeightedQuickUnionUF wquf;
    private int numberOfOpenSites = 0;

    public PercolationWithBackwash(int n) {
        if (n <= 0) throw new IllegalArgumentException("size should be greater than 0");
        size = n;
        open = new boolean[n][n];
        wquf = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        validateIndex(row, col);
        open[row-1][col-1] = true;
        if (row == 1) {
            wquf.union(0, xyToId(row, col));
        }
        if (row == size) {
            wquf.union(size * size + 1, xyToId(row, col));
        }

        if (row > 1 && isOpen(row -1,col)) wquf.union(xyToId(row - 1, col), xyToId(row, col));
        if (col > 1 && isOpen(row,col - 1)) wquf.union(xyToId(row, col - 1), xyToId(row, col));
        if (col < size && isOpen(row, col+1)) wquf.union(xyToId(row, col + 1), xyToId(row, col));
        if (row < size && isOpen(row+1, col)) wquf.union(xyToId(row + 1, col), xyToId(row, col));
        numberOfOpenSites++;
    }

    public boolean isOpen(int row, int col) {
        return open[row-1][col-1];
    }

    public boolean isFull(int row, int col) {
        return wquf.connected(0, xyToId(row, col));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return wquf.connected(0, size * size + 1);
    }

    private void validateIndex(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IndexOutOfBoundsException("index out of bounds");
    }

    private int xyToId(int row, int col) {
        return (row - 1) * size + col;
    }

    public static void main(String[] args) {
    }
}
```

2. We could solve backwash by using 2 WQUF objects in the cost of more memory


3. use 1 WQUF object to fix backwash, in rid of virtual sites

```java
public class Percolation {
    private int size;
    private byte[] status;
    private WeightedQuickUnionUF wquf;
    private boolean percolates = false;
    private int numberOfOpenSites = 0;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("size should be greater than 0");
        size = n;
        status = new byte[n * n];
        wquf = new WeightedQuickUnionUF(n * n);
    }

    public void open(int row, int col) {
        validateIndex(row, col);
        int index = xyToId(row, col);
        int rootOfNeighbor;
        if (!isOpen(index)) {
            setOpen(status, index);
            numberOfOpenSites++;
        }
        boolean top = false;
        boolean bottom = false;

        if (row == 1) top = true;
        if (row == size) bottom = true;
        if (row > 1 && isOpen(index - size)) {
            rootOfNeighbor = wquf.find(index - size);
            if (isConnectedToTop(rootOfNeighbor)) top = true;
            if (isConnectedToBottom(rootOfNeighbor)) bottom = true;
            wquf.union(index, index - size);
        }
        if (row < size && isOpen(index + size)) {
            rootOfNeighbor = wquf.find(index + size);
            if (isConnectedToTop(rootOfNeighbor)) top = true;
            if (isConnectedToBottom(rootOfNeighbor)) bottom = true;
            wquf.union(index, index + size);
        }

        if (col > 1 && isOpen(index - 1)) {
            rootOfNeighbor = wquf.find(index - 1);
            if (isConnectedToTop(rootOfNeighbor)) top = true;
            if (isConnectedToBottom(rootOfNeighbor)) bottom = true;
            wquf.union(index, index - 1);
        }

        if (col < size && isOpen(index + 1)) {
            rootOfNeighbor = wquf.find(index + 1);
            if (isConnectedToTop(rootOfNeighbor)) top = true;
            if (isConnectedToBottom(rootOfNeighbor)) bottom = true;
            wquf.union(index, index + 1);
        }

        rootOfNeighbor = wquf.find(index);
        if (top) setConnectedToTop(status, rootOfNeighbor);
        if (bottom) setConnectedToBottom(status, rootOfNeighbor);
        if (top && bottom) percolates = true;
    }

    public boolean isOpen(int row, int col) {
        validateIndex(row, col);
        return isOpen(xyToId(row, col));
    }

    private boolean isOpen(int id) {
        return (status[id] & 1) > 0;
    }

    private void setOpen(byte[] x, int index) {
        x[index] |= 1;
    }

    private boolean isConnectedToTop(int id) {
        return (status[id] & (1 << 1)) > 0;
    }

    private void setConnectedToTop(byte[] x, int index) {
        x[index] |= (1 << 1);
    }

    private boolean isConnectedToBottom(int id) {
        return (status[id] & (1 << 2)) > 0;
    }

    private void setConnectedToBottom(byte[] x, int index) {
        x[index] |= (1 << 2);
    }

    public boolean isFull(int row, int col) {
        validateIndex(row, col);
        return isConnectedToTop(wquf.find(xyToId(row, col)));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return percolates;
    }

    private void validateIndex(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IndexOutOfBoundsException("index out of bounds");
    }

    private int xyToId(int row, int col) {
        return (row - 1) * size + col - 1;
    }

    public static void main(String[] args) {
    }
}
```

code credits:
1. [sigmainfy](http://www.sigmainfy.com/blog/avoid-backwash-in-percolation.html)
2. [some implemetation](http://www.cnblogs.com/anne-vista/p/4841732.html)


Theory of Algorithms
1. tilde notation
2. order of growth:
    constant, linear, logarithmic, linearithmic, quadratic, cubic, exponential
3. best case - upper bound on cost
    worst case - lower bound on cost 
