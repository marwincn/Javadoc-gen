package cn.marwin;

import cn.marwin.util.DownloadUtil;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class DownloadUtilTest {
    @Test
    public void download() throws Exception {
        String dir = "/Users/marwin/test/";
        DownloadUtil.download("git@github.com:marwincn/AdminSystem.git", dir);
        File file = new File(dir, "AdminSystem");

        assertTrue(file.exists());
    }
}