package lab11.graphs;

import java.util.ArrayList;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;


    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);

        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        marked[s] = true;
        announce();

        if (s == t) {
            targetFound = true;
            return;
        }

        int vToVisit = 0;
        int nearestDist = 10086;

        for (int m: maze.adj(s)) {
            if (!marked[m]) {
                int dist = distTo[s] + 1 + h(m);
                if (dist < nearestDist) {
                    nearestDist = dist;
                    vToVisit = m;
                }
            }
        }

        distTo[vToVisit] = distTo[s] + 1;
        edgeTo[vToVisit] = s;
        astar(vToVisit);
    }

    @Override
    public void solve() {
        astar(s);
    }

}

