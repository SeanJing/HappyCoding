import edu.princeton.cs.algs4.WeightedQuickUnionUF;

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
