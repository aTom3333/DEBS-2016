package fise2.hpp.ok.eventsIR;

public class Post implements Event {

    public int ts;
    public int post_id;
    public int user_id;
    public String post;
    public String user;

    @Override
    public Type getType() {
        return Type.POST;
    }
}
