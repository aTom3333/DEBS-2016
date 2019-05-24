package fise2.hpp.ok.parsing;

import fise2.hpp.ok.eventsIR.Like;
import fise2.hpp.ok.utils.Utils;

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
        String[] line = Utils.splitLine(s);
        like.ts = Utils.stringToTS(line[0]);
        like.user_id = Long.valueOf(line[1]);
        like.comment_id = Long.valueOf(line[2]);

        return like;
    }
}
