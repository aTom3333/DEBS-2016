package fise2.hpp.ok.parsing;


import fise2.hpp.ok.eventsIR.Friendship;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

public class FriendshipParser extends AbstractParser {
    public FriendshipParser(Reader reader) {
        super(reader);
    }

    public Friendship getNext() throws IOException, ParseException {
        Friendship friendship = new Friendship();
        String[] line = splitLine();
        friendship.ts = stringToTS(line[0]);
        friendship.user_id_1 = Integer.valueOf(line[1]);
        friendship.user_id_2 = Integer.valueOf(line[2]);

        return friendship;
    }
}
