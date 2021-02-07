public class Arc{

    private int node;
    private int weight;

    public Arc(int node, int weight){
        this.node = node;
        this.weight = weight;
    }

    public int getNode() {
        return node;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "(" + node+","+weight+")";
    }

}
