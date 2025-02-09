package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;


public class BestFirstSearch {
    private static MinPQ<SearchNode> pq;

    public static class SearchNode {
        WorldState state;
        int movesFromInitial;
        SearchNode previous;
        int priority;

        private SearchNode(WorldState state, int movesFromInitial, SearchNode previous) {
            this.state = state;
            this.movesFromInitial = movesFromInitial;
            this.previous = previous;
            this.priority = this.state.estimatedDistanceToGoal() + this.movesFromInitial;
        }

    }

    public static SearchNode bfs(WorldState initial) {

        SearchNode initial1 = new SearchNode(initial, 0, null);

        // 初始化minPQ
        Comparator<SearchNode> comparator = new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return o1.priority - o2.priority;
            }
        };
        pq = new MinPQ<>(comparator);
        pq.insert(initial1);
        return aStar();
    }

    private static SearchNode aStar() {
        SearchNode bms = pq.delMin();
        WorldState F = bms.state;
        if (!F.isGoal()) {
            for (WorldState ws: F.neighbors()) {
                if (bms.previous == null || !ws.equals(bms.previous.state)) {
                    pq.insert(new SearchNode(ws, bms.movesFromInitial + 1, bms));
                }
            }
            bms = aStar();
        }
        return bms;
    }
}
