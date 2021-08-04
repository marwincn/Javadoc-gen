package cn.marwin;

import cn.marwin.util.CommandUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CommandUtilTest {
    @Test
    public void exec() throws IOException {
        CommandUtil.CommandResult result = CommandUtil.exec(new String[] {"echo",  "hello"});

        Assert.assertTrue(result.isSuccess());
        Assert.assertEquals("hello\n", result.getOutput());
        Assert.assertEquals("", result.getError());
    }

    @Test
    public void exec1() throws IOException {
        CommandUtil.CommandResult result = CommandUtil.exec(new String[] {"javadoc",  "-help"});
        System.out.println("Output:");
        System.out.println(result.getOutput());
        System.out.println("Error:");
        System.out.println(result.getError());
    }
}