package fise2.hpp.ok.parser;

import fise2.hpp.ok.eventsIR.Like;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

public class LikeParser extends AbstractParser {
    public LikeParser(Reader reader) {
        super(reader);
    }

    public Like getNext() throws IOException, ParseException {
        Like like = new Like();
        String[] line = splitLine();
        like.ts = stringToTS(line[0]);
        like.user_id = Integer.valueOf(line[1]);
        like.comment_id = Integer.valueOf(line[2]);

        return like;
    }
}
