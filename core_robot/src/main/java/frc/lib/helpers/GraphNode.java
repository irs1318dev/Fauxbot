package frc.lib.helpers;

import java.util.ArrayList;

public abstract class GraphNode
{
    final ArrayList<GraphLink> edgesFrom;
    int ordinal;

    protected GraphNode()
    {
        this.edgesFrom = new ArrayList<GraphLink>();
        this.ordinal = -1;
    }

    void addLink(GraphLink graphLink)
    {
        this.edgesFrom.add(graphLink);
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof GraphNode)
        {
            return ((GraphNode)other).ordinal == this.ordinal;
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return this.ordinal;
    }
}
