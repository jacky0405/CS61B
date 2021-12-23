package hw4.puzzle;

import  edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Solver {
    private class SearchNode {
        private WorldState word;
        private int move;
        private SearchNode prev;

        public SearchNode(WorldState word, int moves, SearchNode prev) {
            this.word = word;
            this.move = moves;
            this.prev = prev;
        }
    }

    private class NodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int estDist1 = o1.word.estimatedDistanceToGoal();
            int estDist2 = o2.word.estimatedDistanceToGoal();
            int o1Priority = o1.move + estDist1;
            int o2Priority = o2.move + estDist2;
            return o1Priority - o2Priority;
        }
    }

    private MinPQ<SearchNode> MP;
    private List<WorldState> solutions = new ArrayList<>();

    public Solver(WorldState initial) {
        MP = new MinPQ<>(new NodeComparator());
        SearchNode currentNode = new SearchNode(initial, 0, null);
        MP.insert(currentNode);
        while(!MP.isEmpty()) {
            currentNode = MP.delMin();
            if (currentNode.word.isGoal()) {
                break;
            }
            for (WorldState w : currentNode.word.neighbors()) {
                SearchNode newNode = new SearchNode(w, currentNode.move + 1, currentNode);
                if (currentNode.prev != null && w.equals(currentNode.prev.word)) {
                    continue;
                }
                MP.insert(newNode);
            }
        }

        Stack<WorldState> path = new Stack<>();
        for (SearchNode n = currentNode; n != null; n=n.prev) {
            path.push(n.word);
        }
        while (!path.isEmpty()) {
            this.solutions.add(path.pop());
        }
    }

    public int moves() {
        return this.solutions.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return this.solutions;
    }
}
