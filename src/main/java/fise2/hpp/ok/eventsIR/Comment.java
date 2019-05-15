package fise2.hpp.ok.eventsIR;

import fise2.hpp.ok.interfaces.Event;

import java.util.Objects;

public class Comment implements Event {

    public long ts;
    public int comment_id;
    public int user_id;
    public String comment;
    public String user;
    public Integer comment_replied = null;
    public Integer post_replied = null;

    public Comment() {
    }

    public Comment(long ts, int comment_id, int user_id, String comment, String user, Integer comment_replied, Integer post_replied) {
        this.ts = ts;
        this.comment_id = comment_id;
        this.user_id = user_id;
        this.comment = comment;
        this.user = user;
        this.comment_replied = comment_replied;
        this.post_replied = post_replied;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "ts=" + ts +
                ", comment_id=" + comment_id +
                ", user_id=" + user_id +
                ", comment='" + comment + '\'' +
                ", user='" + user + '\'' +
                ", comment_replied=" + comment_replied +
                ", post_replied=" + post_replied +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return ts == comment1.ts &&
                comment_id == comment1.comment_id &&
                user_id == comment1.user_id &&
                Objects.equals(comment, comment1.comment) &&
                Objects.equals(user, comment1.user) &&
                Objects.equals(comment_replied, comment1.comment_replied) &&
                Objects.equals(post_replied, comment1.post_replied);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ts, comment_id, user_id, comment, user, comment_replied, post_replied);
    }

    @Override
    public Type getType() {
        return Type.COMMENT;
    }

    @Override
    public long getTS() {
        return ts;
    }
}
