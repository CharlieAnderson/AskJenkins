package hudson.plugins.depgraph_view.model.operations;

import hudson.plugins.depgraph_view.model.graph.DependencyGraph;
import hudson.plugins.depgraph_view.model.graph.*;

import java.util.Collection;
import java.util.ArrayList;

/*jokeefe94 psjoshi2
 * 11/4/15
 */

public class CycleFinder
{
	
	public static boolean hasCycle(DependencyGraph graph)
	{
		return false;
	}

	public static Collection<ProjectNode> getCycleNodes(DependencyGraph graph)
	{
		return new ArrayList<ProjectNode>();
	}

	public static Collection<Edge> getCycleEdges(DependencyGraph graph)
	{
        	return new ArrayList<Edge>();
    	}

	public static void setCycleEdgesColor(DependencyGraph graph)
	{
        	for(Edge e : getCycleEdges(graph))
		{
            		e.setColor("red");
        	}
    	}
}
