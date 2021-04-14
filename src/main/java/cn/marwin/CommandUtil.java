package cn.marwin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.nio.charset.StandardCharsets;

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
    public static CommandResult exec(String cmds[], File dir) throws IOException {
        StringBuilder resultBuilder = new StringBuilder();
        StringBuilder errorBuilder = new StringBuilder();

        Process process = null;
        BufferedReader bufIn = null;
        BufferedReader bufError = null;

        try {
            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
            process = Runtime.getRuntime().exec(cmds, null, dir);

            // 方法阻塞, 等待命令执行完成
            process.waitFor();

            // 获取命令执行结果和错误
            bufIn = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            bufError = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));

            // 读取输出和错误
            String line;
            while ((line = bufIn.readLine()) != null) {
                resultBuilder.append(line).append(System.lineSeparator());
            }
            while ((line = bufError.readLine()) != null) {
                errorBuilder.append(line).append(System.lineSeparator());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            closeStream(bufIn);
            closeStream(bufError);

            // 销毁子进程
            if (process != null) {
                process.destroy();
            }
        }

        return new CommandResult(process.exitValue(), resultBuilder.toString(), errorBuilder.toString());
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

    private static void closeStream(Closeable stream) {
        if (stream == null) {
            return;
        }

        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
