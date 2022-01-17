package home;
import java.util.Comparator;


public class Kruskal {

    public static void main(String[] args) {
        int V = 4; // Number of vertices in graph
        int E = 5; // Number of edges in graph
        Graph graph = new Graph(V, E);

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

        System.out.println(graph.mst());

    }

}

class Graph{
    private final int  v;
    private final int e;
    private PriorityQueue<Edge> pq;
    Graph(int v,int e){
        this.v = v;
        this.e = e;
        pq = new PriorityQueue<>(e,new CustomComparator());
    }

    public void addEdge(int src,int dest,int wt){
        pq.add(new Edge(src,dest,wt));
    }

    private int find(DisjointSet set[], int i) {

        // find root and make root as parent of i (path compression)
        if (set[i].parent != i)
            set[i].parent = find(set, set[i].parent);

        return set[i].parent;
    }

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

        DisjointSet set[] = new DisjointSet[v];
        for(int i=0; i<v; ++i){
            set[i]=new DisjointSet();
        }

        for (int i = 0; i < v; ++i) {
            set[i].parent = i;
            set[i].rank = 0;
        }

        int edges_picked = 0;
        int wt_mst = 0;

        while(edges_picked < v-1 ){

            Edge next_edge = pq.poll();

            int x = find(set, next_edge.src);
            int y = find(set, next_edge.dest);

            // If including this edge doesn't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y)
            {
                union(set, x, y);
                wt_mst+=next_edge.weight;
                edges_picked++;
            }
            // Else discard the next_edge
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

class CustomComparator implements Comparator<Edge> {

    public int compare(Edge e1,Edge e2)
    {
        if(e1.weight < e2.weight)return -1;
        else if(e1.weight > e2.weight)return 1;
        else return 0;
    }
}






