package fise2.hpp.ok.events;

import fise2.hpp.ok.interfaces.Answerable;
import fise2.hpp.ok.interfaces.Perishable;
import fise2.hpp.ok.structs.User;

public class Comment implements Answerable, Perishable {

    public final int comment_id;
    public final String comment;
    public long ts = 0;
    public User commentator;
    public Answerable answered;

    public int score = 10;

    public Comment(long ts, int comment_id, User commentator, String comment, Answerable answered) {
        this.ts = ts;
        this.comment_id = comment_id;
        this.answered = answered;
        this.comment = comment;
        this.commentator = commentator;
    }

    public Post getParentPost() {
        if(answered instanceof Post)
            return (Post) answered;
        else
            return ((Comment) answered).getParentPost();
    }

    @Override
    public Type getType() {
        return Type.COMMENT;
    }
}