package cn.marwin.model;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class Project {
    /**
     * 仓库名
     */
    private String projectName;

    /**
     * 仓库的git地址，目录名就是git仓库名，同时也是路径名
     */
    private String projectUrl;

    /**
     * 需要生成Javadoc的module，一个支持多个module
     */
    private List<Module> modules;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(projectName, project.projectName) &&
                Objects.equals(projectUrl, project.projectUrl) &&
                Objects.equals(modules, project.modules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName, projectUrl, modules);
    }
}
