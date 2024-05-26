package frc.lib.common;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import frc.lib.helpers.*;

public class GraphTests
{
    // @Test
    public void testGraph()
    {
        TestGraph graph = new TestGraph();
        TestGraphNode portland = graph.createNode("Portland");
        TestGraphNode seattle = graph.createNode("Seattle");
        TestGraphNode ashland = graph.createNode("Ashland");
        TestGraphNode bellevue = graph.createNode("Bellevue");
        TestGraphNode yakima = graph.createNode("Yakima");
        TestGraphNode everett = graph.createNode("Everett");
        TestGraphNode leavenworth = graph.createNode("Leavenworth");

        graph.connectBidirectional(portland, seattle, 2.0);
        graph.connectBidirectional(seattle, bellevue, 0.5);
        graph.connectBidirectional(portland, bellevue, 2.0);
        graph.connectBidirectional(bellevue, yakima, 1.0);
        graph.connectBidirectional(yakima, portland, 2.0);
        graph.connectBidirectional(portland, ashland, 1.0);
        graph.connectBidirectional(yakima, ashland, 2.5);
        graph.connectBidirectional(everett, seattle, 1.0);
        graph.connectBidirectional(everett, bellevue, 1.25);
        graph.connect(leavenworth, everett, 1.0); // long detour...
        graph.connectBidirectional(leavenworth, yakima, 1.0);

        // graph.precalculateOptimalPaths();

        List<TestGraphNode> list = graph.getOptimalPath(bellevue, ashland);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(bellevue, list.get(0));
        Assertions.assertEquals(portland, list.get(1));
        Assertions.assertEquals(ashland, list.get(2));

        list = graph.getOptimalPath(ashland, ashland);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(ashland, list.get(0));

        list = graph.getOptimalPath(everett, portland);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(everett, list.get(0));
        Assertions.assertEquals(seattle, list.get(1));
        Assertions.assertEquals(portland, list.get(2));

        list = graph.getOptimalPath(everett, yakima);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(everett, list.get(0));
        Assertions.assertEquals(bellevue, list.get(1));
        Assertions.assertEquals(yakima, list.get(2));

        list = graph.getOptimalPath(leavenworth, seattle);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(leavenworth, list.get(0));
        Assertions.assertEquals(everett, list.get(1));
        Assertions.assertEquals(seattle, list.get(2));

        list = graph.getOptimalPath(seattle, leavenworth);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(4, list.size());
        Assertions.assertEquals(seattle, list.get(0));
        Assertions.assertEquals(bellevue, list.get(1));
        Assertions.assertEquals(yakima, list.get(2));
        Assertions.assertEquals(leavenworth, list.get(3));
    }

    static class TestGraph extends Graph<TestGraphNode>
    {
        public TestGraph()
        {
            super();
        }

        public TestGraphNode createNode(String name)
        {
            TestGraphNode node = new TestGraphNode(name);
            this.addNode(node);
            return node;
        }
    }

    static class TestGraphNode extends GraphNode
    {
        public final String name;

        public TestGraphNode(String name)
        {
            super();

            this.name = name;
        }

        @Override
        public String toString()
        {
            return this.name;
        }
    }
}
