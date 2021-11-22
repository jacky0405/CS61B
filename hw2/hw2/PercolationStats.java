package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private int count;
    private double[] experiments;
    private int T;

    public PercolationStats(int N, int T, PercolationFactory pf) {  // perform T independent experiments on an N-by-N grid
        if(N<=0 || T<=0) {
            throw new IllegalArgumentException("Must be positive");
        }
        this.T = T;

        experiments = new double[T];
        for(int i=0; i<T; i++){
            Percolation p =  pf.make(N);

            while(!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                p.open(row, col);
            }
            experiments[i] = (double)p.numberOfOpenSites() / (double)(N*N);
        }

    }

    public double mean() {  // sample mean of percolation threshold
        double sum = 0;
        for(double i : experiments){
            sum += i;
        }
        return sum / (double)T;
    }
    public double stddev() {    // sample standard deviation of percolation threshold
        return StdStats.stddev(experiments);
    }
    public double confidenceLow() {  // low endpoint of 95% confidence interval
        return mean() - 1.96*stddev() / Math.sqrt((double) T);
    }
    public double confidenceHigh() {  // high endpoint of 95% confidence interval
        return mean() + 1.96*stddev() / Math.sqrt((double) T);
    }

    public static void main(String[] args) {
        PercolationStats a = new PercolationStats(20, 100, new PercolationFactory());
        System.out.println(a.mean());

    }
}


