package fise2.hpp.ok.eventsIR;

public class Like implements Event {

    public int ts;
    public int user_id;
    public int comment_id;

    @Override
    public Type getType() {
        return Type.LIKE;
    }
}