package cn.marwin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
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
}
