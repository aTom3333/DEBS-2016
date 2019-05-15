package fise2.hpp.ok.eventsIR;

import fise2.hpp.ok.interfaces.Event;

import java.util.Objects;

public class Post implements Event {

    public long ts;
    public int post_id;
    public int user_id;
    public String post;
    public String user;

    public Post() {
    }

    public Post(long ts, int post_id, int user_id, String post, String user) {
        this.ts = ts;
        this.post_id = post_id;
        this.user_id = user_id;
        this.post = post;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "ts=" + ts +
                ", post_id=" + post_id +
                ", user_id=" + user_id +
                ", post='" + post + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Post post1 = (Post) o;
        return ts == post1.ts &&
                post_id == post1.post_id &&
                user_id == post1.user_id &&
                Objects.equals(post, post1.post) &&
                Objects.equals(user, post1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ts, post_id, user_id, post, user);
    }

    @Override
    public Type getType() {
        return Type.POST;
    }

    @Override
    public long getTS() {
        return ts;
    }
}
