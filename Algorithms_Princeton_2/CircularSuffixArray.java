import java.util.Arrays;

/**
 * Created by xiaojing on 12/23/17.
 */
public class CircularSuffixArray {

    private String s;
    private Suffix[] suffixes;

    private static class Suffix implements Comparable<Suffix> {
        private int index;
        private String s;

        public Suffix(String s, int index) {
            this.s = s;
            this.index = index;
        }

        private char charAt(int i) {
            return s.charAt((index + i) % s.length());
        }

        public int compareTo(Suffix that) {
            if (this == that) return 0;  // optimization
            for (int i = 0; i < s.length(); i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                if (this.charAt(i) > that.charAt(i)) return +1;
            }
            return 0;
        }
    }

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("argument cannot be null");
        int n = s.length();
        this.suffixes = new Suffix[n];
        for (int i = 0; i < n; i++)
            suffixes[i] = new Suffix(s, i);
        Arrays.sort(suffixes);
    }

    // length of s
    public int length() {
        return suffixes.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length()) throw new IllegalArgumentException("argument out of range");
        return suffixes[i].index;
    }

    // unit testing (required)
    public static void main(String[] args) {
    }
}
