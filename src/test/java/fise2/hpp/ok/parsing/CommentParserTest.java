package fise2.hpp.ok.parsing;

import fise2.hpp.ok.eventsIR.Comment;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;

public class CommentParserTest {

    @Test
    public void epoch() throws IOException, ParseException {
        Reader reader = new StringReader("1970-01-01T00:00:00.000+0000|1|2|Comment|Name|3|\n");
        CommentParser parser = new CommentParser(reader);
        Comment comment = parser.getNext();
        Assert.assertEquals(0, comment.ts);
        Assert.assertEquals(1, comment.comment_id);
        Assert.assertEquals(2, comment.user_id);
        Assert.assertEquals("Comment", comment.comment);
        Assert.assertEquals("Name", comment.user);
        Assert.assertEquals(Integer.valueOf(3), comment.comment_replied);
        Assert.assertNull(comment.post_replied);
    }

    @Test
    public void epoch2() throws IOException, ParseException {
        Reader reader = new StringReader("1970-01-01T00:00:00.000+0000|1|2|Comment|Name||3\n");
        CommentParser parser = new CommentParser(reader);
        Comment comment = parser.getNext();
        Assert.assertEquals(0, comment.ts);
        Assert.assertEquals(1, comment.comment_id);
        Assert.assertEquals(2, comment.user_id);
        Assert.assertEquals("Comment", comment.comment);
        Assert.assertEquals("Name", comment.user);
        Assert.assertNull(comment.comment_replied);
        Assert.assertEquals(Integer.valueOf(3), comment.post_replied);
    }
}