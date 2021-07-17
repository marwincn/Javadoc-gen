package cn.marwin;

import cn.marwin.util.DownloadUtil;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class DownloadUtilTest {
    @Test
    public void download() throws Exception {
        Path dir = Paths.get("/Users/marwin/test/");
        DownloadUtil.download("git@github.com:marwincn/Javadoc-gen.git", dir);
        Path file = Paths.get(dir.toString(), "Javadoc-gen");

        assertTrue(Files.exists(file));
    }
}