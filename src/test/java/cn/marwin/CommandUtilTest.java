package cn.marwin;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class CommandUtilTest {
    @Test
    public void exec() throws IOException {
        CommandUtil.CommandResult result = CommandUtil.exec(new String[] {"echo",  "hello"});

        Assert.assertEquals(0, result.getExitCode());
        Assert.assertEquals("hello\n", result.getOutput());
        Assert.assertEquals("", result.getError());
    }

    @Test
    public void exec1() throws IOException {
        CommandUtil.CommandResult result = CommandUtil.exec(new String[] {"javadoc",  "-help"}, new File("/Users/marwin/test/AdminSystem"));
        System.out.println(result.getOutput());
        System.out.println(result.getError());
    }
}