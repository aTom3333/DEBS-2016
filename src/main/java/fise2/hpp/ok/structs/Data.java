package fise2.hpp.ok.structs;

import fise2.hpp.ok.events.Comment;
import fise2.hpp.ok.events.Post;
import fise2.hpp.ok.interfaces.Perishable;
import fise2.hpp.ok.utils.CircularList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Data {
    public CircularList<Perishable> perishables = new CircularList<>();

    private static int MS_PER_DAY = 24 * 60 * 60 * 1000;

    public Map<Integer, User> users = new HashMap<>();

    public Map<Integer, Post> posts = new HashMap<>();

    public Map<Integer, Comment> comments = new HashMap<>();

    public Data() {
    }

    public long lastTS = 0;

    public Post[] oldTop3;

    public void addPost(Post post) {
        posts.put(post.post_id, post);

        expireAt();

        perishables.addBefore(post);
    }

    public void addComment(Comment comment) {
        Post parent = comment.getParentPost();
        if (parent.score <= 0) {
            return; // Si le post est déjà expiré, on ne retient pas le commentaire
        }
        comments.put(comment.comment_id, comment);
        parent.relatedComments.add(comment);

        expireAt();

        perishables.addBefore(comment);
    }

    public void removePost(Post post) {
        posts.remove(post.post_id);
        for (Comment c : post.relatedComments)
            comments.remove(c.comment_id);
    }

    public void expireUntil(long timestamp) {
        if (perishables.isEmpty()) {
            lastTS = timestamp;
            return;
        }

        final long noOfFullDay = (timestamp - lastTS) / MS_PER_DAY;
        if (noOfFullDay > 0) {
            perishables.forEach(p -> p.perish((int) noOfFullDay));
        }

        final Perishable first = perishables.curr();

        int iter = 0;

        final long lastDayTS = lastTS % MS_PER_DAY; // Number of ms since midnight
        final long currentDayTS = timestamp % MS_PER_DAY; // Number of ms since midnight

        if (lastDayTS < currentDayTS) {
            if (first.getTS() % MS_PER_DAY > lastDayTS
                    && first.getTS() % MS_PER_DAY < currentDayTS) {
                first.perish(1);
                perishables.advanceForward();
                iter++;
            }
            while (perishables.curr() != first
                    && perishables.curr().getTS() % MS_PER_DAY > lastDayTS
                    && perishables.curr().getTS() % MS_PER_DAY < currentDayTS) {
                perishables.curr().perish(1);
                perishables.advanceForward();
                iter++;
            }
        } else {
            if (first.getTS() % MS_PER_DAY > lastDayTS
                    || first.getTS() % MS_PER_DAY < currentDayTS) {
                first.perish(1);
                perishables.advanceForward();
                iter++;
            }
            while (perishables.curr() != first
                    && (perishables.curr().getTS() % MS_PER_DAY > lastDayTS
                    || perishables.curr().getTS() % MS_PER_DAY < currentDayTS)) {
                perishables.curr().perish(1);
                perishables.advanceForward();
                iter++;
            }
        }

        // Remove expired data
        for (int i = 0; i < iter; i++)
            perishables.advanceBackward();
        for (int i = 0; i < iter; i++) {
            if (perishables.curr() instanceof Post && ((Post) perishables.curr()).getTotalScore() == 0) {
                removePost((Post) perishables.curr());
                perishables.removeCurr();
            } else {
                perishables.advanceForward();
            }
        }

        lastTS = timestamp;
    }

    public void expireAt() {
        if (perishables.isEmpty()) {
            return;
        }

        final Perishable first = perishables.curr();

        int iter = 0;

        final long lastDayTS = lastTS % MS_PER_DAY; // Number of ms since midnight

        if (first.getTS() % MS_PER_DAY == lastDayTS && first.getTS() < lastTS) {
            first.perish(1);
            perishables.advanceForward();
            iter++;
        }
        while (perishables.curr() != first
                && perishables.curr().getTS() % MS_PER_DAY == lastDayTS) {
            perishables.curr().perish(1);
            perishables.advanceForward();
            iter++;
        }

        // Remove expired data
        for (int i = 0; i < iter; i++)
            perishables.advanceBackward();
        for (int i = 0; i < iter; i++) {
            if (perishables.curr() instanceof Post && ((Post) perishables.curr()).getTotalScore() == 0) {
                removePost((Post) perishables.curr());
                perishables.removeCurr();
            } else {
                perishables.advanceForward();
            }
        }
    }


    public Top3 getTop3() {
        Top3 top3 = new Top3();

        ArrayList<Post> list = new ArrayList<>(posts.values());

        list.sort((a, b) -> b.getTotalScore() - a.getTotalScore());

        if (list.size() > 0) {
            top3.data[0] = new Top3.PostData(list.get(0));
        }
        if (list.size() > 1) {
            top3.data[1] = new Top3.PostData(list.get(1));
        }
        if (list.size() > 2) {
            top3.data[2] = new Top3.PostData(list.get(2));
        }

        return top3;
    }

}
