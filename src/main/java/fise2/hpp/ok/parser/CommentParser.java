package fise2.hpp.ok.parser;

import fise2.hpp.ok.eventsIR.Comment;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

public class CommentParser extends AbstractParser {
    public CommentParser(Reader reader) {
        super(reader);
    }

    public Comment getNext() throws IOException, ParseException {
        Comment comment = new Comment();
        String[] line = splitLine();
        comment.ts = stringToTS(line[0]);
        comment.comment_id = Integer.valueOf(line[1]);
        comment.user_id = Integer.valueOf(line[2]);
        comment.comment = line[3];
        comment.user = line[4];
        comment.comment_replied = Integer.valueOf(line[5]);
        comment.post_replied = Integer.valueOf(line[6]);

        return comment;
    }
}
