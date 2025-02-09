package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;


public class BestFirstSearch {
    private static MinPQ<searchNode> pq;
    public static int total;


    public static class searchNode {
        WorldState state;
        int movesFromInitial;
        searchNode previous;
        int priority;

        private searchNode(WorldState state, int movesFromInitial, searchNode previous) {
            this.state = state;
            this.movesFromInitial = movesFromInitial;
            this.previous = previous;
            this.priority = this.state.estimatedDistanceToGoal() + this.movesFromInitial;
        }

    }

    public static searchNode BFS(WorldState initial) {

        searchNode initial1 = new searchNode(initial, 0, null);

        // 初始化minPQ
        Comparator<searchNode> comparator = new Comparator<searchNode>() {
            @Override
            public int compare(searchNode o1, searchNode o2) {
                return o1.priority - o2.priority;
            }
        };
        pq = new MinPQ<>(comparator);
        pq.insert(initial1);
        return aStar();
    }

    private static searchNode aStar() {
        System.out.println(1);
        searchNode bms = pq.delMin();
        WorldState F = bms.state;
        if (!F.isGoal()) {
            for (WorldState ws: F.neighbors()) {
                if (bms.previous == null || !ws.equals(bms.previous.state)) {
                    pq.insert(new searchNode(ws, bms.movesFromInitial + 1, bms));
                    total += 1;
                }
            }
            bms = aStar();
        }
        return bms;
    }

    public static int getTotalNodesInserted() {
        return total;
    }


}
