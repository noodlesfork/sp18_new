package hw2;

import java.util.Random;


public class PercolationStats {
    private int[] X; //用于储存试验中的open grid数量
    private Random r = new Random();
    private int testAmount;

    public PercolationStats(int N, int T, PercolationFactory pf) { // perform T independent experiments on an N-by-N grid
        if (N <= 0) {
            throw new IllegalArgumentException("grid size should be larger than 0");
        }

        if (T <= 0) {
            throw new IllegalArgumentException("Experiment amount should be larger than 0");
        }

        X = new int[T];
        testAmount = T;
        for (int i = 0; i < testAmount; i += 1) {
            Percolation P = pf.make(N);
            while (!P.percolates()) {
                int serial = r.nextInt(N * N);
                P.open(Math.floorDiv(serial, N), serial % N);
            }
            X[i] = P.numberOfOpenSites();
        }

    }
    public double mean() { // sample mean of percolation threshold
        double temp = 0;
        for (int x: X) {
            temp += x;
        }
        return temp / testAmount;
    }
    public double stddev() { // sample standard deviation of percolation threshold
        double miu = mean();
        double temp = 0;
        for (int x: X) {
            temp += (x - miu) * (x - miu);
        }
        return Math.sqrt(temp / (testAmount - 1));
    }
    public double confidenceLow() { // low endpoint of 95% confidence interval
        double miu = mean();
        double stdd = stddev();
        return miu - 1.96 * stdd / Math.sqrt(testAmount);
    }
    public double confidenceHigh() { // high endpoint of 95% confidence interval
        double miu = mean();
        double stdd = stddev();
        return miu + 1.96 * stdd / Math.sqrt(testAmount);
    }


}
