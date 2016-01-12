/*
 * Copyright (c) 2010 Stefan Wolf
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package hudson.plugins.depgraph_view.model.graph;


import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import hudson.model.AbstractProject;
import hudson.model.FreeStyleProject;
import hudson.tasks.BuildTrigger;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static hudson.plugins.depgraph_view.model.graph.ProjectNode.node;
import static org.junit.Assert.assertTrue;

public class UserStory4Test {
    private FreeStyleProject project1;
    private FreeStyleProject project2;

    @Rule
    public JenkinsRule j = new JenkinsRule();

    @Test
    public void testEmptyProjectNodeStatistic() throws IOException {
        createProjects();
        addDependency(project1, project2);
        assertTrue(node(project1).getSuccess()== 0);
	   assertTrue(node(project1).getUnstable() == 0);
	   assertTrue(node(project1).getFail() == 0);

        assertTrue(node(project2).getSuccess() == 0);
	   assertTrue(node(project2).getUnstable() == 0);
	   assertTrue(node(project2).getFail() == 0);



    }
    @Test
    public void testWebAppStats() throws IOException, SAXException {
        createProjects();
        addDependency(project1, project2);
/*
        WebClient webClient = new WebClient();
       // webClient.addRequestHeader("ACCEPT", "application/json");
        DefaultCredentialsProvider credentials= new DefaultCredentialsProvider();
        credentials.addCredentials("cmndrsn2", "correctpass");
        webClient.setCredentialsProvider(credentials);
*/
        HtmlPage page = j.createWebClient().goTo("depgraph-view/jsplumb");
       // HtmlPage page2 = (HtmlPage) j.createWebClient().getPage("https://fa15-cs427-192.cs.illinois.edu:8083/job/mp1_maven/depgraph-view/jsplumb");  // https://fa15-cs427-166.cs.illinois.edu:8083/job/mp1_maven/depgraph-view/jsplumb

        final List<DomElement> elements = page.getByXPath("//div[@class='statWindow']");
        for(DomElement element: elements) {
            final String text = element.getNodeValue();
            System.out.println("Statistics test text: " + text);
            assertTrue(text.contains("Statistics for mp5_maven"));
            assertTrue(text.contains("numSuccess: 0"));
            assertTrue(text.contains("numUnstable: 0"));
            assertTrue(text.contains("numFailed: 0"));
            //*[@id="mp5-maven"]/text()[1]
        }
    }
    @Test
    public void testaddbuildStatus() throws IOException {
        createProjects();
        addDependency(project1, project2);

        ProjectNode test = node(project1);
        test.addSuccess();
        test.addSuccess();
        test.addSuccess();

        assertTrue(test.getSuccess() == 3);


    }

    private void assertGraphContainsProjects(DependencyGraph graph, AbstractProject<?,?>... projects) {
        Collection<ProjectNode> projectNodes = Lists.newArrayList(GraphCalculator.abstractProjectSetToProjectNodeSet(Arrays.asList(projects)));
        assertTrue(graph.getNodes().containsAll(projectNodes));
    }

    private DependencyGraph generateGraph(AbstractProject<?,?> from) {
        return new GraphCalculator(getDependencyGraphEdgeProviders()).generateGraph(Collections.singleton(node(from)));
    }

    private void assertHasOneDependencyEdge(DependencyGraph graph, AbstractProject<?,?> from, AbstractProject<?,?> to) {
        assertTrue(graph.findEdgeSet(node(from), node(to)).size() == 1);
    }

    private void createProjects() throws IOException {
        project1 = j.createFreeStyleProject();
        project2 = j.createFreeStyleProject();
    }

    private ImmutableSet<EdgeProvider> getDependencyGraphEdgeProviders() {
        return ImmutableSet.<EdgeProvider>of(new DependencyGraphEdgeProvider(j.getInstance()));
    }

    private void addDependency(AbstractProject<?,?> project1, AbstractProject<?,?> project2) throws IOException {
        project1.getPublishersList().add(new BuildTrigger(project2.getName(), false));
    }
}
