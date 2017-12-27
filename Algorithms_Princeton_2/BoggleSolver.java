import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by xiaojing on 12/16/17.
 */
public class BoggleSolver {
    private SET<String> words;
    private static final int R = 26;
    private Node root; // root of trie
    private int n;

    // R-way trie node
    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }

    private void add(String key) {
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (!x.isString) n++;
            x.isString = true;
        } else {
            char c = key.charAt(d);
            x.next[c - 'A'] = add(x.next[c - 'A'], key, d + 1);
        }
        return x;
    }

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String word : dictionary)
            add(word);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        words = new SET<>();
        int m = board.rows();
        int n = board.cols();
        // enumerate all strings
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                boolean[][] marked = new boolean[m][n];
                bfs(board, i, j, marked, "", words, root);
            }
        return words;
    }

    private void bfs(BoggleBoard board, int row, int col, boolean[][] marked, String prefix, SET<String> words, Node x) {
        if (marked[row][col]) return;
        char c = board.getLetter(row, col);
        Node next = x.next[c - 'A'];
        if (next == null) return;
        String word = prefix;

        if (c == 'Q') {
            word += "QU";
            next = next.next['U' - 'A'];
            if (next == null) return;
        } else {
            word += c;
        }

        if (word.length() > 2 && next.isString) {
            words.add(word);
        }

        marked[row][col] = true;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                if ((row + i >= 0) && (row + i < board.rows()) && (col + j >= 0) && (col + j < board.cols())) {
                    bfs(board, row + i, col + j, marked, word, words, next);
                }
            }
        }

        marked[row][col] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (words.contains(word)) {
            switch (word.length()) {
                case 0:
                case 1:
                case 2:
                    return 0;
                case 3:
                case 4:
                    return 1;
                case 5:
                    return 2;
                case 6:
                    return 3;
                case 7:
                    return 5;
                default:
                    return 11;
            }
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
