package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;


public class BestFirstSearch {
    private static MinPQ<SearchNode> pq;
    private static SearchNode finalNode;

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
        aStar();
        return finalNode;
    }

    private static void aStar() {

        while (!pq.isEmpty()) {
            SearchNode bms = pq.delMin();
            if (bms.state.isGoal()) {
                finalNode = bms;
                return;
            }
            // 处理邻居节点
            for (WorldState neighbor : bms.state.neighbors()) {
                // 避免回到前驱状态
                if (bms.previous == null || !neighbor.equals(bms.previous.state)) {
                    pq.insert(new SearchNode(neighbor, bms.movesFromInitial + 1, bms));
                }
            }
        }

    }
}
