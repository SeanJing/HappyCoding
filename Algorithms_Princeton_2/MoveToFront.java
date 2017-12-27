import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Created by xiaojing on 12/23/17.
 */
public class MoveToFront {
    private static int R = 256;

    public static void encode() {
        String s = BinaryStdIn.readString();
        char[] in = s.toCharArray();
        char[] mtf = new char[R];
        for (char i = 0; i < R; i++)
            mtf[i] = i;
        for (int i = 0; i < in.length; i++) {
            char c = 0;
            for (char k = 0; k < 256; k++) {
                char tmp = mtf[k];
                mtf[k] = c;
                c = tmp;
                if (c == in[i]) {
                    mtf[0] = c;
                    BinaryStdOut.write(k);
                    break;
                }
            }
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        String s = BinaryStdIn.readString();
        char[] in = s.toCharArray();
        char[] mtf = new char[R];
        for (char i = 0; i < R; i++)
            mtf[i] = i;
        for (int i = 0; i < in.length; i++) {
            char c = 0;
            for (char k = 0; k < 256; k++) {
                char tmp = mtf[k];
                mtf[k] = c;
                c = tmp;
                if (k == in[i]) {
                    mtf[0] = c;
                    BinaryStdOut.write(c);
                    break;
                }
            }
        }
        BinaryStdOut.flush();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
