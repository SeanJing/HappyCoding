import edu.princeton.cs.algs4.*;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by xiaojing on 11/22/17.
 */
public class WordNet {

    private HashMap<String, HashSet<Integer>> map;
    private HashMap<Integer, String> synMap;
    private Digraph G;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("input file names should be non-null");
        map = new HashMap<>();
        synMap = new HashMap<>();
        In in = new In(synsets);
        int n = 0;
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(",");
            int id = Integer.parseInt(a[0]);
            n++;
            synMap.put(id, a[1]);
            String[] nouns = a[1].split(" ");
            for (String noun : nouns) {
                if (map.containsKey(noun)) {
                    map.get(noun).add(id);
                } else {
                    HashSet<Integer> set = new HashSet<>();
                    set.add(id);
                    map.put(noun, set);
                }
            }
        }

        G = new Digraph(n);

        in = new In(hypernyms);
        boolean[] noRoots = new boolean[n];
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(",");
            int v = Integer.parseInt(a[0]);
            for (int i = 1; i < a.length; i++) {
                int w = Integer.parseInt(a[i]);
                G.addEdge(v, w);
                noRoots[v] = true;
            }
        }

        DirectedCycle dc = new DirectedCycle(G);
        if (dc.hasCycle())
            throw new IllegalArgumentException("Digraph is not DAG");

        int rootNum = 0;
        for (boolean b : noRoots)
            if (!b) rootNum++;
        if (rootNum > 1)
            throw new IllegalArgumentException("Digraph has more than one root");

        sap = new SAP(G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        if (map == null) return null;
        return map.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (map == null) return false;
        if (word == null) throw new IllegalArgumentException("word cannot be null");
        return map.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (map == null) return -1;
        if (!map.containsKey(nounA) || !map.containsKey(nounB))
            throw new IllegalArgumentException("noun does not exist in wordnet");
        Iterable<Integer> v = map.get(nounA);
        Iterable<Integer> w = map.get(nounB);
        int d = sap.length(v, w);
        return d;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (map == null) return null;
        if (!map.containsKey(nounA) || !map.containsKey(nounB))
            throw new IllegalArgumentException("noun does not exist in WordNet");
        Iterable<Integer> v = map.get(nounA);
        Iterable<Integer> w = map.get(nounB);
        int ancestor = sap.ancestor(v, w);
        return synMap.get(ancestor);
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet("/test/synsets15.txt", "/test/hypernyms15Tree.txt");
        while (!StdIn.isEmpty()) {
            String v = StdIn.readString();
            String w = StdIn.readString();
            int length = wordnet.distance(v, w);
            String ancestor = wordnet.sap(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
