package fise2.hpp.ok.parsing;

import fise2.hpp.ok.eventsIR.Comment;
import fise2.hpp.ok.utils.Utils;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

public class CommentParser extends AbstractParser {
    public CommentParser(Reader reader) {
        super(reader);
    }

    public Comment getNext() throws IOException, ParseException {
        String s = input.readLine();
        if (s == null || s.isEmpty()) {
            return null;
        }

        Comment comment = new Comment();
        String[] line = Utils.splitLine(s);

        comment.ts = Utils.stringToTS(line[0]);

        comment.comment_id = Integer.valueOf(line[1]);
        comment.user_id = Integer.valueOf(line[2]);

        comment.comment = line[3];
        comment.user = line[4];

        if(!line[5].isEmpty())
            comment.comment_replied = Integer.valueOf(line[5]);

        if(!line[6].isEmpty())
            comment.post_replied = Integer.valueOf(line[6]);

        return comment;
    }
}
