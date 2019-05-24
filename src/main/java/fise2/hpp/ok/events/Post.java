package fise2.hpp.ok.events;

import fise2.hpp.ok.interfaces.Answerable;
import fise2.hpp.ok.interfaces.Perishable;
import fise2.hpp.ok.structs.Data;
import fise2.hpp.ok.structs.User;
import fise2.hpp.ok.utils.Utils;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.HashSet;
import java.util.Set;

public class Post implements Answerable, Perishable, Comparable<Post> {

    public long ts;
    public final long post_id;
    public final String post;
    public User poster;
    public final Set<Comment> relatedComments = new HashSet<>(); // For faster lookup

    private int score = 10;
    private int cachedTotalScore = 10;
    private boolean isCacheUpdtated = true;

    public Post(long ts, long post_id, User poster, String post) {
        this.post_id = post_id;
        this.ts = ts;
        this.poster = poster;
        this.post = post;
    }

    @Override
    public Type getType() {
        return Type.POST;
    }

    @Override
    public long getTS() {
        return ts;
    }

    public int score() {
        return score;
    }

    public int getTotalScore() {
        if (!isCacheUpdtated) {
            cachedTotalScore = relatedComments.stream().mapToInt(c -> c.score).sum() + score;
            isCacheUpdtated = true;
        }
        return cachedTotalScore;
    }

    @Override
    public void perish(int amount) {
        score = Math.max(0, score - amount);
        isCacheUpdtated = false;
    }

    @Override
    public String toString() {
        return "Post{" +
                "ts=" + Utils.TSToString(ts) +
                ", post_id=" + post_id +
                ", post='" + post + '\'' +
                ", poster=" + poster +
                ", relatedComments=" + relatedComments +
                ", score=" + score +
                '}';
    }

    public int getTotalCommenters() {
        HashSet<User> users = new HashSet<User>();
        for (Comment c : relatedComments) {
            if (c.commentator != poster) {
                users.add(c.commentator);
            }
        }
        return users.size();
    }

    @Override
    public boolean updateScore(long ts) {
        int numdays = (int) ((ts - this.ts) / Data.MS_PER_DAY);
        int new_score = Math.max(10 - numdays, 0);
        if (new_score != score) {
            score = new_score;
            isCacheUpdtated = false;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Post post) {
        return new CompareToBuilder()
                .append(post.getTotalScore(), getTotalScore())
                .append(post.getTS(), getTS())
                .toComparison();
    }
}
