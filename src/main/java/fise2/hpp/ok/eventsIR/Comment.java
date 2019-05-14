package fise2.hpp.ok.eventsIR;

public class Comment implements Event {

    public int ts;
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
