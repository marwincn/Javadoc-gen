package cn.marwin.model;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class Module {
    /**
     * 模块名<自定义>，同时也是路径名
     */
    private String moduleName;

    /**
     * 该module在项目下的路径
     */
    private String modulePath;

    /**
     * 需要生成Javadoc的包
     */
    private List<String> packages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return moduleName.equals(module.moduleName) &&
                modulePath.equals(module.modulePath) &&
                packages.equals(module.packages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleName, modulePath, packages);
    }
}
