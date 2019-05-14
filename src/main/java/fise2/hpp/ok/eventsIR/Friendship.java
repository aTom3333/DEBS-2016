package fise2.hpp.ok.eventsIR;

public class Friendship implements Event {

    public long ts;
    public int user_id_1;
    public int user_id_2;

    @Override
    public Type getType() {
        return Type.FRIENDSHIP;
    }
}
