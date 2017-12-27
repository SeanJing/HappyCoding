import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by xiaojing on 12/9/17.
 */
public class BaseballElimination {

    private int n = 0;
    private String[] teams = null;
    private HashMap<String, Integer> map = null;
    private int[] w = null;
    private int[] l = null;
    private int[] r = null;
    private int[][] g = null;

    private boolean[] isEliminated = null;
    private ArrayList<HashSet<String>> sets = null;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        n = Integer.parseInt(in.readLine());

        teams = new String[n];
        map = new HashMap<>();
        w = new int[n];
        l = new int[n];
        r = new int[n];
        g = new int[n][n];
        isEliminated = new boolean[n];
        sets = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            teams[i] = in.readString();
            map.put(teams[i], i);
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < n; j++)
                g[i][j] = in.readInt();
            sets.add(new HashSet<String>());
        }

        trivialElimination();
        nonTrivialElimination();
        for (int i = 0; i < n; i++) {
            if (!isEliminated[i]) {
                sets.set(i, null);
            }
        }
    }

    private void trivialElimination() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                if (w[i] + r[i] < w[j]) {
                    isEliminated[i] = true;
                    sets.get(i).add(teams[j]);
                }
            }
    }

    private void nonTrivialElimination() {
        int numOfGameV = (n - 1) * (n - 2) / 2;
        int numOfTeamV = n - 1;
        int numberOfV = 2 + numOfGameV + numOfTeamV;
        for (int index = 0; index < n; index++) {
            if (isEliminated[index]) continue;
            int max = w[index] + r[index];
            FlowNetwork flowNetwork = new FlowNetwork(numberOfV);
            int i = 1;
            for (int j = 0; j < n; j++)
                for (int k = j + 1; k < n; k++) {
                    if (j == index || k == index) continue;
                    flowNetwork.addEdge(new FlowEdge(0, i, g[j][k]));
                    flowNetwork.addEdge(new FlowEdge(i, j > index ? j + numOfGameV : j + numOfGameV + 1, Double.POSITIVE_INFINITY));
                    flowNetwork.addEdge(new FlowEdge(i, k > index ? k + numOfGameV : k + numOfGameV + 1, Double.POSITIVE_INFINITY));
                    i++;
                }

            for (i = 0; i < n; i++) {
                if (i == index) continue;
                int vertexIndex = i > index ? i + numOfGameV : i + numOfGameV + 1;
                flowNetwork.addEdge(new FlowEdge(vertexIndex, numberOfV - 1, max - w[i]));
            }

            FordFulkerson ff = new FordFulkerson(flowNetwork, 0, numberOfV - 1);
            for (FlowEdge e : flowNetwork.adj(0)) {
                if (e.flow() != e.capacity()) {
                    isEliminated[index] = true;
                    break;
                }
            }

            if (isEliminated[index]) {
                for (i = 0; i < n; i++) {
                    if (i == index) continue;
                    int vertexIndex = i > index ? i + numOfGameV : i + numOfGameV + 1;
                    if (ff.inCut(vertexIndex))
                        sets.get(index).add(teams[i]);
                }
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return map.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (team == null || !map.containsKey(team)) throw new IllegalArgumentException();
        return w[map.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        if (team == null || !map.containsKey(team)) throw new IllegalArgumentException();
        return l[map.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (team == null || !map.containsKey(team)) throw new IllegalArgumentException();
        return r[map.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (team1 == null || !map.containsKey(team1) || team2 == null || !map.containsKey(team2))
            throw new IllegalArgumentException();
        return g[map.get(team1)][map.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (team == null || !map.containsKey(team)) throw new IllegalArgumentException();
        return isEliminated[map.get(team)];
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (team == null || !map.containsKey(team)) throw new IllegalArgumentException();
        return sets.get(map.get(team));
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
