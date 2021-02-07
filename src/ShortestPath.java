import java.util.*;

public class ShortestPath {

    private Graph graph;
    private List<LinkedList<Arc>> neighbours;

    public ShortestPath(Graph graph){
        this.graph = graph;
        this.neighbours = graph.getNeighbours();
    }

    public void dijkstra(int source, boolean priorityQueue){
        int[] distance = initDijkstra(source);
        if(priorityQueue) dijkstraPriorityQueue(distance);
        else dijkstraSequentialSearch(distance);
        //System.out.println(Arrays.toString(distance)); // print distances after dijkstra
    }

    private int[] initDijkstra(int source) {
        int length = graph.getNodesNumber();
        int[] distance = new int[length];
        for (int i = 0; i < length; i++)  distance[i] = Integer.MAX_VALUE;
        distance[source] = 0;
        return distance;
    }

    private void dijkstraPriorityQueue(int[] distance) {
        PriorityQueue<ArcPriority> priorityQueue = new PriorityQueue<>();
        int neighboursSize = neighbours.size();
        for (int i = 0; i < neighboursSize; i++) {
            int finalI = i;
            neighbours.get(i).forEach(arc ->
                    priorityQueue.add(new ArcPriority(finalI, arc.getNode(),distance[arc.getNode()])));
        }
        while(!priorityQueue.isEmpty()){
            int currentMin = priorityQueue.poll().getNodeSecond();
            if(distance[currentMin] == Integer.MAX_VALUE) break;
            for(Arc arc : neighbours.get(currentMin)){
                int newDistance = distance[currentMin] + arc.getWeight();
                if(distance[arc.getNode()] > newDistance){
                    distance[arc.getNode()] = newDistance;
                    priorityQueue.add(new ArcPriority(currentMin, arc.getNode(), newDistance));
                }
            }
        }
    }

    private void dijkstraSequentialSearch(int[] distance) {
        List<Integer> queue = graph.getAllNodes();
        while(!queue.isEmpty()){
            int currentMin = extractMin(queue, distance);
            if(currentMin == Integer.MIN_VALUE) break;
            queue.remove(queue.size()-1);
            for(Arc arc : neighbours.get(currentMin)){
                if(distance[arc.getNode()] > distance[currentMin] + arc.getWeight())
                    distance[arc.getNode()] = distance[currentMin] + arc.getWeight();
            }
        }
    }

    private int extractMin(List<Integer> queue, int[] distance) {
        int min = Integer.MAX_VALUE;
        int indexMin = Integer.MAX_VALUE;
        for(Integer node : queue){
            if(distance[node] < min){
                min = distance[node];
                indexMin = queue.indexOf(node);
            }
        }
        if(min == Integer.MAX_VALUE) return Integer.MIN_VALUE;
        swap(queue, indexMin);
        return queue.get(queue.size()-1);
    }

    private void swap(List<Integer> queue, int indexMin) {
        int tmp = queue.get(queue.size()-1);
        queue.set(queue.size()-1, queue.get(indexMin));
        queue.set(indexMin, tmp);
    }
}
