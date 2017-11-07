import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] mean;
    private int size;
    private int trials;

    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) throw new IllegalArgumentException("argument should be greater than 0");
        size = n;
        trials = t;
        mean = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(size);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, size + 1);
                int col = StdRandom.uniform(1, size + 1);
                if (!perc.isOpen(row, col)) perc.open(row, col);
            }
            mean[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(mean);
    }

    public double stddev() {
        if (trials == 1) return Double.NaN;
        return StdStats.stddev(mean);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        if (args.length != 2) return;
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);

        StdOut.printf("mean\t\t\t\t= %.10f%n", stats.mean());
        StdOut.printf("stddev\t\t\t\t= %.10f%n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%.10f %.10f]", stats.confidenceLo(), stats.confidenceHi());
    }
}