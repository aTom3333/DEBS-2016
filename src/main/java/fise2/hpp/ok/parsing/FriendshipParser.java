package fise2.hpp.ok.parsing;


import fise2.hpp.ok.eventsIR.Friendship;
import fise2.hpp.ok.utils.Utils;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

public class FriendshipParser extends AbstractParser {
    public FriendshipParser(Reader reader) {
        super(reader);
    }

    public Friendship getNext() throws IOException, ParseException {
        String s = input.readLine();
        if (s == null || s.isEmpty()) {
            return null;
        }

        Friendship friendship = new Friendship();
        String[] line = Utils.splitLine(s);
        friendship.ts = Utils.stringToTS(line[0]);
        friendship.user_id_1 = Long.valueOf(line[1]);
        friendship.user_id_2 = Long.valueOf(line[2]);

        return friendship;
    }
}
