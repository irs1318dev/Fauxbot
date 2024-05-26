package frc.lib.helpers;

class GraphLink
{
    public static final double DEFAULT_WEIGHT = 1.0;

    final GraphNode from;
    final GraphNode to;
    final double weight;

    GraphLink(GraphNode from, GraphNode to, double weight)
    {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}