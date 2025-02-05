package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF topUnionFind;
    private WeightedQuickUnionUF downUnionFind;
    private boolean[][] grid;
    private int N;
    private int openSitesAmount = 0;
    private boolean isPercolate = false;


    public Percolation(int N) { // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("gird size should be larger than 0");
        }
        this.N = N;

        // 初始化unionFind
        topUnionFind = new WeightedQuickUnionUF(N * N + 1);
        downUnionFind = new WeightedQuickUnionUF(N * N + 1);
        for (int i = 0; i < N; i += 1) {
            topUnionFind.union(N * N , getSerialNumber(0, i));
            downUnionFind.union(N * N, getSerialNumber(N - 1, i));
        }

        // 初始化grid
        grid = new boolean[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                grid[i][j] = false;
            }
        }


    }

    private int getSerialNumber(int row, int col) {
        return row * N + col;
    }

    private void connectHelper(int rowChange, int colChange, int row, int col, WeightedQuickUnionUF unionFind) {
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

        // Union
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSitesAmount += 1;
            connectHelper(0, 1, row, col, topUnionFind);
            connectHelper(1, 0, row, col, topUnionFind);
            connectHelper(0, -1, row, col, topUnionFind);
            connectHelper(-1, 0, row, col, topUnionFind);

            connectHelper(0, 1, row, col, downUnionFind);
            connectHelper(1, 0, row, col, downUnionFind);
            connectHelper(0, -1, row, col, downUnionFind);
            connectHelper(-1, 0, row, col, downUnionFind);
        }

        //检查是否联通
        if (topUnionFind.connected(N * N, getSerialNumber(row, col)) && downUnionFind.connected(N * N, getSerialNumber(row, col))) {
            isPercolate = true;
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
            if (topUnionFind.connected(getSerialNumber(row, col), N * N)) {
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() { // number of open sites
        return openSitesAmount;
    }

    public boolean percolates()     { // does the system percolate?
        return isPercolate;
    }

    public static void main(String[] args){

    }

}
