package frc.lib.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public abstract class Graph<TGraphNode extends GraphNode>
{
    private final ArrayList<TGraphNode> nodes;
    private final ArrayList<ArrayList<TGraphNode>> optimalPredecessorPathMap;

    private int nodeCount;
    private boolean precalculated;

    protected Graph()
    {
        this.optimalPredecessorPathMap = new ArrayList<ArrayList<TGraphNode>>();
        this.nodes = new ArrayList<TGraphNode>();

        this.nodeCount = 0;
        this.precalculated = false;
    }

    protected void addNode(TGraphNode node)
    {
        node.ordinal = this.nodeCount++;
        this.nodes.add(node);
        this.optimalPredecessorPathMap.add(null);
        ExceptionHelpers.Assert(this.nodes.size() == this.nodeCount, "Expect node count %d to equal node count %d", this.nodes.size(), this.nodeCount);
        ExceptionHelpers.Assert(this.optimalPredecessorPathMap.size() == this.nodeCount, "Expect optimal predecessor map count %d to equal node count %d", this.nodes.size(), this.nodeCount);
    }

    public void connectBidirectional(TGraphNode node1, TGraphNode node2)
    {
        this.connectBidirectional(node1, node2, GraphLink.DEFAULT_WEIGHT);
    }

    public void connectBidirectional(TGraphNode node1, TGraphNode node2, double weight)
    {
        this.connectBidirectional(node1, node2, weight, weight);
    }

    public void connectBidirectional(TGraphNode node1, TGraphNode node2, double weight12, double weight21)
    {
        this.connect(node1, node2, weight12);
        this.connect(node2, node1, weight21);
    }

    public void connect(TGraphNode from, TGraphNode to)
    {
        this.connect(from, to, GraphLink.DEFAULT_WEIGHT);
    }

    public void connect(TGraphNode from, TGraphNode to, double weight)
    {
        from.addLink(new GraphLink(from, to, weight));
    }

    public List<TGraphNode> getNodes()
    {
        return this.nodes;
    }

    public void precalculateOptimalPaths()
    {
        System.out.println("Precalculating optimal paths through the graph");
        for (int i = 0; i < this.nodeCount; i++)
        {
            TGraphNode node = this.nodes.get(i);
            ExceptionHelpers.Assert(node.ordinal == i, "Expect node ordinal %d to equal index %d", node.ordinal, i);
            ArrayList<TGraphNode> optimalPredecessorNodes = this.dijkstra(node);
            this.optimalPredecessorPathMap.set(i, optimalPredecessorNodes);
        }

        this.precalculated = true;
    }

    public List<TGraphNode> getOptimalPath(TGraphNode start, TGraphNode end)
    {
        ArrayList<TGraphNode> optimalPredecessorNodes = this.optimalPredecessorPathMap.get(start.ordinal);
        if (optimalPredecessorNodes == null)
        {
            ExceptionHelpers.Assert(!this.precalculated, "Didn't expect to need to calculate the optimal path if we precalculated them...");
            optimalPredecessorNodes = this.dijkstra(start);
            this.optimalPredecessorPathMap.set(start.ordinal, optimalPredecessorNodes);
        }

        // build the list in reverse order
        List<TGraphNode> optimalPath = new ArrayList<TGraphNode>(16);
        TGraphNode node = end;
        while (node != null)
        {
            optimalPath.add(node);
            node = optimalPredecessorNodes.get(node.ordinal);
        }

        Collections.reverse(optimalPath);
        if (optimalPath.size() < 1 || optimalPath.get(0) != start)
        {
            ExceptionHelpers.Assert(false, "The provided start node is not reachable from the provided end node.");
            return null;
        }

        return optimalPath;
    }

    private ArrayList<TGraphNode> dijkstra(TGraphNode start)
    {
        // the optimal previous node along each potential path for each node based on the provided starting node,
        // where the index is the ordinal number for the node.
        ArrayList<TGraphNode> optimalPreviousNode = new ArrayList<TGraphNode>(this.nodeCount);
        for (int i = 0; i < this.nodeCount; i++)
        {
            optimalPreviousNode.add(null);
        }

        // the current distance to each node from the starting node
        Map<GraphNode, Double> distanceMap = new HashMap<GraphNode, Double>(this.nodeCount);

        // the set of nodes that have not yet been visited
        HashSet<TGraphNode> unvisitedNodes = new HashSet<TGraphNode>(this.nodeCount);
        for (TGraphNode node : this.nodes)
        {
            if (node == start)
            {
                distanceMap.put(node, 0.0);
                unvisitedNodes.add(node);
            }
            else
            {
                distanceMap.put(node, Double.POSITIVE_INFINITY);
                unvisitedNodes.add(node);
            }
        }

        while (!unvisitedNodes.isEmpty())
        {
            TGraphNode node = null;
            for (TGraphNode unvisitedNode : unvisitedNodes)
            {
                if (node == null || distanceMap.get(unvisitedNode) < distanceMap.get(node))
                {
                    node = unvisitedNode;
                }
            }

            unvisitedNodes.remove(node);

            double nodeDistance = distanceMap.get(node);
            for (GraphLink link : node.edgesFrom)
            {
                GraphNode to = link.to;
                double distance = nodeDistance + link.weight;
                if (distance < distanceMap.get(to))
                {
                    distanceMap.put(to, distance);
                    optimalPreviousNode.set(to.ordinal, node);
                }
            }
        }

        return optimalPreviousNode;
    }
}
