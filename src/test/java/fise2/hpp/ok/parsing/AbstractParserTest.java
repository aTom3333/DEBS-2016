package fise2.hpp.ok.parsing;

import org.junit.Assert;
import org.junit.Test;

public class AbstractParserTest {

    @Test
    public void splitLine() {
        String[] strings = AbstractParser.splitLine("ghost|line|");
        Assert.assertEquals(3, strings.length);
        Assert.assertEquals("ghost", strings[0]);
        Assert.assertEquals("line", strings[1]);
        Assert.assertEquals("", strings[2]);

        strings = AbstractParser.splitLine("ghost||line");
        Assert.assertEquals(3, strings.length);
        Assert.assertEquals("ghost", strings[0]);
        Assert.assertEquals("", strings[1]);
        Assert.assertEquals("line", strings[2]);
    }

    @Test
    public void stringToTS() {
    }
}