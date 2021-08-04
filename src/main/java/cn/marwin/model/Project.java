package cn.marwin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
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
     * 代码仓分支
     */
    private String branch;

    /**
     * 需要生成Javadoc的module，一个支持多个module
     */
    private List<Module> modules;
}
