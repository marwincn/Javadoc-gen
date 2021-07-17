package cn.marwin;

import cn.marwin.bean.Configuration;
import cn.marwin.model.Module;
import cn.marwin.model.Project;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class ConfigurationTest {
    @Test
    public void configuration() {
        Configuration configuration = new Configuration();

//        String fileLocation = configuration.getFileLocation();
//        Assert.assertEquals(fileLocation, "/Users/marwin/test/");

        List<Project> projects = configuration.getProjects();
        Assert.assertEquals(projects.size(), 1);

        Module newModule = new Module();
        newModule.setModuleName("main");
        newModule.setModulePath("/src/main/java");
        newModule.setPackages(Collections.singletonList("cn.marwin"));
        Project newProject = new Project();
        newProject.setProjectName("Javadoc-gen");
        newProject.setProjectUrl("git@github.com:marwincn/Javadoc-gen.git");
        newProject.setModules(Collections.singletonList(newModule));

        Project project = projects.get(0);
        Assert.assertEquals(project, newProject);
    }
}