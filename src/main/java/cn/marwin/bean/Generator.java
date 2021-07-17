package cn.marwin.bean;

import cn.marwin.model.Module;
import cn.marwin.model.Project;
import cn.marwin.util.CommandUtil;
import cn.marwin.util.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

@Component
@DependsOn("configuration")
public class Generator {
    @Autowired
    public Configuration configuration;

    /**
     * 在Configuration注入完成后再执行
     */
    @PostConstruct
    public void init() {
        try {
            generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generate() throws Exception {
        // web服务器根路径
        Path rootPath = Paths.get(configuration.getFileLocation());
        // 源码下载路径
        Path sourcePath = Paths.get(rootPath.toString(), "sources");

        List<Project> projects = configuration.getProjects();
        for (Project project : projects) {
            Path projectSrcPath = Paths.get(sourcePath.toString(), project.getProjectName());
            Path projectDocPath = Paths.get(rootPath.toString(), project.getProjectName());

            // 下载源码，todo 如果源码已下载则执行git pull
            DownloadUtil.download(project.getProjectUrl(), sourcePath);

            // 生成javadoc
            if (project.getModules().size() == 1) { // 如果只有一个module，项目目录就是javadoc目录。路径为：projectName/index.html
                Module module = project.getModules().get(0);
                genModuleDoc(module, projectSrcPath, projectDocPath);
            } else { // 如果有多个module，再项目目录下为每个module生成子目录。路径为：projectName/moduleName/index.html
                // todo: 多个module的处理
            }
        }
    }

    private void genModuleDoc(Module module, Path projectSrcPath, Path projectDocPath) throws IOException {
        Path moduleSourcePath = Paths.get(projectSrcPath.toString(), module.getModulePath());

        String command = MessageFormat.format(
                "javadoc -encoding utf-8 -charset utf-8 -d {0} -sourcepath {1} -subpackages {2}",
                projectDocPath, moduleSourcePath, String.join(" ", module.getPackages()));
        System.out.println("exec command: " + command);
        CommandUtil.CommandResult result = CommandUtil.exec(command.split(" "));

        if (!result.isSuccess()) {
            System.out.println(result.getError());
        } else {
            System.out.println("generate javadoc success: " + result.getOutput());
        }
    }
}
