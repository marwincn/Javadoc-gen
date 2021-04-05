package cn.marwin;

import cn.marwin.model.Project;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class Configuration {
    private List<Project> projects;

    public Configuration() {
        init();
    }

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
