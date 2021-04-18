package cn.marwin.bean;

import cn.marwin.model.Module;
import cn.marwin.model.Project;
import cn.marwin.util.CommandUtil;
import cn.marwin.util.DownloadUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.text.MessageFormat;
import java.util.List;

@Component
@DependsOn("configuration")
public class Generator {
    @Autowired
    public Configuration configuration;

    @PostConstruct
    public void init() {
//        new Thread(() -> {
            try {
                generate();
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }).start();
    }

    private void generate() throws Exception {
        List<Project> projects = configuration.getProjects();
        String rootPath = configuration.getFileLocation();
        String downloadPath = rootPath + File.separator + "source";

        for (Project project : projects) {
            String projectSrcPath = downloadPath + File.separator + project.getProjectName();
            String projectDocPath = rootPath + File.separator + project.getProjectName();

            DownloadUtil.download(project.getProjectUrl(), downloadPath);
            if (project.getModules().size() == 1) {
                Module module = project.getModules().get(0);
                String command = MessageFormat.format("javadoc -encoding utf-8 -charset utf-8 -d {0} -sourcepath {1} -subpackages {2}",
                        projectDocPath, projectSrcPath + module.getModulePath(), String.join(" ", module.getPackages()));

                CommandUtil.CommandResult result = CommandUtil.exec(command.split(" "));
                if (!result.isSuccess()) {
                    System.out.println(result.getError());
                }
            }
        }
    }
}
