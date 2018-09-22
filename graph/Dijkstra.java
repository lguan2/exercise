import java.util.*;

public class Dijkstra {

    public static void main(String[] args) {
        int[][] edges = {{0,1,2}, {1,2,1}, {0,2,6}, {1,0,2}};
        System.out.println(getShortestPath(edges, 0, 2));

    }

    // Time: O(E) + O(ElogE) -> O(E)
    // Space: O(E)
    // 不能用visited set
    public static int getShortestPath(int[][] edges, int src, int dest){
        Map<Integer, Set<int[]>> graph = new HashMap<>();
        for(int[] e : edges){
            if(!graph.containsKey(e[0]))
                graph.put(e[0], new HashSet<>());
            graph.get(e[0]).add(new int[]{e[1], e[2]});
        }
        if(!graph.containsKey(src))
            return -1;
        if(src == dest)
            return 0;
        // 0: cost, 1: position
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
        heap.add(new int[]{0, src});


        while(!heap.isEmpty()){
            int[] cur = heap.poll();
            if(cur[1] == dest)
                return cur[0];
            for(int[] child : graph.get(cur[1])) {
                heap.add(new int[]{cur[0] + child[1], child[0]});
            }
        }
        return -1;
    }
}
