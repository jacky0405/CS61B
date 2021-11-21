package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] grid;
    private int gridLength;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private int top;
    private int bottom;
    private int numberOpen;

    public Percolation(int N) {                       // create N-by-N grid, with all sites initially blocked
        if(N <= 0) {
            throw new IllegalArgumentException("N should be positive");
        }
        gridLength = N;
        grid = new boolean[N * N+2];
        top = 0;
        bottom = N * N + 1;
        grid[top] = true;
        grid[bottom] = true;
        uf = new WeightedQuickUnionUF(N*N + 2);
        uf2 = new WeightedQuickUnionUF(N*N + 1);
        numberOpen = 0;
    }

    private int getSite(int row, int col) {
        return gridLength * row + (col + 1);
    }

    private void Pvalidate(int row, int col) {
        if(row < 0 || row > gridLength-1){
            throw new IndexOutOfBoundsException("Index is over");
        }
        if(col < 0 || col > gridLength-1){
            throw new IndexOutOfBoundsException("Index is over");
        }
    }

    public void open(int row, int col) {             // open the site (row, col) if it is not open already
        Pvalidate(row, col);

        int site = getSite(row, col);
        if(grid[site]){
            return;
        }
        grid[site] = true;
        numberOpen += 1;

        int up, down, left, right;
        if(row == 0) {
            uf.union(top, site);
            uf2.union(top, site);
        } else if(row == gridLength-1) {
            uf.union(bottom, site);
        }
        if(row > 0) {
            up = getSite(row - 1, col);
            if (grid[up]) {
                uf.union(up, site);
                uf2.union(up, site);
            }
        }
        if(row < gridLength-1) {
            down = getSite(row + 1, col);
            if (grid[down]) {
                uf.union(down, site);
                uf2.union(down, site);
            }
        }

        if(col == 0) {
            right = getSite(row, col+1);
            if(grid[right]) {
                uf.union(right, site);
                uf2.union(right, site);
            }
        } else if(col == gridLength-1) {
            left = getSite(row, col-1);
            if(grid[left]) {
                uf.union(left, site);
                uf2.union(left, site);
            }
        } else {
            right = getSite(row, col+1);
            left = getSite(row, col-1);
            if(grid[right]) {
                uf.union(right, site);
                uf2.union(right, site);
            }
            if(grid[left]) {
                uf.union(left, site);
                uf2.union(left, site);
            }
        }

    }
    public boolean isOpen(int row, int col) {        // is the site (row, col) open?
        Pvalidate(row, col);
        return grid[getSite(row, col)];
    }
    public boolean isFull(int row, int col) {        // is the site (row, col) full?
        Pvalidate(row, col);
        return uf2.connected(top, getSite(row, col));
    }
    public int numberOfOpenSites() {                 // number of open sites
        return numberOpen;
    }
    public boolean percolates() {                    // does the system percolate?
        return uf.connected(top, bottom);
    }
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(0, 1);
        percolation.open(1, 1);
        percolation.open(2, 1);

        System.out.println("percolation is " + percolation.percolates());
    }   // use for unit testing (not required)
}
