package fise2.hpp.ok.parsing;

import fise2.hpp.ok.eventsIR.Like;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

public class LikeParser extends AbstractParser {
    public LikeParser(Reader reader) {
        super(reader);
    }

    public Like getNext() throws IOException, ParseException {
        String s = input.readLine();
        if (s == null || s.isEmpty()) {
            return null;
        }

        Like like = new Like();
        String[] line = splitLine(s);
        like.ts = stringToTS(line[0]);
        like.user_id = Integer.valueOf(line[1]);
        like.comment_id = Integer.valueOf(line[2]);

        return like;
    }
}
