package fise2.hpp.ok.parsing;

import fise2.hpp.ok.eventsIR.Post;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;

public class PostParserTest {

    @Test
    public void getNext() throws IOException, ParseException {
        Reader reader = new StringReader("2019-05-15T12:00:00.000+0000|1|54861852|Premier post|user\n");
        PostParser parser = new PostParser(reader);
        Post post = parser.getNext();
        Assert.assertEquals(1557921600000L, post.ts);
        Assert.assertEquals(1, post.post_id);
        Assert.assertEquals(54861852, post.user_id);
        Assert.assertEquals("Premier post", post.post);
        Assert.assertEquals("user", post.user);
    }
}