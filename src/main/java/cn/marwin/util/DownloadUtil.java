package cn.marwin.util;

import java.io.File;

public class DownloadUtil {
    public static void download(String url, String dir) throws Exception {
        File saveDir = new File(dir);
        // 创建下载目录
        if (!saveDir.exists()) {
            boolean mkResult = saveDir.mkdirs();
            if (!mkResult) {
                throw new Exception("Create dir failed : " + dir);
            }
        }

        // 判断目录是否合法
        if (!saveDir.isDirectory() || !saveDir.canWrite()) {
            throw new Exception("Path is not valid : " + dir);
        }

        // 执行下载命令
        CommandUtil.CommandResult result = CommandUtil.exec(new String[]{ "git", "clone", url}, saveDir);
        if (!result.isSuccess()) {
            throw new Exception("Exit error(" + result.getExitCode() + "): " + result.getError());
        }
        // 执行完打印命令执行结果
        System.out.print(result.getOutput());
    }
}
