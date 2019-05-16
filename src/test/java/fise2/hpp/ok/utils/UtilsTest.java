package fise2.hpp.ok.utils;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void TSToString() {
        Assert.assertEquals("1970-01-01T00:00:00.000+0000", Utils.TSToString(0));
    }
}