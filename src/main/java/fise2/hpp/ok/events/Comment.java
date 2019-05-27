package fise2.hpp.ok.events;

import fise2.hpp.ok.interfaces.Answerable;
import fise2.hpp.ok.interfaces.Perishable;
import fise2.hpp.ok.structs.Data;
import fise2.hpp.ok.structs.User;
import fise2.hpp.ok.utils.Utils;

public class Comment implements Answerable, Perishable {

    public final long comment_id;
    public final String comment;
    public long ts = 0;
    public User commentator;
    public Answerable answered;

    public int score = 10;

    public Comment(long ts, long comment_id, User commentator, String comment, Answerable answered) {
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

    @Override
    public long getTS() {
        return ts;
    }

    @Override
    public void perish(int amount) {
        score = Math.max(0, score - amount);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment_id=" + comment_id +
                ", ts=" + Utils.TSToString(ts) +
                ", comment='" + comment + '\'' +
                ", commentator=" + commentator +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean updateScore(long ts) {
        int numdays = (int) ((ts - this.ts) / Data.MS_PER_DAY);
        int new_score = Math.max(10 - numdays, 0);
        if (new_score != score) {
            getParentPost().totalScore -= (score - new_score);
            score = new_score;
            return true;
        }
        return false;
    }
}