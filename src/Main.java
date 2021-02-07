public class Main {

    public static void main(String[] args) {
        long start = System.nanoTime();
        Graph graph = new Graph(4, 60, 1,10);
        long end = System.nanoTime();
        System.out.println("Time: "+(end-start)/1000000 + " ms");

        System.out.println(graph.toString());
        ShortestPath shortestPath = new ShortestPath(graph);
        shortestPath.dijkstra(0, true);
        System.out.println("Sequential is more efficient than PriorityQueue at density: "+ sequentialVsPriority());

        ShortestPaths shortestPaths = new ShortestPaths(graph);
        shortestPaths.floydWarshall();
        System.out.println("Floyd-Warshall is more efficient than Dijkstra at density: "+ floydVsDijkstra());
    }

    /*
     * Si on compare théoriquement les deux implémentations de l'algorithme de Dijkstra avec leur complexité:
     *      PriorityQueue < Sequential
     *      m*log(n) < n² + m <=> n(n-1)*(c/100)*log(n) < n² + (n(n-1)*c/100) <=> ..... <=> Puis on remplace n par 100 et
     *      on isole c, on obtient c < 101,01
     *      Donc théoriquement cette équation est toujours vérfiée donc PriorityQueue est toujours plus performant que Dijsktra
     *
     *      Cependant expérimentalement, la recherche séquentielle est plus perfomante que le tas binaire notamment car on
     *      doit rajouter les ArcPriority à la PriorityQueue.
     */
    public static int sequentialVsPriority(){
        int count = 0;
        for (int i = 0; i < 101; i++) {
            Graph graph = new Graph(100, i, 1, 10);
            ShortestPath shortestPath = new ShortestPath(graph);
            long startSequential = System.nanoTime();
            shortestPath.dijkstra(0, false);
            long endSequential = System.nanoTime();
            long startPriority = System.nanoTime();
            shortestPath.dijkstra(0, true);
            long endPriority = System.nanoTime();
            if((endSequential - startSequential) < (endPriority - startPriority)) count++;
            else count = 0;
            if(count == 2) return i;
        }
        return 101;
    }

    /*
     * Non Floyd-Warshall possède une complexité en O(n^3), il ne dépend pas de la densité du graphe donc on peut
     * étudier ces deux algorithmes seulement avec la complexité, il faut aussi prendre en compte le nombre de sommets de
     * notre graphe. Pour cela on peut résoudre, à l'aide des complexités, l'équation:
     *
     * (tout les sommets)*Dijkstra       Floyd-Warshall
     *      n * m * log n           <=      n^3
     *
     * Expérimentalement, pour 40 noeuds Dijkstra est plus performant que Floyd-Warshall pour une densité faible (<10)
     * sinon Floyd-Wharshall est plus performant. Néanmoins lorsque le nombre de noeuds augmente Dijkstra est plus
     * performant que Floyd-Warshall.
     */
    public static int floydVsDijkstra(){
        int count = 0;
        for (int i = 0; i < 101; i++) {
            Graph graph = new Graph(40, i, 1, 10);
            ShortestPaths shortestPaths = new ShortestPaths(graph);
            ShortestPath shortestPath = new ShortestPath(graph);
            long startFloyd = System.nanoTime();
            shortestPaths.floydWarshall();
            long endFloyd = System.nanoTime();
            long startDijkstra = System.nanoTime();
            for (int k = 0; k < graph.getNodesNumber(); k++) {
                shortestPath.dijkstra(k, false);
            }
            long endDijkstra = System.nanoTime();
            if(endFloyd - startFloyd < endDijkstra - startDijkstra) count++;
            if(count==2) return i;
        }
        return 101;
    }

}
