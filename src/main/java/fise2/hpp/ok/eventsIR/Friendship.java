package fise2.hpp.ok.eventsIR;

import fise2.hpp.ok.interfaces.Event;

public class Friendship implements Event {

    public long ts;
    public int user_id_1;
    public int user_id_2;

    @Override
    public Type getType() {
        return Type.FRIENDSHIP;
    }

    @Override
    public long getTS() {
        return ts;
    }
}
