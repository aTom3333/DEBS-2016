package fise2.hpp.ok.eventsIR;

import fise2.hpp.ok.interfaces.Event;

import java.util.Objects;

public class Friendship implements Event {

    public long ts;
    public int user_id_1;
    public int user_id_2;

    public Friendship() {
    }

    public Friendship(long ts, int user_id_1, int user_id_2) {
        this.ts = ts;
        this.user_id_1 = user_id_1;
        this.user_id_2 = user_id_2;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "ts=" + ts +
                ", user_id_1=" + user_id_1 +
                ", user_id_2=" + user_id_2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return ts == that.ts &&
                user_id_1 == that.user_id_1 &&
                user_id_2 == that.user_id_2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ts, user_id_1, user_id_2);
    }

    @Override
    public Type getType() {
        return Type.FRIENDSHIP;
    }

    @Override
    public long getTS() {
        return ts;
    }
}
