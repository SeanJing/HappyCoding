import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Created by xiaojing on 12/23/17.
 */
public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int first = 0;
        int n = csa.length();
        char[] t = new char[n];

        for (int i = 0; i < n; i++) {
            int index = csa.index(i);
            t[i] = s.charAt((index + n - 1) % n);
            if (index == 0) first = i;
        }
        BinaryStdOut.write(first);
        for (char c : t)
            BinaryStdOut.write(c);
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        char[] t = s.toCharArray();
        int n = t.length;
        char[] aux = new char[n];
        int[] next = new int[n];
        int[] count = new int[256 + 1];
        for (int i = 0; i < n; i++)
            count[t[i] + 1]++;
        for (int i = 0; i < 256; i++)
            count[i + 1] += count[i];

        for (int i = 0; i < n; i++) {
            next[count[t[i]]] = i;
            aux[count[t[i]]] = t[i];
            count[t[i]]++;
        }

        for (int i = 0; i < n; i++) {
            BinaryStdOut.write(aux[first]);
            first = next[first];
        }

        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
