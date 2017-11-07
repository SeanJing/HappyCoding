import java.util.Stack;

public class Board {
    private final int[][] tiles;

    public Board(int[][] blocks) {
        if (blocks == null) throw new NullPointerException("argument should not be null");
        tiles = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
                tiles[i][j] = blocks[i][j];
    }

    public int dimension() {
        return tiles.length;
    }

    public int hamming() {
        int n = dimension();
        int hamming = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] > 0 && tiles[i][j] != n * i + j + 1) {
                    hamming++;
                }
            }
        return hamming;
    }

    public int manhattan() {
        int n = dimension();
        int manhattan = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] > 0)
                    manhattan += Math.abs((tiles[i][j] - 1) / n - i) + Math.abs((tiles[i][j] - 1) % n - j);
            }
        return manhattan;
    }

    public boolean isGoal() {
        int n = dimension();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] > 0 && tiles[i][j] != n * i + j + 1) {
                    return false;
                }
            }
        return true;
    }

    public Board twin() {
        Board twin = new Board(tiles);
        int[][] twinTiles = twin.tiles;
        int temp = 0;
        if (twinTiles[0][0] != 0 && twinTiles[0][1] != 0) {
            temp = twinTiles[0][0];
            twinTiles[0][0] = twinTiles[0][1];
            twinTiles[0][1] = temp;
        } else {
            temp = twinTiles[1][0];
            twinTiles[1][0] = twinTiles[1][1];
            twinTiles[1][1] = temp;
        }
        return twin;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < this.dimension(); i++)
            for (int j = 0; j < this.dimension(); j++)
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int x = 0, y = 0;
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++)
                if (tiles[i][j] == 0) {
                    x = i;
                    y = j;
                    break;
                }
        int[][] newTiles = null;
        Board neighbor = null;
        if (x > 0) {
            neighbor = new Board(tiles);
            newTiles = neighbor.tiles;
            newTiles[x][y] = newTiles[x - 1][y];
            newTiles[x - 1][y] = 0;
            neighbors.push(neighbor);
        }
        if (y > 0) {
            neighbor = new Board(tiles);
            newTiles = neighbor.tiles;
            newTiles[x][y] = newTiles[x][y - 1];
            newTiles[x][y - 1] = 0;
            neighbors.push(neighbor);
        }
        if (x < dimension() - 1) {
            neighbor = new Board(tiles);
            newTiles = neighbor.tiles;
            newTiles[x][y] = newTiles[x + 1][y];
            newTiles[x + 1][y] = 0;
            neighbors.push(neighbor);
        }
        if (y < dimension() - 1) {
            neighbor = new Board(tiles);
            newTiles = neighbor.tiles;
            newTiles[x][y] = newTiles[x][y + 1];
            newTiles[x][y + 1] = 0;
            neighbors.push(neighbor);
        }
        return neighbors;
    }


    public String toString() {
        int n = dimension();
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
    }

}