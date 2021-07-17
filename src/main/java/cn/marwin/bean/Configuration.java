package cn.marwin.bean;

import cn.marwin.model.Project;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * 读取用户的配置
 */
@Component
public class Configuration {
    @Getter
    private List<Project> projects;

    @Getter
    @Setter
    @Value("${file.location}")
    private String fileLocation;

    public Configuration() {
        init();
    }

    /**
     * 读取自定义配置内容
     */
    private void init() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.json");
            ObjectMapper mapper = new ObjectMapper();
            projects = mapper.readValue(inputStream, new TypeReference<List<Project>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
