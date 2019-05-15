package fise2.hpp.ok.eventsIR;

import fise2.hpp.ok.interfaces.Event;

public class Post implements Event {

    public long ts;
    public int post_id;
    public int user_id;
    public String post;
    public String user;

    @Override
    public Type getType() {
        return Type.POST;
    }

    @Override
    public long getTS() {
        return ts;
    }
}
