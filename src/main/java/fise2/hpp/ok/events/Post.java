package fise2.hpp.ok.events;

import fise2.hpp.ok.interfaces.Answerable;
import fise2.hpp.ok.structs.User;

public class Post implements Answerable {

    public long ts;
    public final String post;
    public User poster;

    public Post(long ts, User poster, String post) {
        this.ts = ts;
        this.poster = poster;
        this.post = post;
    }

    @Override
    public Type getType() {
        return Type.POST;
    }
}
