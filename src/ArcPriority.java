public class ArcPriority implements Comparable<ArcPriority> {

    private int nodeOne;
    private int nodeSecond;
    private int weight;

    public ArcPriority(int nodeOne, int nodeSecond, int weight) {
        this.nodeOne = nodeOne;
        this.nodeSecond = nodeSecond;
        this.weight = weight;
    }

    @Override
    public int compareTo(ArcPriority arcPriority) {
        return this.getWeight() - arcPriority.getWeight();
    }

    public int getNodeOne() {
        return nodeOne;
    }

    public int getNodeSecond() {
        return nodeSecond;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "(" + nodeOne+","+ nodeSecond+","+weight+")";
    }
}
