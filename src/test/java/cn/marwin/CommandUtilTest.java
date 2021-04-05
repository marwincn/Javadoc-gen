package cn.marwin;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CommandUtilTest {
    @Test
    public void exec() throws IOException {
        CommandUtil.CommandResult result = CommandUtil.exec("echo hello");

        Assert.assertEquals(0, result.getExitCode());
        Assert.assertEquals("hello\n", result.getOutput());
        Assert.assertEquals("", result.getError());
    }
}