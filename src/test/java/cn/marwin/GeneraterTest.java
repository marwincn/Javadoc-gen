package cn.marwin;

import cn.marwin.bean.Generator;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneraterTest extends TestCase {
    @Autowired
    Generator generator;

    @Test
    public void testGenerate() throws Exception {
        generator.init();
    }
}