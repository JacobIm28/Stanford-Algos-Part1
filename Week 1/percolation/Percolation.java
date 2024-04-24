/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] open; // 0 is blocked, 1 is open
    private final int size;
    private int numOpen;
    private final WeightedQuickUnionUF uf;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        uf = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n; i++) {
            uf.union(n * n + 1, i);
            uf.union(n * n, (n - 1) * n + i);
        }

        this.open = new boolean[n][n];
        size = n;
        this.numOpen = 0;
    }

    public void open(int row, int col) {
        isValid(row, col);

        if (!this.open[row - 1][col - 1]) {
            this.numOpen += 1;
        }
        this.open[row - 1][col - 1] = true;

        if (col < this.size && this.open[row - 1][col]) {
            uf.union((row - 1) * size + col - 1, (row - 1) * size + col);
        }
        if (col > 1 && this.open[row - 1][col - 2]) {
            uf.union((row - 1) * size + col - 1, (row - 1) * size + col - 2);
        }
        if (row < this.size && this.open[row][col - 1]) {
            uf.union((row - 1) * size + col - 1, row * size + col - 1);
        }
        if (row > 1 && this.open[row - 2][col - 1]) {
            uf.union((row - 1) * size + col - 1, (row - 2) * size + col - 1);
        }
    }

    public boolean isOpen(int row, int col) {
        isValid(row, col);

        return this.open[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        isValid(row, col);

        return this.open[row - 1][col - 1] && this.uf.find(this.size * this.size + 1) == this.uf.find((row - 1) * this.size + col - 1);
    }

    public int numberOfOpenSites() {
        return this.numOpen;
    }

    public boolean percolates() {
        return this.numOpen > 0 && this.uf.find(this.size * this.size + 1) == this.uf.find(this.size * this.size);
    }

    private void isValid(int row, int col) {
        if (col < 1 || col > this.size || row < 1 || row > this.size) {
            throw new IllegalArgumentException();
        }
    }
}
