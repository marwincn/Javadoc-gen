package cn.marwin.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class CommandUtil {
    /**
     * 执行系统命令行
     *
     * @param cmds 命令
     * @return 命令执行结果
     * @throws IOException 异常
     */
    public static CommandResult exec(String[] cmds) throws IOException {
        return exec(cmds, null);
    }

    /**
     * 执行系统命令行
     *
     * @param cmds 命令
     * @param dir 指定工作目录，null为主进程工作目录
     * @return 命令执行结果
     * @throws IOException 异常
     */
    public static CommandResult exec(String[] cmds, File dir) throws IOException {
        printCommand(cmds, dir);

        StringBuilder resultBuilder = new StringBuilder();
        StringBuilder errorBuilder = new StringBuilder();

        Process process = null;
        BufferedReader bufResult = null;
        BufferedReader bufError = null;
        try {
            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
            process = Runtime.getRuntime().exec(cmds, null, dir);

            // 获取命令执行结果和错误
            bufResult = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            bufError = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));

            // 读取输出和错误
            String line;
            while ((line = bufResult.readLine()) != null) {
                resultBuilder.append(line).append(System.lineSeparator());
            }
            while ((line = bufError.readLine()) != null) {
                errorBuilder.append(line).append(System.lineSeparator());
            }

            // 方法阻塞, 等待命令执行完成
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            closeStream(bufResult);
            closeStream(bufError);

            // 销毁子进程
            if (process != null) {
                process.destroy();
            }
        }

        return new CommandResult(process.exitValue(), resultBuilder.toString(), errorBuilder.toString());
    }

    private static void closeStream(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printCommand(String[] cmds, File dir) throws IOException {
        String cmdDir = dir == null ? System.getProperty("user.dir") : dir.getCanonicalPath();
        System.out.println(MessageFormat.format("exec command: [{0}] {1}", cmdDir, String.join(" ", cmds)));
    }

    @Data
    @AllArgsConstructor
    public static class CommandResult {
        private int exitCode;
        private String output;
        private String error;

        public boolean isSuccess() {
            return exitCode == 0;
        }
    }
}
