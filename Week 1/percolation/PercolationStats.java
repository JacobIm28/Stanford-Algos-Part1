/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int t;
    private final double[] percThresh;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.percThresh = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);

            while (!p.percolates()) {
                while (p.isOpen(row, col)) {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                }
                p.open(row, col);
            }

            percThresh[i] = (double) p.numberOfOpenSites() / ((double) (n * n));
        }

        this.t = trials;
    }

    public double mean() {
        return StdStats.mean(percThresh);
    }

    public double stddev() {
        return StdStats.stddev(percThresh);
    }

    public double confidenceLo() {
        return this.mean() - (CONFIDENCE_95 * this.stddev()) / Math.sqrt(t);
    }

    public double confidenceHi() {
        return this.mean() + (CONFIDENCE_95 * this.stddev()) / Math.sqrt(t);
    }

    public static void main(String[] args) {
        int nIn = Integer.parseInt(args[0]);
        int tIn = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(nIn, tIn);

        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
