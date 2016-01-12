/*
 * Copyright (c) 2012 Stefan Wolf
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

import com.google.common.base.Preconditions;
import hudson.model.AbstractProject;

import hudson.model.Job;
import hudson.model.Result;
import hudson.model.Run;

import java.util.List;


/**
 * A Node in the DependencyGraph, which corresponds to a Project
 */
public class ProjectNode {
    private final AbstractProject<?,?> project;
    private int success;
    private int unstable;
    private int fail;

    public static ProjectNode node(AbstractProject<?, ?> project) {
        return new ProjectNode(project);
    }

    public ProjectNode(AbstractProject<?, ?> project) {
        Preconditions.checkNotNull(project);
        this.project = project;
        this.success = 0;
        this.unstable = 0;
        this.fail = 0;
        
    }

    public void checkbuildStatus(){
    List<Run> builds = ((Job)project).getBuilds();
        
        for (Run build : builds) {
        // a build result can be null if the build is currently building (JENKINS-15067)
            if(build.getResult() != null) {
                if (build.getResult().isBetterOrEqualTo(Result.SUCCESS))
                    addSuccess();
                else if (build.getResult().isBetterOrEqualTo(Result.UNSTABLE))
                    addUnstable();
                else if (build.getResult().isBetterOrEqualTo(Result.FAILURE))
                    addFail();  
        }
        }

    }

    public String getName() {
        return project.getFullDisplayName();
    }

    public AbstractProject<?, ?> getProject() {
        return project;
    }

    public String getTime(){
        return (project.getLastBuild()).getTime().toString();
    }

    public long getDurationofLastBuild(){
        return (project.getLastBuild()).getDuration();
    }

     public void addSuccess() {
        this.success++;
      }

      public void addUnstable() {
        this.unstable++;
      }

      public void addFail() {
        this.fail++;
      }

      public int getSuccess() {
        return this.success;
      }

      public int getUnstable() {
        return this.unstable;
      }

      public int getFail() {
        return this.fail;
      }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectNode that = (ProjectNode) o;

        if (!project.equals(that.project)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return project.hashCode();
    }
}
