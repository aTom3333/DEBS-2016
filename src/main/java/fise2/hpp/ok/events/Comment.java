package fise2.hpp.ok.events;

import fise2.hpp.ok.interfaces.Answerable;
import fise2.hpp.ok.structs.User;

public class Comment implements Answerable {

    public final String comment;
    public long ts = 0;
    public User commentator;
    public Answerable answered;

    public int likes = 0;

    public Comment(long ts, User commentator, String comment, Answerable answered) {
        this.ts = ts;
        this.answered = answered;
        this.comment = comment;
        this.commentator = commentator;
    }

    @Override
    public Type getType() {
        return Type.COMMENT;
    }
}