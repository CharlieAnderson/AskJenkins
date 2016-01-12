// CHECKSTYLE:OFF

package hudson.plugins.depgraph_view;

import org.jvnet.localizer.Localizable;
import org.jvnet.localizer.ResourceBundleHolder;

@SuppressWarnings({
    "",
    "PMD"
})
public class Messages {

    private final static ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);

    /**
     * Dependency Graph Viewer
     * 
     */
    public static String DependencyGraphProperty_DependencyGraphViewer() {
        return holder.format("DependencyGraphProperty.DependencyGraphViewer");
    }

    /**
     * Dependency Graph Viewer
     * 
     */
    public static Localizable _DependencyGraphProperty_DependencyGraphViewer() {
        return new Localizable(holder, "DependencyGraphProperty.DependencyGraphViewer");
    }

    /**
     * Dependency Graph
     * 
     */
    public static String AbstractDependencyGraphAction_DependencyGraph() {
        return holder.format("AbstractDependencyGraphAction.DependencyGraph");
    }

    /**
     * Dependency Graph
     * 
     */
    public static Localizable _AbstractDependencyGraphAction_DependencyGraph() {
        return new Localizable(holder, "AbstractDependencyGraphAction.DependencyGraph");
    }

    /**
     * Dependency Graph of {0}
     * 
     */
    public static String AbstractDependencyGraphAction_DependencyGraphOf(Object arg1) {
        return holder.format("AbstractDependencyGraphAction.DependencyGraphOf", arg1);
    }

    /**
     * Dependency Graph of {0}
     * 
     */
    public static Localizable _AbstractDependencyGraphAction_DependencyGraphOf(Object arg1) {
        return new Localizable(holder, "AbstractDependencyGraphAction.DependencyGraphOf", arg1);
    }

}
