package home;
import java.util.Comparator;

/*
* Union(A, B): This operation tells to merge the sets containing elements A and B respectively by performing a Union operation on the sets.
* Find(A): This operation tells to find the subset to which the element A belongs.

* Initially, all elements are single element subsets.
  The idea is to always attach smaller depth tree under the root of the deeper tree
    0 1 2

    Do Union By rank(0, 1)
       1   2
      /
     0

    Do Union By rank (1, 2)
       1
     /  \
    0    2


* The idea of path compression
  is to make the found root as parent of x so that we don't have to traverse all intermediate nodes again

*  A minimum spanning tree has (V – 1) edges (graph with V vertices) and it contains no cycle.

* Algorithm: The Kruskal's algorithm for finding MST works on a Greedy method:
  1. Add All edges into Priority Queue i.e. Min Heap
  2. Pick the smallest edge. Check if it forms a cycle with the spanning tree formed so far. If cycle is not formed, include this edge, else discard it.
  3. Repeat step 2 until there are (V-1) edges in the spanning tree.

* Time complexity
  Adding edge in priority queue - E log E
  Initialize parent and rank array - V
  Union and Find Operation - E alpha V
* */

public class Kruskal {

    public static void main(String[] args) {
        int V = 4; // Number of vertices in graph
        int E = 5; // Number of edges in graph
        Graph graph = new Graph(V, E);

        /* Let us create following weighted graph
            9
        0---------1
        |  \      |
       5|   10\   |8
        |       \ |
        2---------3
            7     */


        // add edge 0-1
        graph.addEdge(0,1,9);

        // add edge 0-2
        graph.addEdge(0,2,5);

        // add edge 0-3
        graph.addEdge(0,3,10);

        //add edge 1-3
        graph.addEdge(1,3,8);

        // add edge 2-3
        graph.addEdge(2,3,7);

        System.out.println("Weight of mst "+ graph.mst());

    }

}

class Graph{
    private final int  V;
    private final int E;
    private PriorityQueue<Edge> pq;
    Graph(int v,int e){
        V = v;
        E = e;
        pq = new PriorityQueue<>(e,new EdgeComparator());
    }

    public void addEdge(int src,int dest,int wt){
        pq.add(new Edge(src,dest,wt));
    }

    // A utility function to find the subset of an element i.
    private int find(DisjointSet set[], int i) {

        // find root and make root as parent of i (path compression)
        if (set[i].parent != i)
            set[i].parent = find(set, set[i].parent);

        return set[i].parent;
    }

    //  A utility function to do union of two subsets
    private void union(DisjointSet set[], int x, int y) {
        int rootX = find(set, x);
        int rootY = find(set, y);

        if (set[rootX].rank < set[rootY].rank)
            set[rootX].parent = rootY;
        else if (set[rootX].rank > set[rootY].rank)
            set[rootY].parent = rootX;
        else
        {
            set[rootY].parent = rootX;
            set[rootX].rank++;
        }
    }

    public int mst(){

        DisjointSet set[] = new DisjointSet[V];
        Edge output[] = new Edge[V-1];

        for(int i=0; i<V; ++i){
            set[i] = new DisjointSet();
        }

        for (int i = 0; i <V; ++i) {
            set[i].parent = i;
            set[i].rank = 0;
        }

        int edges_picked = 0;
        int wt_mst = 0;

        while(edges_picked < V-1 ){

            Edge next_edge = pq.poll();

            int x = find(set, next_edge.src);
            int y = find(set, next_edge.dest);

            // If including this edge doesn't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y)
            {
                union(set, x, y);
                wt_mst += next_edge.weight;
                output[edges_picked] = next_edge;
                edges_picked++;
            }
            // Else discard the next_edge
        }
        for(int i=0;i<edges_picked;i++){
            System.out.println(output[i].src+"----"+output[i].dest+"( "+output[i].weight+" )");
        }
        return wt_mst;
    }

}

class DisjointSet{
    int parent;
    int rank;
}

class Edge {
    int src, dest, weight;
    Edge(int s,int d,int w){
        src = s;
        dest = d;
        weight = w;
    }
}

class EdgeComparator implements Comparator<Edge> {

    public int compare(Edge e1,Edge e2)
    {
        if(e1.weight < e2.weight)return -1;
        else if(e1.weight > e2.weight)return 1;
        else return 0;
    }
}






