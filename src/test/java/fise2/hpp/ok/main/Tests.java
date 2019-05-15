package fise2.hpp.ok.main;

import fise2.hpp.ok.parsing.AbstractParser;
import fise2.hpp.ok.parsing.CommentParser;
import fise2.hpp.ok.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

public class Tests {

    @Test
    public void testLoadFull() throws IOException, URISyntaxException, ParseException {
        Query1 query1 = new Query1("Q1Basic");
        query1.loadFullRessources();
        Assert.assertEquals(3, query1.data.users.size());
        Assert.assertEquals(3, query1.data.posts.size());
        Assert.assertEquals(0, query1.data.comments.size());

        Assert.assertEquals(query1.data.users.get(3981).name, "Lei Liu");
        Assert.assertEquals(query1.data.users.get(4661).name, "Michael Wang");
        Assert.assertEquals(query1.data.users.get(2608).name, "Wei Zhu");

        Assert.assertEquals(query1.data.posts.get(1039993).ts, AbstractParser.stringToTS("2010-02-01T05:12:32.921+0000"));
        Assert.assertEquals(query1.data.posts.get(1039993).post, "");
        Assert.assertEquals(query1.data.posts.get(1039993).poster, query1.data.users.get(3981));
        Assert.assertEquals(query1.data.posts.get(299101).ts, AbstractParser.stringToTS("2010-02-02T19:53:43.226+0000"));
        Assert.assertEquals(query1.data.posts.get(299101).post, "photo299101.jpg");
        Assert.assertEquals(query1.data.posts.get(299101).poster, query1.data.users.get(4661));
        Assert.assertEquals(query1.data.posts.get(529360).ts, AbstractParser.stringToTS("2010-02-09T04:05:10.421+0000"));
        Assert.assertEquals(query1.data.posts.get(529360).post, "");
        Assert.assertEquals(query1.data.posts.get(529360).poster, query1.data.users.get(2608));

    }

    @Test
    public void Q1Basic() throws FileNotFoundException, URISyntaxException {
        CommentParser commentParser = new CommentParser(new FileReader(Utils.pathToResource("Q1Basic/comments.dat")));
    }
}