import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Graph {

    private final int MAX_DENSITY = 100;
    private List<LinkedList<Arc>> neighbours;
    private int nodesNumber;
    private int density;
    private List<Integer> allNodes;
    private int maxWeight;
    private int minWeight;

    public Graph(int nodesNumber, int density, int minWeight, int maxWeight){
        this.neighbours = new ArrayList<>();
        this.nodesNumber = nodesNumber;
        this.density = density;
        this.allNodes = initNodes();
        this.maxWeight = maxWeight;
        this.minWeight = minWeight;
        generateGraph();
    }


    private void generateGraph() {
        if(density<=50) generator(neighbours, density);
        else {
            List<LinkedList<Arc>> neighboursComplementary = generateComplementary();
            addNeighboursWithComplementary(neighboursComplementary);
        }
    }

    private void addNeighboursWithComplementary(List<LinkedList<Arc>> neighboursComplementary) {
        for(int i=0; i<nodesNumber; i++){
            LinkedList<Integer> nodes = new LinkedList<>(allNodes);
            nodes.remove(i);
            LinkedList<Arc> v = nodes.stream()
                    .map(node -> new Arc(node, new Random().nextInt(maxWeight) + minWeight))
                    .collect(Collectors.toCollection(LinkedList::new));
            int finalI = i;
            v.removeIf(arc -> containsNode(neighboursComplementary.get(finalI), arc.getNode()));
            neighbours.add(i, v);
        }
    }

    private List<LinkedList<Arc>> generateComplementary() {
        List<LinkedList<Arc>> neighboursComplementary = new ArrayList<>();
        int densityComplementary = MAX_DENSITY - density;
        generator(neighboursComplementary, densityComplementary);
        return neighboursComplementary;
    }

    private void generator(List<LinkedList<Arc>> neighboursComplementary, int density) {
        initGraph(neighboursComplementary);
        int numberArcsWanted = getNumberArcsWanted(density);
        int currentNumberArcs = 0;
        while (currentNumberArcs < numberArcsWanted) {
            addArcs(neighboursComplementary);
            currentNumberArcs++;
        }
    }

    private List<Integer> initNodes() {
        return IntStream.rangeClosed(0, nodesNumber-1).boxed().collect(Collectors.toList());
    }

    private void initGraph(List<LinkedList<Arc>> neighbours) {
        for(int i=0; i<nodesNumber; i++)
            neighbours.add(i, new LinkedList<>());
    }

    private void addArcs(List<LinkedList<Arc>> neighbours) {
        Random random = new Random();
        int u = random.nextInt(nodesNumber);
        int v = random.nextInt(nodesNumber);
        while(isArcExist(neighbours, u, v) || u == v){
            u = random.nextInt(nodesNumber);
            v = random.nextInt(nodesNumber);
        }
        int weight = random.nextInt(maxWeight) + minWeight;
        neighbours.get(u).add(new Arc(v, weight));
    }

    private boolean isArcExist(List<LinkedList<Arc>> neighbours, int u, int v) {
        return neighbours.size() > u && containsNode(neighbours.get(u), v);
    }

    private boolean containsNode(LinkedList<Arc> arcs, int v) {
        return arcs.stream().anyMatch(arc -> arc.getNode() == v);
    }

    private int getNumberArcsWanted(int densityComplementary) {
        return nodesNumber*(nodesNumber-1) * densityComplementary/MAX_DENSITY;
    }

    private void addNeighbour(int currentNode, int neighbour, int weight){
        neighbours.get(currentNode).add(new Arc(neighbour, weight));
    }

    public List<LinkedList<Arc>> getNeighbours() {
        return neighbours;
    }

    public List<Integer> getAllNodes() {
        return allNodes;
    }

    public int getNodesNumber() {
        return nodesNumber;
    }

    @Override
    public String toString() {
        return "neighbours=" + neighbours;
    }
}
