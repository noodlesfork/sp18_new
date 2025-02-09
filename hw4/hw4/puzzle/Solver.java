package hw4.puzzle;
import java.util.ArrayList;
import java.util.Collections;


public class Solver {
    private BestFirstSearch.SearchNode bms;

    public Solver(WorldState initial) {
        bms = BestFirstSearch.bfs(initial);
    }

    public int moves() {
        return bms.movesFromInitial;
    }

    public Iterable<WorldState> solution() {
        ArrayList<WorldState> S = new ArrayList<>();
        BestFirstSearch.SearchNode temp = bms;
        while (temp.previous != null) {
            S.add(temp.state);
            temp = temp.previous;
        }
        S.add(temp.state);
        Collections.reverse(S);
        return S;
    }

}

