package hw4.puzzle;
import java.util.ArrayList;
import hw4.puzzle.BestFirstSearch.*;
import java.util.Collections;


public class Solver {
    private searchNode bms;
    public int total;

    public Solver(WorldState initial) {
        bms = BestFirstSearch.BFS(initial);
        total = BestFirstSearch.getTotalNodesInserted();
    }

    public int moves() {
        return bms.movesFromInitial;
    }

    public Iterable<WorldState> solution() {
        ArrayList<WorldState> S= new ArrayList<>();
        searchNode temp = bms;
        while(temp.previous != null) {
            S.add(temp.state);
            temp = temp.previous;
        }
        S.add(temp.state);
        Collections.reverse(S);
        return S;
    }

}

