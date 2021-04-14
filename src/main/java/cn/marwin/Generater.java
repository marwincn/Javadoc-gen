package cn.marwin;

import cn.marwin.model.Module;
import cn.marwin.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

@Component
public class Generater {
    @Autowired
    private Configuration configuration;

    public void generate() throws Exception {
        List<Project> projects = configuration.getProjects();
        String rootPath = configuration.getFileLocation();
        String downloadPath = rootPath + File.separator + "source";

        for (Project project : projects) {
            String projectSrcPath = downloadPath + File.separator + project.getProjectName();
            String projectDocPath = rootPath + File.separator + project.getProjectName();

            Downloader.download(project.getProjectUrl(), downloadPath);
            if (project.getModules().size() == 1) {
                Module module = project.getModules().get(0);
                String[] commandArgs = new String[] {
                        "javadoc", "-encoding", "utf-8", "-charset", "utf-8", "-d", projectDocPath, "-sourcepath",
                        projectSrcPath + module.getModulePath(), "-subpackages", String.join(" ", module.getPackages())
                };

                CommandUtil.CommandResult result = CommandUtil.exec(commandArgs);
                if (!result.isSuccess()) {
                    System.out.println(result.getError());
                }
            }
        }
    }
}
