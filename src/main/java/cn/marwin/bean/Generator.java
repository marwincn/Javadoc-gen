package cn.marwin.bean;

import cn.marwin.util.GenerateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Generator {
    @Autowired
    Configuration configuration;

    /**
     * 在Configuration注入完成后再执行
     */
    @PostConstruct
    public void init() {
        try {
            // 打印读取的配置
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("Configuration: " + mapper.writeValueAsString(configuration));

            GenerateUtil.generate(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
