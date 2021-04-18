package cn.marwin.bean;

import cn.marwin.model.Module;
import cn.marwin.model.Project;
import cn.marwin.util.CommandUtil;
import cn.marwin.util.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
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
        // web服务器根路径
        String rootPath = configuration.getFileLocation();
        // 源码下载路径
        String downloadPath = rootPath + File.separator + "source";

        for (Project project : projects) {
            String projectSrcPath = downloadPath + File.separator + project.getProjectName();
            String projectDocPath = rootPath + File.separator + project.getProjectName();

            // 下载源码
            DownloadUtil.download(project.getProjectUrl(), downloadPath);

            // 生成javadoc
            if (project.getModules().size() == 1) { // 如果只有一个module，项目目录就是javadoc目录。路径为：projectName/index.html
                Module module = project.getModules().get(0);
                String moduleSourcePath = projectSrcPath + projectSrcPath + File.separator + module.getModulePath();

                String command = MessageFormat.format(
                        "javadoc -encoding utf-8 -charset utf-8 -d {0} -sourcepath {1} -subpackages {2}",
                        projectDocPath, moduleSourcePath, String.join(" ", module.getPackages()));
                CommandUtil.CommandResult result = CommandUtil.exec(command.split(" "));

                if (!result.isSuccess()) {
                    System.out.println(result.getError());
                }
            } else { // 如果有多个module，再项目目录下为每个module生成子目录。路径为：projectName/moduleName/index.html
                // todo: 多个module的处理
            }
        }
    }
}
