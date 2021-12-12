package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
        Queue<Integer> q = new ArrayDeque<Integer>();
        marked[v] = true;
        announce();
        if (v == t) {
            targetFound = true;
        }
        if (targetFound) {
            return;
        }
        q.add(v);

        while (!q.isEmpty()) {
            int s = q.poll();
            for (int w : maze.adj(s)) {
                if (!marked[w]) {
                    edgeTo[w] = s;
                    announce();
                    distTo[w] = distTo[s] + 1;
                    marked[w] = true;
                    announce();
                    if (w == t) {
                        targetFound = true;
                    }
                    if (targetFound) {
                        return;
                    }
                    q.add(w);
                }
            }
        }

    }


    @Override
    public void solve() {
        bfs(s);
    }
}

