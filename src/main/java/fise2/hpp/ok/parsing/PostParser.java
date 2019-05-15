package fise2.hpp.ok.parsing;

import fise2.hpp.ok.eventsIR.Post;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

public class PostParser extends AbstractParser {
    public PostParser(Reader reader) {
        super(reader);
    }

    public Post getNext() throws ParseException, IOException {
        String s = input.readLine();
        if (s == null || s.isEmpty()) {
            return null;
        }

        Post post = new Post();
        String[] line = splitLine(s);
        post.ts = stringToTS(line[0]);
        post.post_id = Integer.valueOf(line[1]);
        post.user_id = Integer.valueOf(line[2]);
        post.post = line[3];
        post.user = line[4];

        return post;
    }
}
