import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Node goal = null;

    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException("argument should not be null");
        Node init = new Node(initial);
        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> twinPQ = new MinPQ<>();
        pq.insert(init);
        init = new Node(initial.twin());
        twinPQ.insert(init);
        Node min = pq.delMin();
        Node twinMin = twinPQ.delMin();
        while (!min.board.isGoal() && !twinMin.board.isGoal()) {
            for (Board neighbor : min.board.neighbors()) {
                if (min.prev == null || !neighbor.equals(min.prev.board)) {
                    Node node = new Node(neighbor);
                    node.prev = min;
                    node.moves = min.moves + 1;
                    pq.insert(node);
                }
            }
            for (Board neighbor : twinMin.board.neighbors()) {
                if (twinMin.prev == null || !neighbor.equals(twinMin.prev.board)) {
                    Node node = new Node(neighbor);
                    node.prev = twinMin;
                    node.moves = twinMin.moves + 1;
                    twinPQ.insert(node);
                }
            }
            min = pq.delMin();
            twinMin = twinPQ.delMin();
        }
        if (min.board.isGoal()) goal = min;
        else if (twinMin.board.isGoal()) {
            goal = twinMin;
            goal.moves = -1;
        }
    }

    public boolean isSolvable() {
        return moves() >= 0;
    }

    public int moves() {
        return goal.moves;
    }

    public Iterable<Board> solution() {
        Stack<Board> solutions = new Stack<>();
        Node node = goal;
        while (node != null) {
            solutions.push(node.board);
            node = node.prev;
        }
        return solutions;
    }

    private static class Node implements Comparable<Node> {
        private Board board = null;
        private int moves = 0;
        private Node prev = null;

        public Node(Board board) {
            this.board = board;
        }

        @Override
        public int compareTo(Node that) {
            int thisPriority = this.board.manhattan() + this.moves;
            int thatPriority = that.board.manhattan() + that.moves;
            if (thisPriority > thatPriority) return +1;
            else if (thisPriority < thatPriority) return -1;
            else return 0;
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board.toString());
        }
    }
}