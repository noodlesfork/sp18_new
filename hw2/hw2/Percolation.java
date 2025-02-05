package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public WeightedQuickUnionUF unionFind;
    public boolean[][] grid;
    public int N;
    public int openSitesAmount = 0;


    public Percolation(int N) { // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("gird size should be larger than 0");
        }
        this.N = N;
        unionFind = new WeightedQuickUnionUF(N * N + 2);
        grid = new boolean[N][N];

        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                grid[i][j] = false;
            }
        }
        for (int i = 0; i < N; i += 1) {
            unionFind.union(N * N , getSerialNumber(0, i));
            unionFind.union(N * N + 1, getSerialNumber(N - 1, i));
        }
    }

    public int getSerialNumber(int row, int col) {
        return row * N + col;
    }

    public void connectHelper(int rowChange, int colChange, int row, int col) {
        if (row + rowChange < N && row + rowChange >= 0 && col + colChange < N && col + colChange >= 0) {
            if (isOpen(row + rowChange, col + colChange)) {
                unionFind.union(getSerialNumber(row, col), getSerialNumber(row + rowChange, col + colChange));
            }
        }

    }

    public void open(int row, int col) { // open the site (row, col) if it is not open already
        if (row >= N || col >= N || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("out of bound");
        }

        if (!grid[row][col]) {
            grid[row][col] = true;
            openSitesAmount += 1;
            connectHelper(0, 1, row, col);
            connectHelper(1, 0, row, col);
            connectHelper(0, -1, row, col);
            connectHelper(-1, 0, row, col);
        }

    }

    public boolean isOpen(int row, int col) { // is the site (row, col) open?
        if (row >= N || col >= N || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("out of bound");
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col) { // is the site (row, col) full?
        if (row >= N || col >= N || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("out of bound");
        }
        if (isOpen(row, col)) {
            if (unionFind.connected(getSerialNumber(row, col), N * N)) {
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() { // number of open sites
        return openSitesAmount;
    }

    public boolean percolates()     { // does the system percolate?
        if (unionFind.connected(N * N, N * N + 1)) {
            return true;
        }
        return false;
    }

}
