package cn.marwin.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DownloadUtil {
    public static void download(String url, String branch, Path dir) throws Exception {
        // 目录不存在，创建下载目录
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
            System.out.println("Created dir: " + dir);
        }

        // 判断目录是否合法
        if (!Files.isDirectory(dir)) {
            throw new IOException("Path is not a directory: " + dir);
        }

        // 执行下载命令
        CommandUtil.CommandResult result = CommandUtil.exec(new String[]{ "git", "clone", "-b", branch, url}, dir.toFile());
        if (!result.isSuccess()) {
            throw new Exception("Exit error(" + result.getExitCode() + "): " + result.getError());
        }
    }
}
