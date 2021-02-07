import java.util.Arrays;

public class ShortestPaths {

    private Graph graph;
    private int nodesNumber;
    private int[][] neighboursDistance;

    public ShortestPaths(Graph graph){
        this.graph = graph;
        this.nodesNumber = graph.getNodesNumber();
        this.neighboursDistance = neighboursDistance();
    }

    public void floydWarshall(){
        for (int k = 0; k < nodesNumber; k++) {
            for (int u = 0; u < nodesNumber; u++) {
                for (int v = 0; v < nodesNumber; v++) {
                    if(u==v) continue;
                    neighboursDistance[u][v] = Math.min(neighboursDistance[u][v],
                                                        neighboursDistance[u][k] + neighboursDistance[k][v]);
                }
            }
        }
      //  System.out.println(Arrays.deepToString(neighboursDistance)); print distances after Floyd-Warshall
    }

    private int[][] neighboursDistance(){
        int[][] distances = new int[nodesNumber][nodesNumber];
        Arrays.stream(distances).forEach(a -> Arrays.fill(a, Integer.MAX_VALUE/2));
        for (int i = 0; i < graph.getNeighbours().size(); i++) {
            for (int j = 0; j < graph.getNeighbours().get(i).size(); j++) {
                Arc arc = graph.getNeighbours().get(i).get(j);
                distances[i][arc.getNode()] = arc.getWeight();
            }
        }
        return distances;
    }
}
