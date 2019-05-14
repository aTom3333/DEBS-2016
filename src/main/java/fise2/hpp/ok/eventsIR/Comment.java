package fise2.hpp.ok.eventsIR;

import fise2.hpp.ok.interfaces.Event;

public class Comment implements Event {

    public long ts;
    public int comment_id;
    public int user_id;
    public String comment;
    public String user;
    public int comment_replied;
    public int post_replied;

    @Override
    public Type getType() {
        return Type.COMMENT;
    }
}
