package cn.marwin.util;

import cn.marwin.bean.Configuration;
import cn.marwin.model.Module;
import cn.marwin.model.Project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

public class GenerateUtil {
    public static void generate(Configuration configuration) throws Exception {
        // web服务器根路径
        Path rootPath = Paths.get(configuration.getFileLocation());
        // 源码下载路径
        Path sourcePath = Paths.get(rootPath.toString(), "sources");

        for (Project project : configuration.getProjects()) {
            Path projectSrcPath = Paths.get(sourcePath.toString(), project.getProjectName());
            Path projectDocPath = Paths.get(rootPath.toString(), project.getProjectName());

            // 下载源码，如果目录存在则更新代码
            if (Files.exists(projectSrcPath)) {
                CommandUtil.exec(new String[]{"git", "pull"}, projectSrcPath.toFile());
            } else {
                DownloadUtil.download(project.getProjectUrl(), project.getBranch(), sourcePath);
            }

            // 生成javadoc
            genProjectDoc(project, projectDocPath, projectSrcPath);
        }
    }

    private static void genProjectDoc(Project project, Path projectDocPath, Path projectSrcPath) {
        if (project.getModules().size() == 1) { // 如果只有一个module，项目目录就是javadoc目录。路径为：projectName/index.html
            Module module = project.getModules().get(0);
            Path moduleSourcePath = Paths.get(projectSrcPath.toString(), module.getModulePath());
            genModuleDoc(projectDocPath, moduleSourcePath, module.getPackages());
        } else { // 如果有多个module，再项目目录下为每个module生成子目录。路径为：projectName/moduleName/index.html
            for (Module module : project.getModules()) {
                Path moduleDocPath = Paths.get(projectDocPath.toString(), module.getModuleName());
                Path moduleSourcePath = Paths.get(projectSrcPath.toString(), module.getModulePath());
                genModuleDoc(moduleDocPath, moduleSourcePath, module.getPackages());
            }
        }
    }

    /**
     * 生成一个Module的Javadoc
     *
     * @param moduleDocPath Javadoc生成路径
     * @param moduleSourcePath module源码路径
     * @param packages module的packages
     */
    private static void genModuleDoc(Path moduleDocPath, Path moduleSourcePath, List<String> packages) {
        try {
            String command = MessageFormat.format("javadoc -encoding utf-8 -charset utf-8 -d {0} -sourcepath {1} -subpackages {2}",
                    moduleDocPath, moduleSourcePath, String.join(" ", packages));
            CommandUtil.CommandResult result = CommandUtil.exec(command.split(" "));

            if (!result.isSuccess()) {
                System.out.println("generate javadoc fail: " + result.getError());
            } else {
                System.out.println("generate javadoc success: " + moduleDocPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
