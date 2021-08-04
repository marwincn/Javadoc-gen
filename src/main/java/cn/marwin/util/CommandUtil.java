package cn.marwin.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

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

        Process process = null;
        PrintThread resultThread = null;
        PrintThread errorThread = null;
        try {
            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
            process = Runtime.getRuntime().exec(cmds, null, dir);

            // 另起线程获取输出和错误信息
            resultThread = new PrintThread(process.getInputStream());
            resultThread.start();
            errorThread = new PrintThread(process.getErrorStream());
            errorThread.start();

            // 方法阻塞, 等待命令执行完成
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new CommandResult(process.exitValue(), resultThread.getPrintRecords(), errorThread.getPrintRecords());
    }

    private static void printCommand(String[] cmds, File dir) throws IOException {
        String cmdDir = dir == null ? System.getProperty("user.dir") : dir.getCanonicalPath();
        System.out.println(MessageFormat.format("exec command: [{0}] {1}", cmdDir, String.join(" ", cmds)));
    }

    private static class PrintThread extends Thread {
        @Getter
        private volatile String printRecords;
        private final InputStream inputStream;

        public PrintThread(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            StringBuilder recordBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                // 读取流
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    recordBuilder.append(line).append(System.lineSeparator());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭流
                try {
                    inputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            // 存储流内容
            printRecords = recordBuilder.toString();
        }
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
