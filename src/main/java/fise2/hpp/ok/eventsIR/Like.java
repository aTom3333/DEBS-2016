package fise2.hpp.ok.eventsIR;

import fise2.hpp.ok.interfaces.Event;

public class Like implements Event {

    public long ts;
    public int user_id;
    public int comment_id;

    @Override
    public Type getType() {
        return Type.LIKE;
    }

    @Override
    public long getTS() {
        return ts;
    }
}
