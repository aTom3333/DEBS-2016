package fise2.hpp.ok.events;

import fise2.hpp.ok.interfaces.Answerable;
import fise2.hpp.ok.interfaces.Perishable;
import fise2.hpp.ok.structs.User;

import java.util.HashSet;
import java.util.Set;

public class Post implements Answerable, Perishable {

    public long ts;
    public final int post_id;
    public final String post;
    public User poster;
    public final Set<Comment> relatedComments = new HashSet<>(); // For faster lookup

    public int score = 10;

    public Post(long ts, int post_id, User poster, String post) {
        this.post_id = post_id;
        this.ts = ts;
        this.poster = poster;
        this.post = post;
    }

    @Override
    public Type getType() {
        return Type.POST;
    }
}
