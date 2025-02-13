package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private int s = 0;
    private int[] tempEdgeTo;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        distTo[s] = 0;
        tempEdgeTo = new int[maze.V()];
        tempEdgeTo[s] = s;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        findCycle(s);
    }

    // Helper methods go here
    private void findCycle(int v) {
        marked[v] = true;
        announce();

        for (int m: maze.adj(v)) {
            if (!marked[m]) {
                distTo[m] = distTo[v] + 1;
                tempEdgeTo[m] = v;
                findCycle(m);
            } else if (distTo[m] >= distTo[v]){
                drawCycle(v, m);
                return;
            }
        }
    }

    private void drawCycle(int v1, int v2) {
        int deeper;
        int shallower;

        if (distTo[v1] >= distTo[v2]) {
            deeper = v1;
            shallower = v2;
        } else {
            deeper = v2;
            shallower = v1;
        }

        edgeTo[shallower] = deeper;

        while (distTo[deeper] > distTo[shallower]) {
            edgeTo[deeper] = tempEdgeTo[deeper];
            deeper = edgeTo[deeper];
        }

        while (tempEdgeTo[deeper] != tempEdgeTo[shallower]) {
            edgeTo[deeper] = tempEdgeTo[deeper];
            deeper = edgeTo[deeper];
            edgeTo[tempEdgeTo[shallower]] = shallower;
            shallower = tempEdgeTo[shallower];
        }

        edgeTo[deeper] = tempEdgeTo[deeper];
        edgeTo[tempEdgeTo[shallower]] = shallower;
        announce();

    }
}

