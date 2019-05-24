package fise2.hpp.ok.eventsIR;

import fise2.hpp.ok.interfaces.Event;

import java.util.Objects;

public class Like implements Event {

    public long ts;
    public long user_id;
    public long comment_id;

    public Like() {
    }

    public Like(long ts, long user_id, long comment_id) {
        this.ts = ts;
        this.user_id = user_id;
        this.comment_id = comment_id;
    }

    @Override
    public String toString() {
        return "Like{" +
                "ts=" + ts +
                ", user_id=" + user_id +
                ", comment_id=" + comment_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return ts == like.ts &&
                user_id == like.user_id &&
                comment_id == like.comment_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ts, user_id, comment_id);
    }

    @Override
    public Type getType() {
        return Type.LIKE;
    }

    @Override
    public long getTS() {
        return ts;
    }
}
