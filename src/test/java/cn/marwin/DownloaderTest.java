package cn.marwin;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class DownloaderTest {
    @Test
    public void download() throws Exception {
        String dir = "/Users/marwin/test/";
        Downloader.download("git@github.com:marwincn/AdminSystem.git", dir);
        File file = new File(dir, "AdminSystem");

        Assert.assertTrue(file.exists());
    }
}